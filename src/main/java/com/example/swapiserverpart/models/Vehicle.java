package com.example.swapiserverpart.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(
        name = "vehicle",
        uniqueConstraints =  @UniqueConstraint(
                name = "pk_vehicle_id",
                columnNames = {
                        "vehicle_id",
                        "url"
                }
        )
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehicle extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long vehicle_id;
    String url;
    String name;
    String model;
    String vehicle_class;
    String manufacturer;
    String cost_in_credits;
    String length;
    String crew;
    String passengers;
    String max_atmosphering_speed;
    String cargo_capacity;
    String consumables;
    String created;
    String edited;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "people_id",
            foreignKey = @ForeignKey(name = "fk_vehicle_people_id")
    )
    private List<People> vehicle_people = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "film_id",
            foreignKey = @ForeignKey(name = "fk_vehicle_film_id")
    )
    private List<Film> vehicle_film = new ArrayList<>();
}
