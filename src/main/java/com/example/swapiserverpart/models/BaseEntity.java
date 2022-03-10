package com.example.swapiserverpart.models;

import com.example.swapiserverpart.util.CommaDelimitedStringsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(
        name = "comma_delimited_strings",
        defaultForType = CommaDelimitedStringsType.class,
        typeClass = CommaDelimitedStringsType.class)
public class BaseEntity implements Serializable {

    @Type(type = "comma_delimited_strings")
    List<String> films = new ArrayList<>();
    @Type(type = "comma_delimited_strings")
    List<String> species = new ArrayList<>();
    @Type(type = "comma_delimited_strings")
    List<String> vehicles = new ArrayList<>();
    @Type(type = "comma_delimited_strings")
    List<String> starships = new ArrayList<>();
    @Type(type = "comma_delimited_strings")
    List<String> planets = new ArrayList<>();
    @Type(type = "comma_delimited_strings")
    List<String> people = new ArrayList<>();
    @Type(type = "comma_delimited_strings")
    List<String> characters = new ArrayList<>();
    @Type(type = "comma_delimited_strings")
    List<String> pilots = new ArrayList<>();
    @Type(type = "comma_delimited_strings")
    List<String> residents = new ArrayList<>();
     }
