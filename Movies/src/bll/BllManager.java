/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import be.Category;
import be.Movie;
import bll.exceptions.bllException;
import dal.DalController;
import dal.IDalFacade;
import dal.exceptions.daoException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpank
 */
public class BllManager implements BllInterface {

    private final IDalFacade dalController;

    public BllManager() 
    {
        dalController = new DalController();
    }

    @Override
    public List<Category> getAllCategories() throws bllException {
        try {
            return dalController.getallCategories();
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
    }

    @Override
    public List<Movie> getAllMovies() throws bllException {
        try {
            return dalController.getAllMovies();
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }

    }

    

    @Override
    public void deleteMovie(Movie movieToDelete) throws bllException {
        try {
            dalController.deleteMovie(movieToDelete);
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
    }

    @Override
    public Category createCategory(int ID, String name) throws bllException {
        try {
            return dalController.createCategory(ID, name);
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
    }

    @Override
    public void deleteCategory(Category categoryToDelete) throws bllException {
        try {
            dalController.deleteCategory(categoryToDelete);
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
    }

    @Override
    public void updateMovieLastView(Movie movie, LocalDate lastView) throws bllException 
    {
        try {    
            dalController.updateMovieLastView(movie, lastView);
        } catch (daoException ex) 
        {
            throw new bllException(ex.getMessage()); 
        }
    }

    @Override
    public Movie updateMovie(Movie movie, String movieName, List<Category> movieCategories, Integer userRating, Integer imdbRating, String filePath) throws bllException 
    {  
        try {
            return dalController.updateMovie(movie, movieName, movieCategories, userRating, imdbRating, filePath);
        } catch (daoException ex) 
        {
        throw new bllException(ex.getMessage());         
        }
    }
    

    @Override
    public Movie createMovie(String name, List<Category> categories, Integer userRating, Integer imdbRating, String filePath, LocalDate lastView) throws bllException 
    {
        try {    
            return dalController.createMovie(name, categories, userRating, imdbRating, filePath, lastView);
        } catch (daoException ex)
        {
           throw new bllException(ex.getMessage());         
        }
    }

}
