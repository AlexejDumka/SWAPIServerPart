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
public class StarshipDto implements Serializable {
    private Long starship_id;
    private String url;
    private String starship_class;
    private String cargo_capacity;
    private String passengers;
    private String consumables;
    private String cost_in_credits;
    private String created;
    private String crew;
    private String edited;
    private String hyperdrive_rating;
    private String length;
    private String manufacturer;
    private String max_atmosphering_speed;
    private String model;
    private String name;

    public StarshipDto(Starship s) {
        this.starship_id = s.starship_id;
        this.url = s.url;
        this.starship_class = s.starship_class;
        this.cargo_capacity = s.cargo_capacity;
        this.passengers = s.passengers;
        this.consumables = s.consumables;
        this.cost_in_credits = s.cost_in_credits;
        this.created = s.created.toString();
        this.crew = s.crew;
        this.edited = s.edited.toString();
        this.hyperdrive_rating = s.hyperdrive_rating;
        this.length = s.length;
        this.manufacturer = s.manufacturer;
        this.max_atmosphering_speed = s.max_atmosphering_speed;
        this.model = s.model;
        this.name = s.name;
        this.pilots  =s.getStarship_people().stream()
                .map(p ->
                {
                    return new PeopleDto(p);
                })
                .collect(Collectors.toList());
        this.films  =s.getStarship_film().stream()
                .map(f ->
                {
                    return new FilmDto(f);
                })
                .collect(Collectors.toList());
    }
    List<FilmDto> films = new ArrayList<>();
    List<PeopleDto> pilots = new ArrayList<>();
}
