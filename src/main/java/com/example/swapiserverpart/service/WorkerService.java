package com.example.swapiserverpart.service;


import com.example.swapiserverpart.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@NoArgsConstructor
@Getter
@Setter
@Service
public class WorkerService {
    private EntityManager entityManager;
    List<People> peopleList = new ArrayList<>();
    private Session session;

    public WorkerService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public WorkerService(Session session) {
        this.session = session;
    }


    private Set<String> linkSet;

    public APIType eApiType;
    private String baseUrl = null;

    public enum APIType {
        FILM, VEHICLE, SPECIE, PEOPLE, STARSHIP, PLANET, UNKNOWN
    }

    public void CollectRootLink(String baseUrl) throws IOException {
        JsonNode rootNode;
        ObjectMapper mapper = new ObjectMapper();
        rootNode = ProcessURL(baseUrl);
        if (rootNode.has("results")) {
            JsonNode entityNode = rootNode.findPath("results");
            APIType sType = GetRootEntityTypeFromURLProperty(baseUrl);
            if (!entityNode.isNull()) {
                if (entityNode.isArray()) {

                    switch (sType) {
                        case PEOPLE -> {
                            List<People> peopleList = Arrays.asList(mapper.readValue(entityNode.toString(), People[].class));
                            peopleList.forEach((people) -> PersistPeopleWithNestedCollections(people));
                        }
                        case PLANET -> {
                            List<Planet> planetList = Arrays.asList(mapper.readValue(entityNode.toString(), Planet[].class));
                            planetList.forEach((planet) -> PersistPlanetWithNestedCollections(planet));
                        }
                        case FILM -> {
                            List<Film> filmList = Arrays.asList(mapper.readValue(entityNode.toString(), Film[].class));
                            filmList.forEach((film) -> PersistFilmWithNestedCollections(film));
                        }
                        case SPECIE -> {
                            List<Specie> specieList = Arrays.asList(mapper.readValue(entityNode.toString(), Specie[].class));
                            specieList.forEach((specie -> PersisSpecieWithNestedCollections(specie)));
                        }
                        case VEHICLE -> {
                            List<Vehicle> vehicleList = Arrays.asList(mapper.readValue(entityNode.toString(), Vehicle[].class));
                            vehicleList.forEach((vehicle) -> PersistVehicleWithNestedCollections(vehicle));
                        }
                        case STARSHIP -> {
                            List<Starship> starshipList = Arrays.asList(mapper.readValue(entityNode.toString(), Starship[].class));
                            starshipList.forEach((starship) -> PersistStarshipWithNestedCollections(starship));
                        }
                        default -> {
                        }
                    }

                } else {
                    Object entity = switch (sType) {
                        case PEOPLE -> mapper.readValue(entityNode.toString(), People.class);
                        case PLANET -> mapper.readValue(entityNode.toString(), Planet.class);
                        case FILM -> mapper.readValue(entityNode.toString(), Film.class);
                        case SPECIE -> mapper.readValue(entityNode.toString(), Specie.class);
                        case VEHICLE -> mapper.readValue(entityNode.toString(), Vehicle.class);
                        case STARSHIP -> mapper.readValue(entityNode.toString(), Starship.class);
                        case UNKNOWN -> null;
                    };
                    entityManager.getTransaction().begin();
                    entityManager.persist(entity);
                    entityManager.flush();
                    entityManager.getTransaction().commit();
                }
            }
        }

        if (rootNode.has("next")) {
            JsonNode nextNode = rootNode.findPath("next");
            if (!nextNode.isNull()) {
                baseUrl = nextNode.toString().replace("\"", " ").trim();
                CollectRootLink(baseUrl);
            }
        }
    }

    public APIType GetRootEntityTypeFromURLProperty(String url) {
        return url.matches("(.*)people(.*)") ?
                APIType.PEOPLE : url.matches("(.*)planet(.*)") ?
                APIType.PLANET : url.matches("(.*)film(.*)") ?
                APIType.FILM : url.matches("(.*)specie(.*)") ?
                APIType.SPECIE : url.matches("(.*)vehicle(.*)") ?
                APIType.VEHICLE : url.matches("(.*)starship(.*)") ?
                APIType.STARSHIP : APIType.UNKNOWN;
        }

    public Object ProcessEntity(String urlLink) {
        ObjectMapper objMapper;
        try {
            objMapper = new ObjectMapper();
            APIType sType = GetRootEntityTypeFromURLProperty(urlLink);
            JsonNode rootNode = ProcessURL(urlLink);
            //JsonNode jNode = ProcessURL(urlLink);
            return switch (sType) {
                case PEOPLE -> objMapper.readValue(rootNode.toString(), People.class);
                case PLANET -> objMapper.readValue(rootNode.toString(), Planet.class);
                case FILM -> objMapper.readValue(rootNode.toString(), Film.class);
                case SPECIE -> objMapper.readValue(rootNode.toString(), Specie.class);
                case VEHICLE -> objMapper.readValue(rootNode.toString(), Vehicle.class);
                case STARSHIP -> objMapper.readValue(rootNode.toString(), Starship.class);
                case UNKNOWN -> null;
            };
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void PersistPeopleWithNestedCollections(People pl) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pl);
            entityManager.flush();
            entityManager.getTransaction().commit();
            if (pl.getFilms() != null && pl.getFilms().size() > 0) {
                pl.setPeople_film(pl.getFilms().parallelStream().map(urlLink -> (Film) ProcessEntity(urlLink)).collect(Collectors.toList()));
            }
            if (pl.getVehicles() != null && pl.getVehicles().size() > 0) {
                pl.setPeople_vehicle(pl.getVehicles().parallelStream().map(urlLink -> (Vehicle) ProcessEntity(urlLink)).collect(Collectors.toList()));
            }
            if (pl.getStarships() != null && pl.getStarships().size() > 0) {
                pl.setPeople_starship(pl.getStarships().parallelStream().map(urlLink -> (Starship) ProcessEntity(urlLink)).collect(Collectors.toList()));
            }
            if (pl.getSpecies() != null && pl.getSpecies().size() > 0) {
                pl.setPeople_specie(pl.getSpecies().parallelStream().map(urlLink -> (Specie) ProcessEntity(urlLink)).collect(Collectors.toList()));
            }
            pl.getFilms().clear();
            pl.getSpecies().clear();
            pl.getStarships().clear();
            pl.getVehicles().clear();
            entityManager.getTransaction().begin();
            entityManager.persist(pl);
            peopleList.add(pl);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void PersistPlanetWithNestedCollections(Planet planet) {
        entityManager.getTransaction().begin();
        entityManager.persist(planet);
        entityManager.flush();
        entityManager.getTransaction().commit();
        if (planet.getPeople() != null && planet.getPeople().size() > 0) {
            planet.setPlanet_people(planet.getPeople().parallelStream().map(urlLink -> {

                return (People) ProcessEntity(urlLink);

            }).collect(Collectors.toList()));

        }
        if (planet.getFilms() != null && planet.getFilms().size() > 0) {
            planet.setPlanet_film(planet.getFilms().parallelStream().map(urlLink -> (Film) ProcessEntity(urlLink)).collect(Collectors.toList()));
        }
        planet.getFilms().clear();
        planet.getPeople().clear();
        entityManager.getTransaction().begin();
        entityManager.persist(planet);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public void PersistFilmWithNestedCollections(Film film) {
        entityManager.getTransaction().begin();
        entityManager.persist(film);
        entityManager.flush();
        entityManager.getTransaction().commit();
        if (film.getStarships() != null && film.getStarships().size() > 0) {
            film.setFilm_starship(film.getStarships().parallelStream().map(urlLink -> (Starship) ProcessEntity(urlLink)).collect(Collectors.toList()));
        }
        if (film.getVehicles() != null && film.getVehicles().size() > 0) {
            film.setFilm_vehicle(film.getVehicles().parallelStream().map(urlLink -> (Vehicle) ProcessEntity(urlLink)).collect(Collectors.toList()));
        }
        if (film.getSpecies() != null && film.getSpecies().size() > 0) {
            film.setFilm_specie(film.getSpecies().parallelStream().map(urlLink -> {
                return (Specie) ProcessEntity(urlLink);
            }).collect(Collectors.toList()));
        }
        if (film.getPlanets() != null && film.getPlanets().size() > 0) {
            film.setFilm_planet(film.getPlanets().parallelStream().map(urlLink -> (Planet) ProcessEntity(urlLink)).collect(Collectors.toList()));
        }
        if (film.getPeople() != null && film.getPeople().size() > 0) {
            film.setFilm_people(film.getPeople().parallelStream().map(urlLink -> (People) ProcessEntity(urlLink)).collect(Collectors.toList()));
        }
        film.getPlanets().clear();
        film.getPeople().clear();
        film.getStarships().clear();
        film.getVehicles().clear();
        film.getSpecies().clear();
        entityManager.getTransaction().begin();
        entityManager.persist(film);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public void PersisSpecieWithNestedCollections(Specie specie) {
        entityManager.getTransaction().begin();
        entityManager.persist(specie);
        entityManager.flush();
        entityManager.getTransaction().commit();
        if (specie.getFilms() != null && specie.getFilms().size() > 0) {
            specie.setSpecie_film(specie.getFilms().parallelStream().map(urlLink -> {
                return (Film) ProcessEntity(urlLink);
            }).collect(Collectors.toList()));
        }
        if (specie.getPeople() != null && specie.getPeople().size() > 0) {
            specie.setSpecie_people(specie.getPeople().parallelStream().map(urlLink -> {
                return (People) ProcessEntity(urlLink);
            }).collect(Collectors.toList()));
        }
        specie.getFilms().clear();
        specie.getPeople().clear();
        entityManager.getTransaction().begin();
        entityManager.persist(specie);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public void PersistVehicleWithNestedCollections(Vehicle vehicle) {
        entityManager.getTransaction().begin();
        entityManager.persist(vehicle);
        entityManager.flush();
        entityManager.getTransaction().commit();
        if (vehicle.getFilms() != null && vehicle.getFilms().size() > 0) {
            vehicle.setVehicle_film(vehicle.getFilms().parallelStream().map(urlLink -> {
                return (Film) ProcessEntity(urlLink);
            }).collect(Collectors.toList()));
        }
        if (vehicle.getPeople() != null && vehicle.getPeople().size() > 0) {
            vehicle.setVehicle_people(vehicle.getPeople().parallelStream().map(urlLink -> {
                return (People) ProcessEntity(urlLink);
            }).collect(Collectors.toList()));
        }
        vehicle.getFilms().clear();
        vehicle.getPeople().clear();
        entityManager.getTransaction().begin();
        entityManager.persist(vehicle);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public void PersistStarshipWithNestedCollections(Starship starship) {
        entityManager.getTransaction().begin();
        entityManager.persist(starship);
        entityManager.flush();
        entityManager.getTransaction().commit();
        if (starship.getFilms() != null && starship.getFilms().size() > 0) {
            starship.setStarship_film(starship.getFilms().parallelStream().map(urlLink -> {
                return (Film) ProcessEntity(urlLink);
            }).collect(Collectors.toList()));
        }
        if (starship.getPeople() != null && starship.getPeople().size() > 0) {
            starship.setStarship_people(starship.getPeople().parallelStream().map(urlLink -> {
                return (People) ProcessEntity(urlLink);
            }).collect(Collectors.toList()));
        }
        starship.getFilms().clear();
        starship.getPeople().clear();
        entityManager.getTransaction().begin();
        entityManager.persist(starship);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    @Async
    public JsonNode ProcessURL(String urlLink) throws JsonProcessingException {
        RestTemplate rest = new RestTemplate();
        URI uri = UriComponentsBuilder
                .fromUriString(urlLink)
                .build(1);
        String entityJson = rest.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(entityJson);
    }

    public String EntityToJson(List<Object> entitiesList) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder builder = new StringBuilder();
        Object entity;
        builder.append("[");
        for (int i = 0; i < entitiesList.size(); i++) {
            entity = entitiesList.get(i);
            if (entity instanceof People) {
                builder.append(mapper.writeValueAsString(new PeopleDto((People) entity)));
                if (i < entitiesList.size() - 1) {
                    builder.append(",");
                }
            }
            if (entity instanceof Planet) {
                entity = entitiesList.get(i);
                builder.append(mapper.writeValueAsString(new PlanetDto((Planet) entity)));

                if (i < entitiesList.size() - 1) {
                    builder.append(",");
                }
            }
            if (entity instanceof Film) {
                builder.append(mapper.writeValueAsString(new FilmDto((Film) entitiesList.get(i))));
                if (i < entitiesList.size() - 1) {
                    builder.append(",");
                }
            }
            if (entity instanceof Vehicle) {
                builder.append(mapper.writeValueAsString(new VehicleDto((Vehicle) entitiesList.get(i))));
                if (i < entitiesList.size() - 1) {
                    builder.append(",");
                }
            }
            if (entity instanceof Starship) {
                builder.append(mapper.writeValueAsString(new StarshipDto((Starship) entitiesList.get(i))));
                if (i < entitiesList.size() - 1) {
                    builder.append(",");
                }
            }
            if (entity instanceof Specie) {
                builder.append(mapper.writeValueAsString(new SpecieDto((Specie) entitiesList.get(i))));
                if (i < entitiesList.size() - 1) {
                    builder.append(",");
                }
            }
        }
        builder.append("]");
        return builder.toString();

    }
}