/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import be.Category;
import be.Movie;
import bll.exceptions.bllException;
import dal.daos.CategoryDAO;
import dal.daos.MovieDAO;
import dal.exceptions.daoException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chris
 */
public class DalController implements IDalFacade {

    private MovieDAO mDao;
    private CategoryDAO cDao;

    public DalController() {
        mDao = new MovieDAO();
        cDao = new CategoryDAO();
    }

    @Override
    public List<Movie> getAllMovies() throws daoException {
        List<Movie> allMovies = null;
        try {
            allMovies = mDao.getAllMovies();
        } catch (Exception e) {
            throw new daoException(e.getMessage());
        }
        return allMovies;

    }

    @Override
    public void deleteMovie(Movie movieToDelete) throws daoException {
        try {
            mDao.deleteMovie(movieToDelete);
        } catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (daoException e) 
        {
                        throw new daoException(e.getMessage());
}
    }

    @Override
    public void updateMovieLastView(Movie movie, LocalDate lastView) throws daoException {
        try {
            mDao.updateMovieLastView(movie, lastView);;
        } 
        catch (daoException e) {

        } catch (Exception e) {
            throw new daoException(e.getMessage());
        }
    }

    @Override
    public List<Category> getallCategories() throws daoException {
        List<Category> allCategories = null;

        try {
            allCategories = cDao.getAllCategories();
        } catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new daoException(e.getMessage());
        }
        return allCategories;
    }

    @Override
    public Category createCategory(int ID, String name) throws daoException {
        Category category = null;

        try {
            category = cDao.createCategory(ID, name);
        } catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new daoException(e.getMessage());
        }
        return category;
    }

    @Override
    public void deleteCategory(Category categoryToDelete) throws daoException {
        try {
            cDao.deleteCategory(categoryToDelete);
        } catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Movie updateMovie(Movie movie, String name, List<Category> categories, Integer userRating, Integer imdbRating, String filePath) throws daoException
    {
        Movie updatedMovie = null;
        try 
        {
            updatedMovie = mDao.updateMovie(movie, name, categories, userRating, imdbRating, filePath);
        }  
        catch (SQLException ex) 
        {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return updatedMovie;        
    }

    @Override
    public Movie createMovie(String name, List<Category> categories, Integer userRating, Integer imdbRating, String filePath, LocalDate lastView) throws daoException 
    {
        Movie createdMovie = null;    
        createdMovie = mDao.createMovie(name, categories, userRating, imdbRating, filePath, lastView);
        return createdMovie;    
    }
}
