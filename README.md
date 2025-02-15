# Yourbank

## Описание
Этот проект представляет собой сайт банка, разработанный с использованием Spring Boot, Spring Security и Spring Data JPA. В основе архитектуры используется паттерн Controller-Service-Repository, а также принципы SOLID и DRY.

Для GUI использовался Thymeleaf, CSS, HTML. Для защиты от мошенников были использованы роли и JWT.

## Стек технологий
- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Frontend**: Thymeleaf, CSS, HTML
- **База данных**: PostgreSQL (или другая SQL БД)
- **Безопасность**: JWT, роли пользователей, хеширование паролей

## НАЗВАНИЯ ПАКЕТОВ И ИХ ПРЕДНАЗНАЧЕНИЯ

| Пакет | Описание |
|-------|----------|
| `controllers` | Пакет контроллеров |
| `entities` | Пакет с классами для работы с БД |
| `json` | Пакет с классами для работы с JSON* |
| `jwt` | Пакет с классами для настройки и работы JWT |
| `mathematics` | Пакет с классами для работы с банковскими задачами* |
| `payment` | Пакет с классами для разделения функционала приложения* |
| `repository` | Пакет с интерфейсами для работы с БД |
| `security` | Пакет с классами для настройки и работы Spring Security (роли и JWT) |
| `service` | Пакет с классами для бизнес-логики |
| `template` | Пакет с интерфейсами для функционала банковской логики* |

_* - задачи, которые будут решаться в будущем для развития функционала приложения._

## КРАТКОЕ ОБЪЯСНЕНИЕ РАБОТЫ ПРОГРАММЫ
Для решения поставленной задачи, а именно создания сайта банка, был выбран паттерн **Controller-Service-Repository**. Также использовались принципы **SOLID** и **DRY**. Для GUI использовался thymeleaf, CSS, HTML. Для защиты от мошенников были использованы роли и JWT. 

## ЗАЩИТА ОТ МОШЕННИКОВ
### 1. Хеширование паролей
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

### 2. Идентификация, аутентификация и авторизация
#### Идентификация пользователя по номеру телефона:
```java
UserClient user = clientService.findByPhoneNumber(phoneNumber);
```

#### Проверка пароля:
```java
if (!passwordEncoder.matches(password, user.getPassword())) {
    model.addAttribute("error", "Неверный пароль.");
    return "login";
}
```

#### Генерация JWT токена и установка куки:
```java
String token = JWTKeyGenerator.generateToken(userDetails, jwtKey);
Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
jwtCookie.setHttpOnly(true);   // Защита от XSS
jwtCookie.setSecure(false);    // Только по HTTPS
jwtCookie.setPath("/");        // Доступ для всех страниц
jwtCookie.setMaxAge(3600);
jwtCookie.setAttribute("SameSite", "Lax"); // Защита от CSRF
response.addCookie(jwtCookie);
```

### 3. Ограничение доступа по ролям
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/v1/registration", "/v1/login", "/css/**", "/js/**", "/images/**", "/static/**", "/favicon.ico").permitAll()
            .requestMatchers("/v1/admin/**").hasAuthority("ADMIN")
            .requestMatchers("/v1/user/**").hasAnyAuthority("USER", "ADMIN")
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint((request, response, authException) -> {
                response.sendRedirect("/v1/login?error=true");
            })
        );
    return http.build();
}
```

## РАБОТА С БД
Связь в базой данных велась через Spring Data JPA, где для каждой таблицы создавался собственный интерфейс, где прописывались нужные для работы методы, которые реализовывал сам Spring или я сам через аннотацию Query. Были созданы 3 таблицы:

**Структура таблиц:**
- `clients` – таблица с данными пользователя (ФИО, ID, баланс и т. д.)
- `roles` – таблица с ролями для разграничения доступа (ID, название роли)
- `client_roles` – таблица для связи пользователей и ролей (ID пользователя, ID роли)

Пример SQL-запроса для обновления баланса пользователя:
```java
@Transactional
@Modifying
@Query(value = "UPDATE clients SET client_balance = :#{#client.balance} WHERE client_id = :#{#client.id}", nativeQuery = true)
void updateBalanceUser(@Param("client") UserClient client);
```

## СУЩНОСТИ ДЛЯ БД
Для того, чтобы Spring смог связать свои запросы репозиториев с базой данных, были созданы классы. Аннотациями в них были "размечены" переменные для создания связи между объектами класса и бд. Так же аннотациями была создана связь между 2 таблицами roles, clients и 3-ей таблицей client_roles.
Пример сущности пользователя с ролями:
```java
@Component
@Entity
@Table(name = "clients")
public class UserClient extends Client {
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "client_roles",
        joinColumns = @JoinColumn(name = "id_client"),
        inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "client_name")
    private String name;
}
```

## БИЗНЕС-ЛОГИКА
Для бизнес логики был написал отдельный слой сервисов, который выполнял запросы контроллеров. 
Пример сервиса для работы с клиентами:
```java
@Service
@Lazy
public class ClientService {
    private final ClientRepository clientRepository;
    private UserClient userClient;
    private RoleRepository roleRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, RoleRepository roleRepository){
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
    }

    public UserClient createClient(UserClient client){
        clientRepository.save(client);
        this.userClient = client;
        return client;
    }
}
```

## КОНТРОЛЛЕРЫ
Для контроллеров так же был написан отдельный слой, где контроллеры обрабатывали запросы пользователя и взаимодействовали с GUI.
Пример контроллера:
```java
@Controller
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
@RequestMapping("/v1/user/main-page")
public class MainPageController {
    private final ClientService clientService;

    @Autowired
    public MainPageController(ClientService clientService){
        System.out.println("MainPageController создан");
        this.clientService = clientService;
    }

    @GetMapping("/all-clients")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public List<UserClient> getAllClients(){
        return clientService.findAll();
    }
}
```

