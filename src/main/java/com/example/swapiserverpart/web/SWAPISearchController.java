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
import java.util.*;

@CrossOrigin(origins="http://127.0.0.1:8081")
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


    @CrossOrigin(origins = "http://127.0.0.1:8081/people")
    @RequestMapping(value = "/people/{name}", method = RequestMethod.GET, produces = "application/json")
    public String getHumanByName(@PathVariable String name) throws IOException, SQLException {
        List<?> peopleList = getEntityManager().createQuery("SELECT people FROM People people WHERE people.name like ?1")
                .setParameter(1, name + "%")
                .getResultList();
        WorkerService workerService = new WorkerService();
        return workerService.EntityToJson((List<Object>) peopleList);
    }

    @CrossOrigin(origins = "http://127.0.0.1:8081/film")
    @RequestMapping(value = "/film/{title}", method = RequestMethod.GET, produces = "application/json")
    public String getFilmByTitle(@PathVariable String title) throws IOException, SQLException {
        List<?> filmList = getEntityManager().createQuery("SELECT film from Film film where film.title like ?1")
                .setParameter(1, title + "%")
                .getResultList();
        WorkerService workerService = new WorkerService();
        return workerService.EntityToJson((List<Object>) filmList);
    }

    @CrossOrigin(origins = "http://127.0.0.1:8081/planet")
    @RequestMapping(value = "/planet/{name}", method = RequestMethod.GET, produces = "application/json")
    public String getPlanetByName(@PathVariable String name) throws IOException, SQLException {
        List<?> planetList = getEntityManager().createQuery("SELECT planet FROM Planet planet WHERE planet.name like ?1")
                .setParameter(1, name + "%")
                .getResultList();
        WorkerService workerService = new WorkerService();
        return workerService.EntityToJson((List<Object>) planetList);
    }
    @CrossOrigin(origins = "http://localhost:8081/specie")
    @RequestMapping(value = "/specie/{name}", method = RequestMethod.GET, produces = "application/json")
    public String getSpeciesByName(@PathVariable String name) throws IOException, SQLException {

        List<?> specieList = getEntityManager().createQuery("SELECT specie FROM Specie specie WHERE specie.name like ?1")
                .setParameter(1, name + "%")
                .getResultList();
        WorkerService workerService = new WorkerService();
        return workerService.EntityToJson((List<Object>)  specieList);
    }
    @CrossOrigin(origins = "http://127.0.0.1:8081/vehicle")
    @RequestMapping(value = "/vehicle/{name}", method = RequestMethod.GET, produces = "application/json")
    public String getVehicleByName(@PathVariable String name) throws IOException, SQLException {
        List<?> vehicleList = getEntityManager().createQuery("SELECT vehicle FROM Vehicle vehicle WHERE vehicle.name like ?1")
                .setParameter(1, name + "%")
                .getResultList();
        WorkerService workerService = new WorkerService();
        return workerService.EntityToJson((List<Object>) vehicleList);
    }

    @CrossOrigin(origins = "http://127.0.0.1:8081/starship")
    @RequestMapping(value = "/starship/{name}", method = RequestMethod.GET, produces = "application/json")
    public String getStarshipByName(@PathVariable String name) throws IOException, SQLException {
        List<?> starshipList = getEntityManager().createQuery("SELECT starship FROM Starship starship WHERE starship.name like ?1")
                .setParameter(1, name + "%")
                .getResultList();
        WorkerService workerService = new WorkerService();
        return workerService.EntityToJson((List<Object>) starshipList);
    }

}







