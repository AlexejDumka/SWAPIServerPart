package com.example.swapiserverpart.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.jdbc.ClobProxy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Table(
        name = "film",
        uniqueConstraints =  @UniqueConstraint(
                name = "pk_film_id",
                columnNames = {
                        "film_id",
                        "url"
                }
        )
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Film extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long film_id;
    String url;
    int episode_id;
    @Lob
    Clob opening_crawl;
    String director;
    String producer;
    Date release_date;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    Date created;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    Date edited;
    String title;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "people_id",
            foreignKey = @ForeignKey(name = "fk_film_people_id")
    )
    private List<People> film_people = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "specie_id",
            foreignKey = @ForeignKey(name = "fk_film_specie_id")
    )
    private List<Specie> film_specie = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "vehicle_id",
            foreignKey = @ForeignKey(name = "fk_film_vehicle_id")
    )
     private List<Vehicle> film_vehicle = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "starship_id",
            foreignKey = @ForeignKey(name = "fk_film_starship_id")
    )
     private List<Starship> film_starship = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "planet_id",
            foreignKey = @ForeignKey(name = "fk_film_planet_id")
    )
    private List<Planet> film_planet = new ArrayList<>();
    public void setOpening_crawl(String opening_crawl) {
        this.opening_crawl = ClobProxy.generateProxy(opening_crawl);
    }

    public String getOpening_crawl() throws SQLException, IOException {
        Reader r = opening_crawl.getCharacterStream();
        int j = 0;
        StringBuffer buffer = new StringBuffer();
        int ch;
        while ((ch = r.read())!=-1) {
            buffer.append(""+(char)ch);
        }

        j++;
        return buffer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return episode_id == film.episode_id && Objects.equals(film_id, film.film_id) && Objects.equals(url, film.url) && Objects.equals(opening_crawl, film.opening_crawl) && Objects.equals(director, film.director) && Objects.equals(producer, film.producer) && Objects.equals(release_date, film.release_date) && Objects.equals(created, film.created) && Objects.equals(edited, film.edited) && Objects.equals(title, film.title) && Objects.equals(film_people, film.film_people) && Objects.equals(film_specie, film.film_specie) && Objects.equals(film_vehicle, film.film_vehicle) && Objects.equals(film_starship, film.film_starship) && Objects.equals(film_planet, film.film_planet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}

