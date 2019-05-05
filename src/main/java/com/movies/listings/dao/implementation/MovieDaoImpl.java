package com.movies.listings.dao.implementation;

import com.movies.listings.dao.MovieDao;
import com.movies.listings.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class MovieDaoImpl  implements MovieDao {

    private final String SELECT_SQL = "SELECT * FROM dbo.Movie ORDER BY ReleaseYear DESC";
    private final String SELECT_SQL_BY_ID = "SELECT * FROM dbo.Movie WHERE MovieId = ? ORDER BY ReleaseYear DESC";
    private final String SELECT_SQL_BY_GEN_ID = "SELECT * FROM dbo.Movie WHERE GenreId = ? ORDER BY ReleaseYear DESC";

    private final String SELECT_SQL_BY_SEARCH = "SELECT * FROM dbo.Movie WHERE MovieName like :search or MainCelebrity like :search";
    private final String INSERT_SQL = "INSERT INTO Movie(MovieName,GenreId,Director,MainCelebrity,Certificate,ReleaseYear,StarRating) values(?,?,?,?,?,?,?)";
    private final String UPDATE_SQL = "UPDATE dbo.Movie SET MovieName = ?, GenreId = ?, Director = ?, MainCelebrity = ?, Certificate = ?, ReleaseYear = ?, StarRating = ? WHERE MovieId = ?";
    private final String DELETE_SQL_BY_ID = "DELETE FROM dbo.Movie WHERE MovieId = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Implement findAll() which retrieves all movies from the DB
    // MovieMapper() converts rows from the result into Movie objects
    public List<Movie> findAll() {
        return jdbcTemplate.query(SELECT_SQL, new MovieMapper());
    }

    public Movie findById(int id) {
        return jdbcTemplate.queryForObject(SELECT_SQL_BY_ID, new Object[]{id}, new MovieMapper());
    }

    public List<Movie> findByGenre(int id) {
        return jdbcTemplate.query(SELECT_SQL_BY_GEN_ID, new Object[]{id}, new MovieMapper());
    }

    //Update an existing movie
    public int update(final Movie movie) {
        // Update movie using values in movie object passed as a parameter
        // As this is an update a new primary key id is not required

        // The query requires 8 parameters which will be passed as an object
        Object[] params = {
                movie.getMovieName(),
                movie.getGenreId(),
                movie.getDirector(),
                movie.getMainCelebrity(),
                movie.getCertificate(),
                movie.getReleaseYear(),
                movie.getStarRating(),
                movie.getMovieId()
        };

        // execute the query using params, returning the number of rows affected
        return jdbcTemplate.update(UPDATE_SQL, params);
    }

    public Movie create(final Movie movie) {

        // A new Primary key (identity) value will be generated by the database on insert
        // This value is retrieved using KeyHolder
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            // Prepared statement replaces ? parameters with values
            // Create the statement and connect
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                // Set each parameter by index (of ? in SQL) and value
                ps.setString(1, movie.getMovieName());
                ps.setInt(2, movie.getGenreId());
                ps.setString(3, movie.getDirector());
                ps.setString(4, movie.getMainCelebrity());
                ps.setString(5, movie.getCertificate());
                ps.setInt(6, movie.getReleaseYear());
                ps.setInt(7, movie.getStarRating());

                // return the completed statement
                return ps;
            }
        }, holder);

        // Get the new id and assign it to the new movie object
        int newMovieId = holder.getKey().intValue();
        movie.setMovieId(newMovieId);

        // Return the newly created movie
        return movie;
    }

    // Delete a movie by id
    public int delete(int id) {
        // Use the delete sql, setting the id paramater
        // This method returns the number of rows affected
        return jdbcTemplate.update(DELETE_SQL_BY_ID, new Object[] {id});
    }

    // return a list of movies matching search term
    public List<Movie> findBySearchText(String searchText)  {
        // The named parameter template assigns values to the named parameters (as opposed to ?) in an SQL statement
        NamedParameterJdbcTemplate namedParamJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        // Set the :search parameter
        // % is a wildcard - the search term will be used to match files using the like operator
        // https://www.w3schools.com/SQL/sql_like.asp
        parameters.addValue("search", "%" + searchText + "%");

        // execute the query with named parameters
        // use MovieMapper() to process the resultset and return the resulting movie list
        return namedParamJdbcTemplate.query(SELECT_SQL_BY_SEARCH, parameters, new MovieMapper());
    }
}

class MovieMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie p = new Movie();
        p.setMovieId(rs.getInt("MovieId"));
        p.setGenreId(rs.getInt("GenreId"));
        p.setMovieName(rs.getString("MovieName"));
        p.setMainCelebrity(rs.getString("MainCelebrity"));
        p.setDirector(rs.getString("Director"));
        p.setCertificate(rs.getString("Certificate"));
        p.setReleaseYear(rs.getInt("ReleaseYear"));
        p.setStarRating(rs.getInt("StarRating"));
        return p;
    }
}
