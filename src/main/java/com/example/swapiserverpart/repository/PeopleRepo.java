package com.example.swapiserverpart.repository;

import com.example.swapiserverpart.models.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepo extends JpaRepository<People, Long> {
    List<People>findByNameStartingWithOrNameEndingWith(String nameStart,String nameEnd);
    List<People> findAllByNameIsContaining(String example);
}

