package com.seplag.senior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Ativa o agendamento de tarefas (importante para o SyncService)
public class SeniorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeniorApplication.class, args);
    }
}