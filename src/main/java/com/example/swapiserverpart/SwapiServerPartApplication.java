package com.example.swapiserverpart;

import com.example.swapiserverpart.service.WorkerService;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.servlet.DispatcherType;
import java.io.IOException;

@SpringBootApplication
public class SwapiServerPartApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwapiServerPartApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/people").allowedOrigins("http://localhost:8081");
            }
        };
    }

    @PostConstruct
    void init() throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SWAPI");
      EntityManager  entityManager = emf.createEntityManager();



        WorkerService workerService = new WorkerService(entityManager );
        workerService.CollectRootLink("https://swapi.dev/api/people");
        workerService.CollectRootLink("https://swapi.dev/api/species");
        workerService.CollectRootLink("https://swapi.dev/api/planets");
        workerService.CollectRootLink("https://swapi.dev/api/films");
        workerService.CollectRootLink("https://swapi.dev/api/vehicles");
        workerService.CollectRootLink("https://swapi.dev/api/starships");

    }
}
