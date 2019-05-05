package com.movies.listings.dao;

import com.movies.listings.model.Genre;

import java.util.List;

public interface GenreDao {
    // Return a list containing all the genre objects
    public List<Genre> findAll();

    // Return a genre with matching id
    public Genre findById(int id);

}//class


