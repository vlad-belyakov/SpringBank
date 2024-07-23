package org.example.security;

import org.example.BankApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Authentication {

    private AnnotationConfigApplicationContext context;

    public Authentication(){
         context = new AnnotationConfigApplicationContext(BankApplication.class);
    }

}
