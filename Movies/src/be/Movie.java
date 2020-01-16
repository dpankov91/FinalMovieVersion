/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author chris
 */
public class Movie {

    private int ID;
    private String name;
    private int userRating;
    private final int imdbRating;
    private String filePath;
    private LocalDate lastView;
    private List<Category> categories;


    public Movie(String name, List<Category> categories, int userRating, int imdbRating, String filePath) {
        
        this.name = name;
        this.categories = categories;
        this.userRating = userRating;
        this.imdbRating = imdbRating;
        this.filePath = filePath;
    }

    // Constructor with parameters for Movie object
    public Movie(String name, List<Category> categories, int userRating, int imdbRating, String filePath, LocalDate lastView) 
    {
        this.name = name;
        this.categories = categories;
        this.userRating = userRating;
        this.imdbRating = imdbRating;
        this.filePath = filePath;
        this.lastView = lastView;
    }

    public Movie(String name, int userRating, int imdbRating, String filePath, LocalDate lastView)
    {
        this.name = name;
        this.userRating = userRating;
        this.imdbRating = imdbRating;
        this.filePath = filePath;
        this.lastView = lastView;
    }

    public Movie(int ID, String name, List<Category> categories, Integer userRating, int imdbRating, String filePath, LocalDate lastView) 
    {
        this.ID=ID;
        categories = new ArrayList();
        this.name = name;
        this.userRating = userRating;
        this.imdbRating = imdbRating;
        this.filePath = filePath;
        this.lastView = lastView;
        
    }

    public Movie(int ID, String name, List<Category> categories, Integer userRating, int imdbRating, String filePath, java.sql.Date lastView) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getID() {
        return ID;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public int getImdbRating() {
        return imdbRating;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String path) {
        this.filePath = filePath;
    }

    public LocalDate getLastView() {
        return lastView;
    }

    public void setLastView(LocalDate lastView) {
        this.lastView = lastView;
    }

    @Override
    public String toString() {
        return "Movie{" + "name=" + name + ", userRating=" + userRating + ", imdbRating=" + imdbRating + ", lastView=" + lastView + '}';
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public boolean removeCategory(Category category) {
        return categories.remove(category);
    }

    public void setTitle(String movieName) 
    {
        this.name = movieName;
    }

    public void setRating(Integer userRating) 
    {
        this.userRating = userRating;    
    }

    public void setPath(String filePath) 
    {
        this.filePath = filePath;
    }
}
