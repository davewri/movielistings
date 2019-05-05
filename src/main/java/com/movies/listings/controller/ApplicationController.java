package com.movies.listings.controller;

import com.movies.listings.dao.GenreDao;
import com.movies.listings.dao.MovieDao;
import com.movies.listings.model.Genre;
import com.movies.listings.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ApplicationController {

    @Autowired
    private GenreDao genreData;

    @Autowired
    private MovieDao movieData;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    //@ResponseBody // Send a direct response without a view template
    public String index(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    //@ResponseBody // Send a direct response without a view template
    public String about() {
        //Load and return the about view
        return "about";
    }

    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public String movie(@RequestParam(name = "gen", required = false, defaultValue = "0") String gen, Model model){
        List<Movie> movies;

        // Initialise id (default value used to get all movies)
        int id = 0;

        // The parameter may be not be valid - which could crash the application
        // This trys to parse the string converting it to an it
        // If successfull id will be assigned the dep value
        // Otherwise - catch any exception
        // If it fails (i.e an exception occurs) id value will not be changed (from 0).
        try {
            id = Integer.parseInt(gen);
        }
        catch(NumberFormatException e) {
            System.out.println("Bad input for dep id: " + e);
        }

        // If id is 0 then get all movies otherwise get movies for genre id
        if (id == 0) {
            // Get the movie list from the MovieDao instance
            movies = movieData.findAll();
        } else {
            movies = movieData.findByGenre(id);
        }


        // Get the genre list from the GenreDao instance
        List<Genre> genre = genreData.findAll();

        model.addAttribute("movies", movies);
        model.addAttribute("genres", genre);

        // Return the view
        return "movie";
    }

    // The newMovie page will be accessed using /updateMovie from the browser
    @RequestMapping(value = "/updateMovie", method = RequestMethod.GET)
    public String updateMovie(@RequestParam(name = "id", required = true) String pId, Model model) {
        Movie movie;
        // Initialise id (default value used to get all movie)
        int id = 0;
        // The parameter may be not be valid - which could crash the application // This trys to parse the string converting it to an it
        // If successfull id will be assigned the dep value
        // Otherwise - catch any exception
        // If it fails (i.e an exception occurs) id value will not be changed (from 0).
        try {
            id = Integer.parseInt(pId);
        }
        catch(NumberFormatException e) {
            System.out.println("Bad input for id: " + e); }
        // If id is 0 then get all movies otherwise get movies for genre id
        if (id == 0) {
            // movie id=0 does not exist - return to movie list
            return "redirect:/movie";
        } else {
            // Otherwise find the movie matching the id movie = movieData.findById(id);
            movie = movieData.findById(id);
        }
        // add movie to the model
        model.addAttribute("movie", movie);
        // Get a list of genres and add to the model
        List<Genre> genres = genreData.findAll();
        model.addAttribute("genres", genres);
        // Return the updateMovie view

        return "updateMovie";
    }

    // Handle form submit via HTTP POST
    @RequestMapping(value = "/updateMovie", method = RequestMethod.POST) // Form data will be supplied as a filled in Movie object
    public String editMovie(Movie movie) {
        // Use the Dao to update the movie
        // To do: check for errors and return to form if any found
        // https://www.journaldev.com/2668/spring-validation-example-mvc-validator
        int rows = movieData.update(movie);
        // output result in server side console
        System.out.println(rows + " rows were updated");
        // Redirect back to the movies list
        return "redirect:/movie";
    }

    // The newMovie page will be accessed using /newMovie from the browser
    @RequestMapping(value = "/newMovie", method = RequestMethod.GET)
    public String newMovie(Model model) {

        // add empty movie to the model
        model.addAttribute("movie", new Movie());

        // Get a list of genres and add to the model
        List<Genre> genres = genreData.findAll();
        model.addAttribute("genres", genres);

        // Return the view
        return "newMovie";
    }

    // Handle form submit via HTTP POST
    @RequestMapping(value = "/newMovie", method = RequestMethod.POST)
    // Form data will be supplied as a filled in Movie object
    public String createMovie(Movie movie, RedirectAttributes redirAttrs) {

        // Use the Dao to create the new movie
        // To do: check for errors and return to form if any found
        // https://www.journaldev.com/2668/spring-validation-example-mvc-validator
        Movie newMovie = movieData.create(movie);

        if (newMovie != null) {
            redirAttrs.addFlashAttribute("message", "New movie added - id: " + newMovie.getMovieId());
            System.out.println("New movie was added " + newMovie.getMovieName());
        }
        else {
            redirAttrs.addFlashAttribute("error", "error: movie not added");
        }

        // Redirect back to the movies list
        // To do: Open a page showing the new movie in its own page
        return "redirect:/movie";
    }

    // Delete Movie
    @RequestMapping(value = "/deleteMovie", method = RequestMethod.GET)
    public String deleteMovie(@RequestParam(name = "id", required = true) String pId, RedirectAttributes redirAttrs) {

        // Initialise id (default value used to get all movies)
        int id = 0;

        // The parameter may be not be valid - which could crash the application
        // This trys to parse the string converting it to an it
        // If successfull id will be assigned the cat value
        // Otherwise - catch any exception
        // If it fails (i.e an exception occurs) id value will not be changed (from 0).
        try {
            id = Integer.parseInt(pId);
        }
        catch(NumberFormatException e) {
            System.out.println("Bad input for id: " + e);
        }
        // If id value is greater than 0 then delete - otherwise error
        if (id != 0) {
            int rows = movieData.delete(id);

            // Verify that something was deleted (rows affected > 1)
            if (rows >= 1) {
                // Set a flash message confirming the delete
                redirAttrs.addFlashAttribute("message", rows + " rows deleted");
                System.out.println(rows + " rows deleted");
            }
            else  {
                // Nothing deleted - set error flash message
                redirAttrs.addFlashAttribute("error", "Error: Movie delete failed");
            }
        }
        else {
            // can't delete id = 0 - show error
            redirAttrs.addFlashAttribute("error", "Nothing to delete");
        }

        // Return to movies page
        return "redirect:/movie";
    }

    // This method displays the movie page
    @RequestMapping(value = "/searchMovies", method = RequestMethod.GET)
    // Uses a Model instance - which will be passed to a view
    // cat parameter is for genre id
    public String searchMovie(@RequestParam(name = "search", required = false, defaultValue = "") String search, Model model) {

        // If search is blank then redirect to the movies page
        if (search == "") {
            return "redirect:/movies";
        }

        // Do the search and get the results
        List<Movie> movie = movieData.findBySearchText(search);

        // Get all genres
        List<Genre> genres = genreData.findAll();

        model.addAttribute("movies", movie);
        model.addAttribute("genres", genres);

        // Return the view
        return "movie";
    }
}
