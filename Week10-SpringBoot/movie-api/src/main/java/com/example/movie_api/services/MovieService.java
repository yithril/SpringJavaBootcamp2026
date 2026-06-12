package com.example.movie_api.services;

import com.example.movie_api.entities.Movie;
import com.example.movie_api.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    //Dependency Injection
    @Autowired
    private MovieRepository movieRepository;

    //Get All Movies
    public List<Movie> getAllMovies(){
        List<Movie> movieList = movieRepository.findAll();

        return movieList;
    }

    public List<Movie> searchMovies(String title){
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public Optional<Movie> getMovieById(long id){
        var movie = movieRepository.findById(id);

        return movie;
    }

    public boolean delete(Long id){
        Optional<Movie> movieToDelete = movieRepository.findById(id);

        if(movieToDelete.isEmpty()){
            return false;
        }

        movieRepository.delete(movieToDelete.get());
        return true;
    }

    public Movie create(Movie movie){
        Movie newMovie = movieRepository.save(movie);

        return newMovie;
    }

    public Movie update(Long id, Movie movie){
        //look to see if there even IS a movie with that id
        Optional<Movie> updateMovie = movieRepository.findById(id);

        if(updateMovie.isEmpty()){
            return null;
        }

        //we are now going to update everything to the new values
        Movie movieToUpdate = updateMovie.get();

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setSynopsis(movie.getSynopsis());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setRunTimeInMinutes(movie.getRunTimeInMinutes());
        movieToUpdate.setLanguage(movie.getLanguage());

        movieRepository.save(movieToUpdate);

        return movieToUpdate;
    }
}
