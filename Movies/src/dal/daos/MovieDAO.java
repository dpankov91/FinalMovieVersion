/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal.daos;

import be.Category;
import be.Movie;
import dal.DbConnectionProvider;
import dal.exceptions.daoException;
import java.sql.Statement;
import java.sql.Date;
import java.util.List;
import java.sql.Types;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class MovieDAO {

    private DbConnectionProvider connector;
    private CatMovDAO mcDao;

    public MovieDAO() {

        connector = new DbConnectionProvider();
        mcDao = new CatMovDAO();
    }

    public Movie createMovie(int ID, String name, List<Category> categories, Integer userRating, int imdbRating, String filePath, LocalDate lastView) throws daoException {

        String sqlStatement = "INSERT INTO Movie(name, userRating, imdbRating, filePath, lastView) VALUES (?,?,?,?,?)";
        try ( Connection con = connector.getConnection();  PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, ID);
            statement.setString(2, name);
            if (userRating == null) {
                statement.setNull(3, Types.INTEGER);
            } else {
                statement.setInt(3, userRating);
            }
            statement.setInt(4, imdbRating);
            statement.setString(5, filePath);
            statement.setDate(6, Date.valueOf(lastView));
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();

            Movie movie = new Movie(ID, name, categories, userRating, imdbRating, filePath, lastView);
            mcDao.addCategoriesToMovie(categories, movie);
            return movie;
        } catch (SQLException ex) {
            throw new daoException("Cannot execute query");
        }

    }

    public List<Movie> getAllMovies() throws daoException {

        String sqlStatement = "SELECT * FROM Movie";
        List<Movie> allMovies = new ArrayList();
        try ( Connection con = connector.getConnection();  PreparedStatement statement = con.prepareStatement(sqlStatement)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String name = rs.getString("name");
                int userRating = rs.getInt("userRating");
                int imdbRating = rs.getInt("imdbRating");
                String filePath = rs.getString("filePath");
                LocalDate lastView = rs.getDate("lastView").toLocalDate();
                allMovies.add(new Movie(name, userRating, imdbRating, filePath, lastView));
            }
            rs.close();
            statement.close();
            mcDao.addCategoriesToAllMovies(allMovies);
            return allMovies;
        } catch (SQLException ex) {
            throw new daoException("Cannot execute query");
        }

    }

    public void deleteMovie(Movie movieToDelete) throws SQLException, daoException {

        mcDao.deleteAllCategoriesFromMovie(movieToDelete);
        String sqlStatement = "DELETE FROM Movie WHERE ID=?";
        try ( Connection con = connector.getConnection();  PreparedStatement statement = con.prepareStatement(sqlStatement)) {
            statement.setInt(1, movieToDelete.getID());
            statement.execute();
        } catch (SQLException ex) {
            throw new daoException("Cannot execute query");
        }
    }

    public void updateMovieLastView(Movie movie, LocalDate lastView) throws daoException {

        String sqlStatement = "UPDATE Movie SET lastView=? WHERE ID=?";
        try ( Connection con = connector.getConnection();  PreparedStatement statement = con.prepareStatement(sqlStatement)) {
            statement.setDate(1, Date.valueOf(lastView));
            statement.setInt(2, movie.getID());
            statement.execute();

        } catch (SQLException ex) {
            throw new daoException("Cannot execute query");
        }
    }

    public Movie updateMovie(Movie movie, String movieName, List<Category> movieCategories, Integer userRating, String filePath) throws SQLException 
    {
        String sqlStatement = "UPDATE Movies SET name=?, category=?, userRating=?, filePath=?; WHERE id=?";
        try(Connection con = connector.getConnection();
                PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            statement.setString(1, movieName);
            if(userRating == null)
            {
                statement.setNull(4, Types.INTEGER);
            }
            else
            {
                statement.setInt(4, userRating);
            }
            statement.execute();
            movie.setTitle(movieName);
            movie.setRating(userRating);
            movie.setPath(filePath);
            mcDao.deleteAllCategoriesFromMovie(movie);
            mcDao.addCategoriesToMovie(movie.getCategories(), movie);
            movie.setCategories(movie.getCategories());
            return movie;
        }    
    }

    public Movie createMovie(String name, List<Category> categories, Integer userRating, Integer imdbRating, String filePath, LocalDate lastView) throws daoException 
    {
        String sqlStatement = "INSERT INTO Movie(name, userRating, imdbRating, filePath, lastView) VALUES (?,?,?,?,?)";
        try(Connection con = connector.getConnection();
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS))
            
            {
            statement.setString(1, name);
            if(userRating == null) {
                statement.setNull(2, Types.INTEGER);
            }
            else {
                statement.setInt(2, userRating);
            }
            statement.setInt(3, imdbRating);
            statement.setString(4, filePath);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            
            Movie movie = new Movie(name, categories, userRating, imdbRating, filePath);
            mcDao.addCategoriesToMovie(categories, movie);
            return movie;
        } catch (SQLException ex) {
            throw new daoException("Cannot execute query");
        }    
    }

    public Movie updateMovie(Movie movie, String name, List<Category> categories, Integer userRating, Integer imdbRating, String filePath) throws SQLException
    {
        String sqlStatement = "UPDATE Movies SET name=?, category=?, userRating=?, imdbRating=?, filePath=?; WHERE id=?";
        try(Connection con = connector.getConnection();
                PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            statement.setString(1, name);
            if(userRating == null)
            {
                statement.setNull(4, Types.INTEGER);
            }
            else
            {
                statement.setInt(4, userRating);
            }
            statement.execute();
            movie.setTitle(name);
            movie.setRating(userRating);
            movie.setPath(filePath);
            mcDao.deleteAllCategoriesFromMovie(movie);
            mcDao.addCategoriesToMovie(movie.getCategories(), movie);
            movie.setCategories(movie.getCategories());
            return movie;
        }    
    }
}
