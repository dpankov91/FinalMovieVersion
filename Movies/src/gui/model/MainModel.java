/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.model;

import be.Category;
import be.Movie;
import bll.BllInterface;
import bll.BllManager;
import bll.exceptions.bllException;
import gui.exceptions.ModelException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Vicneas
 */
public class MainModel {

    private static MainModel instance;
    private BllInterface bllManager;
    private ObservableList<Movie> movies;
    private ObservableList<Movie> allMovies;

    public MainModel() throws ModelException, bllException {
        bllManager = new BllManager();
        try {
            movies = FXCollections.observableArrayList(bllManager.getAllMovies());
        } catch (bllException e) {
            throw new ModelException(e.getMessage());
        }
    }

    public static MainModel createInstance() throws ModelException, bllException {
        if (instance == null) {
            instance = new MainModel();
        }
        return instance;
    }

    public ObservableList<Movie> getAllMovies() {
        return movies;
    }
            
    public void createMovie(String name, List<Category> categories, Integer userRating, int imdbRating, String filePath, LocalDate lastView) throws ModelException, bllException {
        LocalDate currentDate = LocalDate.now();
        Movie createdMovie = bllManager.createMovie(name, categories, userRating, imdbRating, filePath, lastView);
        movies.add(createdMovie);
    }

    void deleteCategoryFromAllMovies(Category category) {
        for (Movie movie : movies) {
            if (movie.removeCategory(category)) {

                updateMovieCategories(movie);
                updateListOfMovies(movie);
            }
        }
    }

    private void updateMovieCategories(Movie movie) {
        List<Category> categories = new ArrayList();
        for (Category category : movie.getCategories()) {
            categories.add(category);
        }
        movie.setCategories(categories);
    }

    private void updateListOfMovies(Movie movie) {
        int index = movies.indexOf(movie);
        movies.set(index, movie);
    }
        
    public void updateMovieLastView(Movie movie) throws ModelException, bllException
    {
            LocalDate lastView = LocalDate.now();
            bllManager.updateMovieLastView(movie, lastView);
    }

    public void deleteMovie(Movie movie) throws ModelException {
        try {
            bllManager.deleteMovie(movie);
            movies.remove(movie);
        } catch (bllException e) {
            throw new ModelException(e.getMessage());
        }
    }

    /*public ObservableList<Movie> getFilteredMovies(List<Movie> allMovies, String filter, Integer rating)
    {
        ObservableList<Movie> filteredMovies = FXCollections.observableArrayList(MovieSearcher.searchMoviesByName(allMovies));
        filteredMovies = FXCollections.observableArrayList(MovieSearcher.searchMoviesByName(filteredMovies, filter));
        return filteredMovies;
    }
    
    public ObservableList<Movie> getMoviesToDelete()
    {
        ObservableList<Movie> moviesToDelete = FXCollections.observableArrayList(MovieSearcher.searchMoviesWithLowerRating(movies, 4));
        moviesToDelete = FXCollections.observableArrayList(MovieSearcher.searchMoviesNotSeenRecently(moviesToDelete, 2));
        return moviesToDelete;
    } 
*/

    public void updateMovie(Movie movie, String name, List<Category> category, Integer userRating, Integer imdbRating, String filePath) throws ModelException, bllException
    {
        Movie updatedMovie = bllManager.updateMovie(movie, name, category, userRating,imdbRating,  filePath);
        updateListOfMovies(updatedMovie);    
    }

    public void createMovie(String name, List<Category> categories, Integer userRating, Integer imdbRating, String filePath) throws ModelException, bllException
    {
        LocalDate lastView = LocalDate.now();
        Movie createdMovie = bllManager.createMovie(name, categories, userRating, imdbRating, filePath, lastView);
        movies.add(createdMovie);
 }

    public ObservableList<Movie> getCurrentMovies() throws ModelException {
        return allMovies;
    }
}
 