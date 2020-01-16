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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dpank
 */
public class CategoryDAO {

    private DbConnectionProvider connector;
    private CatMovDAO mcDao;

    public List<Category> getAllCategories() throws SQLException {
        String sqlStatement = "SELECT * FROM Categories";
        List<Category> allCategories = new ArrayList();
        try ( Connection con = connector.getConnection();  PreparedStatement statement = con.prepareStatement(sqlStatement)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                allCategories.add(new Category(id, name));
            }
            return allCategories;
        }
    }

    public Category createCategory(int ID, String name) throws SQLException {
        String sqlStatement = "INSERT INTO Categories(ID, name) VALUES(?, ?)";
        try ( Connection con = connector.getConnection();  PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, ID);
            statement.setString(2, name);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            return new Category(ID, name);

        }
    }

    public void deleteCategory(Category categoryToDelete) throws SQLException {
        mcDao.deleteCategoryFromAllMovies(categoryToDelete);
        String sqlStatement = "DELETE FROM Categories WHERE ID=?";
        try ( Connection con = connector.getConnection();  PreparedStatement statement = con.prepareStatement(sqlStatement)) {
            statement.setInt(1, categoryToDelete.getID());
            statement.execute();
        }
    }

}
