package com.example.first_project_java;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
public class FirstProjectJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstProjectJavaApplication.class, args);
        System.out.println("------------------ Sistema Inicializado! ------------------");
    }
}

