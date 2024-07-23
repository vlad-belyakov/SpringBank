package org.example.JWT;

public class JWTKeyGenerator {
    /*public static Key generateKey() {
        return Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    }

    public static String generateToken(Key key) {
        return Jwts.builder()
                .setSubject("user") // субъект (пользователь)
                .setIssuer("yourapp") // издатель
                .setIssuedAt(new Date()) // время выпуска
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // время истечения (1 час)
                .signWith(key, SignatureAlgorithm.HS256) // подписываем токен
                .compact();
    }*/
}