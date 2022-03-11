package com.example.swapiserverpart.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(
        name = "starship",
        uniqueConstraints =  @UniqueConstraint(
                name = "pk_starship_id",
                columnNames = {
                        "starship_id",
                        "url"
                }
        )
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Starship extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long starship_id;
    String url;
    String starship_class;
    String cargo_capacity;
    String passengers;
//    String MGLT;
    String consumables;
    String cost_in_credits;
    String created;
    String crew;
    String edited;
    String hyperdrive_rating;
    String length;
    String manufacturer;
    String max_atmosphering_speed;
    String model;
    String name;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "people_id",
            foreignKey = @ForeignKey(name = "fk_starship_people_id")
    )
    private List<People> starship_people = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "film_id",
            foreignKey = @ForeignKey(name = "fk_starship_film_id")
    )
    private List<Film> starship_film = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Starship starship = (Starship) o;
        return Objects.equals(url, starship.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
