package com.example.swapiserverpart;

import com.example.swapiserverpart.models.People;
import com.example.swapiserverpart.service.WorkerService;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class SwapiServerPartApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwapiServerPartApplication.class, args);
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
