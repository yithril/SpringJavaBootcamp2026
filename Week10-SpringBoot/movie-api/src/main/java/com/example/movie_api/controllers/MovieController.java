package com.example.movie_api.controllers;

import com.example.movie_api.entities.Movie;
import com.example.movie_api.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    //other way of doing dependency injection
    private MovieService movieService;

    //technically speaking, if you write the constructor out
    //the annotation is optional
    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    //GET /api/movies
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(
            @RequestParam(value = "title", required = false) String title
    ){
        if(title == null){
            List<Movie> movies = this.movieService.getAllMovies();

            return new ResponseEntity<>(movies, HttpStatus.OK);
        }
        else{
           List<Movie> movies = this.movieService.searchMovies(title);
            return new ResponseEntity<>(movies, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id){
        Optional<Movie> movie = this.movieService.getMovieById(id);

        //Either there is a movie with this id, or there isn't
        if(movie.isPresent()){
            return new ResponseEntity<>(movie.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        boolean deleteSuccessful = this.movieService.delete(id);

        if(!deleteSuccessful){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            //Successful deletes do NOT return 200, they return 204 No Content
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //The valid annotation will return a 400 Bad request if they messed up the data
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody @Valid Movie movie){
        Movie newMovie = this.movieService.create(movie);

        //Successful creation is NOT 200, it is 201
        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@RequestBody @Valid Movie movie,
                                             @PathVariable Long id){
        Movie updatedMovie = this.movieService.update(id, movie);

        if(updatedMovie == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
        }
    }
}
