/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import be.Category;
import be.Movie;
import dal.exceptions.daoException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author chris
 */
public interface IDalFacade {


    public List<Movie> getAllMovies() throws daoException;

    public void deleteMovie(Movie movieToDelete) throws daoException;

    public List<Category> getallCategories() throws daoException;

    public Category createCategory(int ID, String name) throws daoException;

    public void deleteCategory(Category categoryToDelete) throws daoException;

    public void updateMovieLastView(Movie movie, LocalDate lastView) throws daoException;

    public Movie updateMovie(Movie movie, String name, List<Category> categories, Integer userRating, Integer imdbRating, String filePath) throws daoException;

    public Movie createMovie(String name, List<Category> categories, Integer userRating, Integer imdbRating, String filePath, LocalDate lastView) throws daoException;
    
}
