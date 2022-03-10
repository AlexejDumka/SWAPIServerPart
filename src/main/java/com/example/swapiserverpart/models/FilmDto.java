package com.example.swapiserverpart.models;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.jdbc.ClobProxy;

import javax.persistence.Lob;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@JsonRootName(value="Film")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilmDto implements Serializable {
    private Long film_id;
    private String url;
    private int episode_id;
    @Lob
    private String opening_crawl;
    private String director;
    private String producer;
    private  String release_date;
    private String created;
    private String edited;
    private String title;

    public FilmDto(Film f)  {
        this.film_id = f.film_id;
        this.url = f.url;
        this.episode_id = f.episode_id;
        this.opening_crawl = SetOpening_crawl(f.opening_crawl);
        this.director = f.director;
        this.producer = f.producer;
        this.release_date = release_date;
        this.created = f.created;
        this.edited = f.edited;
        this.title = f.title;
        this.characters  = f.getFilm_people().stream()
                .map(p ->
                {
                    return new PeopleDto(p);
                })
                .collect(Collectors.toList());
        this.vehicles  = f.getFilm_vehicle().stream()
                .map(vh ->
                {
                    return new VehicleDto(vh);
                })
                .collect(Collectors.toList());
        this.species  = f.getFilm_specie().stream()
                .map(s ->
                {
                    return new SpecieDto(s);
                })
                .collect(Collectors.toList());

        this.starships  = f.getFilm_starship().stream()
                .map(st ->
                {
                    return new StarshipDto(st);
                })
                .collect(Collectors.toList());
    }

    List<PeopleDto> characters = new ArrayList<>();
    List<SpecieDto> species = new ArrayList<>();
    List<VehicleDto> vehicles = new ArrayList<>();
    List<StarshipDto> starships = new ArrayList<>();
    public String SetOpening_crawl(Clob oq)  {
        try {
            Reader r = oq.getCharacterStream();
            int j = 0;
            StringBuilder buffer = new StringBuilder();
            int ch;
            while ((ch = r.read()) != -1) {
                buffer.append("").append((char) ch);
            }

            j++;
            return buffer.toString();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    }
