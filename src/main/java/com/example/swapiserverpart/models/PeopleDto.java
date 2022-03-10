package com.example.swapiserverpart.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@JsonRootName(value="People")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeopleDto implements Serializable {
    private Long people_id;
    private String url;
    private String name;
    private String birth_year;
    private String gender;
    private String hair_color;
    private String eye_color;
    private String height;
    private String homeworld;
    private String mass;
    private String skin_color;
    private String created;
    private String edited;

    public PeopleDto(People p) {
        this.people_id = p.people_id;
        this.url = p.url;
        this.name = p.name;
        this.birth_year = p.birth_year;
        this.gender = gender;
        this.hair_color = p.hair_color;
        this.eye_color = p.eye_color;
        this.height = p.height;
        this.homeworld = p.homeworld;
        this.mass = p.mass;
        this.skin_color = p.skin_color;
        this.created = p.created;
        this.edited = p.edited;
        this.films = p.getPeople_film().stream()
                .map(f -> {
                    return new FilmDto(f);
                })
                .collect(Collectors.toList());

        this.species = p.getPeople_specie().stream()
                .map(s ->
                {
                    return new SpecieDto(s);

                })
                .collect(Collectors.toList());

        this.starships = p.getPeople_starship().stream()
                .map(s ->
                {
                    return new StarshipDto(s);

                })
                .collect(Collectors.toList());

        this.vehicles = p.getPeople_vehicle().stream()
                .map(v ->
                {
                    return new VehicleDto(v);

                })
                .collect(Collectors.toList());

    }
    List<FilmDto> films = new ArrayList<>();
    List<SpecieDto> species = new ArrayList<>();
    List<VehicleDto> vehicles = new ArrayList<>();
    List<StarshipDto> starships = new ArrayList<>();







    @Override
    public String toString() {
         return
                "people_id=" + people_id +
                ", url=" + url +
                ", name=" + name +
                ", birth_year=" + birth_year +
                ", gender=" + gender +
                ", hair_color=" + hair_color +
                ", eye_color=" + eye_color +
                ", height=" + height +
                ", homeworld=" + homeworld +
                ", mass=" + mass +
                ", skin_color=" + skin_color +
                ", created=" + created +
                ", edited=" + edited ;
    }
    String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);

    }

}
