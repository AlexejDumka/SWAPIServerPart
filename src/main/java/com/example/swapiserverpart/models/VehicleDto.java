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
public class VehicleDto implements Serializable {
    private Long vehicle_id;
    private String url;
    private String name;
    private String model;
    private String vehicle_class;
    private String manufacturer;
    private String cost_in_credits;
    private String length;
    private String crew;
    private String passengers;
    private String max_atmosphering_speed;
    private String cargo_capacity;
    private String consumables;
    private String created;
    private String edited;

    public VehicleDto(Vehicle v) {
        this.vehicle_id = v.vehicle_id;
        this.url = v.url;
        this.name = v.name;
        this.model = v.model;
        this.vehicle_class = v.vehicle_class;
        this.manufacturer = v.manufacturer;
        this.cost_in_credits = v.cost_in_credits;
        this.length = v.length;
        this.crew = v.crew;
        this.passengers = v.passengers;
        this.max_atmosphering_speed =v. max_atmosphering_speed;
        this.cargo_capacity = v.cargo_capacity;
        this.consumables = v.consumables;
        this.created = v.created;
        this.edited = v.edited;
        this.pilots  =v.getVehicle_people().stream()
                .map(p ->
                {
                    return new PeopleDto(p);
                })
                .collect(Collectors.toList());
        this.films  =v.getVehicle_film().stream()
                .map(f ->
                {
                    return new FilmDto(f);
                })
                .collect(Collectors.toList());
    }

    List<FilmDto> films = new ArrayList<>();
    List<PeopleDto> pilots = new ArrayList<>();
}
