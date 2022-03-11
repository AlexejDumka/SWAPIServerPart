package com.example.swapiserverpart.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Data
public class PlanetDto implements Serializable {
    private Long planet_id;
    private String url;
    private String name;
    private String rotation_period;
    private String orbital_period;
    private String diameter;
    private String climate;
    private String gravity;
    private String terrain;
    private String surface_water;
    private String population;
    private String edited;
    private String created;

    public PlanetDto(Planet p) {
        this.planet_id = p.planet_id;
        this.url = p.url;
        this.name = p.name;
        this.rotation_period = p.rotation_period;
        this.orbital_period = p.orbital_period;
        this.diameter = p.diameter;
        this.climate = p.climate;
        this.gravity = p.gravity;
        this.terrain = p.terrain;
        this.surface_water = p.surface_water;
        this.population = p.population;
        this.edited = p.edited.toString();
        this.created = p.created.toString();
        this.films  =p.getPlanet_film().stream()
                .map(f ->
                {
                    return new FilmDto(f);
                })
                .collect(Collectors.toList());
        this.residents  =p.getPlanet_people().stream()
                .map(pl ->
                {
                    return new PeopleDto(pl);
                })
                .collect(Collectors.toList());

    }
    List<FilmDto> films = new ArrayList<>();
    List<PeopleDto> residents = new ArrayList<>();

    String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);

    }}
