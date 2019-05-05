package com.movies.listings.dao.implementation;

import com.movies.listings.dao.GenreDao;
import com.movies.listings.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GenreDaoImpl implements GenreDao {

    // SQL for selecting Genres
    private final String SELECT_SQL = "SELECT * FROM dbo.Genre";
    private final String SELECT_SQL_BY_ID = "SELECT * FROM dbo.Genre WHERE GenreId = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Implement findAll() which retrieves all Categorys from the DB
    // CategoryMapper() converts rows from the result into Category objects
    public List<Genre> findAll() {

        // Use jdbcTemplate to execute query
        // Then use GenreMapper to crate a category object for each row in the query result
        return jdbcTemplate.query(SELECT_SQL, new GenreMapper());
    }

    // Return a single Category matching id
    public Genre findById(int id) {
        return jdbcTemplate.queryForObject(SELECT_SQL_BY_ID, new Object[]{id}, new GenreMapper());
    }
}

// This class converts resultset rows (from the sql execution) into Java objects
class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        Genre c = new Genre();
        c.setGenreId(rs.getInt("GenreId"));
        c.setGenreType(rs.getString("GenreType"));
        c.setGenreDescription(rs.getString("GenreDescription"));
        return c;
    }
}
