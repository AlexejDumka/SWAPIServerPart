package com.example.swapiserverpart.web;

import com.example.swapiserverpart.repository.PeopleRepo;
import com.example.swapiserverpart.service.WorkerService;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins="http://localhost:8080")
@RequestMapping(path = "/", produces = "application/json")
@RestController
@Getter
@Setter
public class SWAPISearchController {
    @Autowired
    private PeopleRepo peopleRepo;
    private final EntityManager entityManager;
    private final SessionFactory sessionFactory;
    private Session session;

    public SWAPISearchController(EntityManager entityManager) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SWAPI");
        this.entityManager = emf.createEntityManager();
        this.sessionFactory = emf.unwrap(SessionFactory.class);
        this.session = sessionFactory.openSession();
    }


    @CrossOrigin(origins = "http://localhost:8080/people")
    @RequestMapping(value = "/people/{filter}", method = RequestMethod.GET, produces = "application/json")
    public String getHumanByName(@PathVariable String filter) throws IOException, SQLException {
        List<?> peopleList = getEntityManager().createQuery("SELECT people from People people where people.name like ?1")
                .setParameter(1, filter + "%")
                .getResultList();
           WorkerService workerService = new WorkerService();
            return workerService.EntityToJson((List<Object>) peopleList);
        }


}
