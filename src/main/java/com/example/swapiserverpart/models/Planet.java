package com.example.swapiserverpart.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Table(
        name = "planet",
        uniqueConstraints =  @UniqueConstraint(
                name = "pk_planet_id",
                columnNames = {
                        "planet_id",
                        "url"
                }
        )
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Planet  extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long planet_id;
    String url;
    String name;
    String rotation_period;
    String orbital_period;
    String diameter;
    String climate;
    String gravity;
    String terrain;
    String surface_water;
    String population;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    Date edited;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    Date created;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "people_id",
            foreignKey = @ForeignKey(name = "fk_planet_people_id")
    )
    private List<People> planet_people = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "film_id",
            foreignKey = @ForeignKey(name = "fk_planet_film_id")
    )
    private List<Film> planet_film = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return url.equals(planet.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
