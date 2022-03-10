package com.example.swapiserverpart.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecieDto implements Serializable {
    private Long specie_id;
    private String url;
    private String name;
    private String homeworld;
    private String classification;
    private String designation;
    private String average_height;
    private String average_lifespan;
    private String eye_colors;
    private String hair_colors;
    private String skin_colors;
    private String language;
    private String created;
    private String edited;

    public SpecieDto(Specie s) {
        this.specie_id = s.specie_id;
        this.url = s.url;
        this.name = s.name;
        this.homeworld = s.homeworld;
        this.classification = s.classification;
        this.designation = s.designation;
        this.average_height = s.average_height;
        this.average_lifespan = s.average_lifespan;
        this.eye_colors = s.eye_colors;
        this.hair_colors = s.hair_colors;
        this.skin_colors = s.skin_colors;
        this.language = s.language;
        this.created = s.created;
        this.edited = s.edited;
        this.films = s.getSpecie_film().stream()
                .map(f -> {
                    return new FilmDto(f);
                })
                .collect(Collectors.toList());
        this.people = s.getSpecie_people().stream()
                .map(p -> {
                    return new PeopleDto(p);
                })
                .collect(Collectors.toList());
    }
    List<FilmDto> films = new ArrayList<>();
    List<PeopleDto> people = new ArrayList<>();
}
