package com.example.swapiserverpart.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.jdbc.ClobProxy;

import javax.persistence.*;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    String release_date;
    String created;
    String edited;
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
}

