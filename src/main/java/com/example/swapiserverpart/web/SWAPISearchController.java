package com.example.swapiserverpart.web;
import com.example.swapiserverpart.service.WorkerService;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@RequestMapping(path = "/")
@RestController
@Getter
@Setter
public class SWAPISearchController {
    private final EntityManager entityManager;
    private final SessionFactory sessionFactory;
    private Session session;

    public SWAPISearchController() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SWAPI");
        this.entityManager = emf.createEntityManager();
        this.sessionFactory = emf.unwrap(SessionFactory.class);
        this.session = sessionFactory.openSession();
    }

       @RequestMapping(value = "/people/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getHumanByName(@PathVariable String name) throws IOException {
        List<?> peopleList = getEntityManager().createQuery("SELECT people FROM People people WHERE people.name like ?1")
                .setParameter(1, name + "%")
                .getResultList();
        WorkerService workerService = new WorkerService();
        return workerService.EntityToJson((List<Object>) peopleList);
    }


    @RequestMapping(value = "/film/{title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getFilmByTitle(@PathVariable String title) throws IOException{
        List<?> filmList = getEntityManager().createQuery("SELECT film from Film film where film.title like ?1")
                .setParameter(1, title + "%")
                .getResultList();
        WorkerService workerService = new WorkerService();
        return workerService.EntityToJson((List<Object>) filmList);
    }


    @RequestMapping(value = "/planet/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPlanetByName(@PathVariable String name) throws IOException, SQLException {
        List<?> planetList = getEntityManager().createQuery("SELECT planet FROM Planet planet WHERE planet.name like ?1")
                .setParameter(1, name + "%")
                .getResultList();
        WorkerService workerService = new WorkerService();
        return workerService.EntityToJson((List<Object>) planetList);
    }
    @RequestMapping(value = "/specie/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSpeciesByName(@PathVariable String name) throws IOException {

        List<?> specieList = getEntityManager().createQuery("SELECT specie FROM Specie specie WHERE specie.name like ?1")
                .setParameter(1, name + "%")
                .getResultList();
        WorkerService workerService = new WorkerService();
        return workerService.EntityToJson((List<Object>)  specieList);
    }

    @RequestMapping(value = "/vehicle/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getVehicleByName(@PathVariable String name) throws IOException, SQLException {
        List<?> vehicleList = getEntityManager().createQuery("SELECT vehicle FROM Vehicle vehicle WHERE vehicle.name like ?1")
                .setParameter(1, name + "%")
                .getResultList();
        WorkerService workerService = new WorkerService();
        return workerService.EntityToJson((List<Object>) vehicleList);
    }


    @RequestMapping(value = "/starship/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getStarshipByName(@PathVariable String name) throws IOException {
        List<?> starshipList = getEntityManager().createQuery("SELECT starship FROM Starship starship WHERE starship.name like ?1")
                .setParameter(1, name + "%")
                .getResultList();
        WorkerService workerService = new WorkerService();
        return workerService.EntityToJson((List<Object>) starshipList);
    }

}







