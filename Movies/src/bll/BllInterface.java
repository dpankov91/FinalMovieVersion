/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import be.Category;
import be.Movie;
import bll.exceptions.bllException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author dpank
 */
public interface BllInterface {

//Gets a list of all categories.
public List<Category> getAllCategories() throws bllException;

//Gets a list of all movies.
public List<Movie> getAllMovies() throws bllException;

//Deletes movies.
public void deleteMovie(Movie movieToDelete) throws bllException;

//Creates category.
 public Category createCategory(int ID, String name) throws bllException;
 
 //Deletes category.
 public void deleteCategory(Category categoryToDelete) throws bllException;
 
public void updateMovieLastView(Movie movie, LocalDate lastView) throws bllException; 

public Movie updateMovie(Movie movie, String movieName, List<Category> movieCategories, Integer userRating, Integer imdbRating, String filePath) throws bllException;
    
public Movie createMovie(String name, List<Category> categories, Integer userRating, Integer imdbRating, String filePath, LocalDate lastView) throws bllException;;
 
}
