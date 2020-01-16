/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal.daos;

import be.Category;
import be.Movie;
import dal.DbConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author dpank
 */
public class CatMovDAO {

    private DbConnectionProvider connector;

    public CatMovDAO() {
        connector = new DbConnectionProvider();
    }

    void deleteCategoryFromAllMovies(Category categoryToDelete) throws SQLException {
        String sqlStatement = "DELETE FROM CatMovie WHERE categoryID=?";
        try ( Connection con = connector.getConnection();  PreparedStatement statement = con.prepareStatement(sqlStatement)) {
            statement.setInt(1, categoryToDelete.getID());
            statement.execute();
        }
    }

    void addCategoriesToMovie(List<Category> categories, Movie movie) throws SQLException {
        String sqlStatement = "INSERT INTO CatMovie(categoryID, movieID) VALUES(?,?)";
        try ( Connection con = connector.getConnection();  PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
            for (Category category : categories) {
                statement.setInt(1, category.getID());
                statement.setInt(2, movie.getID());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    public void deleteAllCategoriesFromMovie(Movie movie) throws SQLException {
        String sqlStatement = "DELETE FROM CatMovie WHERE movieID=?";
        try ( Connection con = connector.getConnection();  PreparedStatement statement = con.prepareStatement(sqlStatement)) {
            statement.setInt(1, movie.getID());
            statement.execute();
        }
    }

    void addCategoriesToAllMovies(List<Movie> allMovies) throws SQLException {
        String sqlStatement = "SELECT Movie.ID as movieID FROM CatMovie "
                + "INNER JOIN Movie ON CatMovie.movieID=Movie.ID "
                + "INNER JOIN Category ON CatMovie.categoryID=Category.ID ";
        try ( Connection con = connector.getConnection();  PreparedStatement statement = con.prepareStatement(sqlStatement)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                for (Movie m : allMovies) {
                    while (!rs.isAfterLast() && rs.getInt("movieID") == m.getID()) {
                        int id = rs.getInt("ID");
                        String name = rs.getString("name");
                        m.addCategory(new Category(id, name));
                        rs.next();
                    }
                }
            }
        }
    }
}
