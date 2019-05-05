package com.movies.listings.model;

public class Movie {

    private int movieId, genreId, releaseYear, starRating;
    private String movieName, director, mainCelebrity, certificate;

    //Constructors
    public Movie() {
    }

    public Movie(int movieId, int genreId, int releaseYear, int starRating, String movieName, String director, String mainCelebrity, String certificate) {
        this.movieId = movieId;
        this.genreId = genreId;
        this.releaseYear = releaseYear;
        this.starRating = starRating;
        this.movieName = movieName;
        this.director = director;
        this.mainCelebrity = mainCelebrity;
        this.certificate = certificate;
    }

    //Getters and Setters
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getMainCelebrity() {
        return mainCelebrity;
    }

    public void setMainCelebrity(String mainCelebrity) {
        this.mainCelebrity = mainCelebrity;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
}
