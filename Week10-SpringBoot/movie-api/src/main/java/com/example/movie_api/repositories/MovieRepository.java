package com.example.movie_api.repositories;

import com.example.movie_api.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


//First thing inside the <> is the Entity
//Second thing is the data type of the primary key
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    //derived query
    //SELECT * FROM movies WHERE LOWER(title) LIKE '%LOWER(title)%'
    List<Movie> findByTitleContainingIgnoreCase(String title);
}
