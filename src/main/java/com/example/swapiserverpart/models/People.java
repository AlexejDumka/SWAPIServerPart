package com.example.swapiserverpart.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(
        name = "people",
        uniqueConstraints =  @UniqueConstraint(
                name = "pk_people_film_id",
                columnNames = {
                       "people_id",
                        "url"
                }
        )
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class People extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long people_id;
    String url;
    String name = null;
    String birth_year = null;
    String gender = null;
    String hair_color = null;
    String eye_color = null;
    String height = null;
    String homeworld = null;
    String mass = null;
    String skin_color = null;
    String created = null;
    String edited = null;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "film_id",
            foreignKey = @ForeignKey(name = "fk_people_film_id")
    )
    private List<Film> people_film = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "specie_id",
            foreignKey = @ForeignKey(name = "fk_people_specie_id")
    )
    private List<Specie> people_specie = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "vehicle_id",
            foreignKey = @ForeignKey(name = "fk_people_vehicle_id")
    )
    private List<Vehicle> people_vehicle = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "starship_id",
            foreignKey = @ForeignKey(name = "fk_people_starship_id")
    )
    private List<Starship> people_starship = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        People people = (People) o;
        return Objects.equals(url, people.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
