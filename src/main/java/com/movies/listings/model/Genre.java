package com.movies.listings.model;

public class Genre {
    private int genreId;
    private String genreType, genreDescription;

    //Constructors
    public Genre() {
    }

    public Genre(int genreId, String genreType, String genreDescription) {
        this.genreId = genreId;
        this.genreType = genreType;
        this.genreDescription = genreDescription;
    }

    //Getters and Setters
    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreType() {
        return genreType;
    }

    public void setGenreType(String genreType) {
        this.genreType = genreType;
    }

    public String getGenreDescription() {
        return genreDescription;
    }

    public void setGenreDescription(String genreDescription) {
        this.genreDescription = genreDescription;
    }
}
