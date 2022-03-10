package com.example.swapiserverpart.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(
        name = "specie",
        uniqueConstraints =  @UniqueConstraint(
                name = "pk_specie_id",
                columnNames = {
                        "specie_id",
                        "url"
                }
        )
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Specie extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long specie_id;
    String url;
    String name;
    String homeworld;
    String classification;
    String designation;
    String average_height;
    String average_lifespan;
    String eye_colors;
    String hair_colors;
    String skin_colors;
    String language;
    String created;
    String edited;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "people_id",
            foreignKey = @ForeignKey(name = "fk_specie_people_id")
    )
    private List<People> specie_people = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "film_id",
            foreignKey = @ForeignKey(name = "fk_specie_film_id")
    )
    private List<Film> specie_film = new ArrayList<>();
}
