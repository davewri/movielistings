package com.movies.listings.dao;

import com.movies.listings.model.Movie;

import java.util.List;

public interface MovieDao {
    // Return a list containing all the movies object
    public List<Movie> findAll();

    // Return a movie with matching id
    public Movie findById(int id);

    // return a list of movies in a category
    public List<Movie> findByGenre(int id);

    //Updating an existing movie - return the number of rows affected
    public int update(final Movie movie);

    public Movie create(final Movie movie);

    // Delete movie with matching id
    public int delete(int id);

    // return a list of movies matching search term
    public List<Movie> findBySearchText(String searchText);

}//class
