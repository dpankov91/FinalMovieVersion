/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers;

import be.Category;
import be.Movie;
import bll.exceptions.bllException;
import gui.exceptions.ModelException;
import gui.model.CategoryModel;
import gui.model.MainModel;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dpank
 */
public class PopUpMoviesController implements Initializable {
    
    private MainModel mainModel;
    private CategoryModel categoryModel;
    private boolean editing;
    private Movie editingMovie;

    @FXML
    private TextField txtName;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancle;
    @FXML
    private ComboBox<Category> cmbCategories;
    @FXML
    private ComboBox<String> cmbUserRating;
    @FXML
    private TextField txtFile;
    @FXML
    private ListView<Category> lstSelectedCategories;
    @FXML
    private ComboBox<String> cmbImdbRating;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        cmbCategories.setItems(categoryModel.getCategories());
        createRatingCombo();
        disableElements();
    }    
    
    private void createRatingCombo() 
    {
        ObservableList<String> ratings = FXCollections.observableArrayList();
        ratings.add("-");
        for(int i = 1; i <= 10; i++) 
        {
            ratings.add(Integer.toString(i));          
        }
        cmbUserRating.setItems(ratings);    
    }
    
    public void disableElements()
    {
        btnSave.setDisable(true);
        txtFile.setDisable(true);
    }
    
    @FXML
    private void clickSelectCategory(ActionEvent event) 
    {
        Category selectedCategory = cmbCategories.getSelectionModel().getSelectedItem();
        if(!lstSelectedCategories.getItems().contains(selectedCategory))
        {
            lstSelectedCategories.getItems().add(selectedCategory);
        }
        checkInputs();
    }

    @FXML
    private void clickChooseFilePath(ActionEvent event) 
    {
        FileChooser fileChooser = createMovieChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null)
        {
            txtFile.setText(selectedFile.getPath());
        }
        checkInputs();
    }
    
    private FileChooser createMovieChooser() 
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a movie");
        FileChooser.ExtensionFilter generalFilter = new FileChooser.ExtensionFilter("All Video Files", "*.mpeg4", "*.mp4", "*.m4a", "*.m4v");
        FileChooser.ExtensionFilter mpeg4Filter = new FileChooser.ExtensionFilter("MPEG4 (*.mpeg4)", "*.mpeg4");
        FileChooser.ExtensionFilter mp4Filter = new FileChooser.ExtensionFilter("MP4 (*.mp4, *.m4a, *.m4v)","*.mp4", "*.m4a", "*.m4v");
        fileChooser.getExtensionFilters().add(generalFilter);
        fileChooser.getExtensionFilters().add(mpeg4Filter);
        fileChooser.getExtensionFilters().add(mp4Filter);       
        return fileChooser;
    }
    
    private void checkInputs() 
    {
        if(txtName.getText().isEmpty() || txtFile.getText().isEmpty())
        {
            btnSave.setDisable(true);
        }
        else
        {
            btnSave.setDisable(false);
        }    
    }

    @FXML
    private void clickSave(ActionEvent event) throws ModelException, bllException 
    {
        String name = txtName.getText();
        List<Category> categories = new ArrayList();
        for(Category category : lstSelectedCategories.getItems())
        {
            categories.add(category);
        }
        String filePath = txtFile.getText();
        Integer userRating = parseRating((String) cmbUserRating.getSelectionModel().getSelectedItem());
        Integer imdbRating = parseRating((String) cmbImdbRating.getSelectionModel().getSelectedItem());
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        if(editing)
        {
            mainModel.updateMovie(editingMovie, name, categories, userRating, imdbRating, filePath);
        }
        else
        {
            try
            {
                mainModel.createMovie(name, categories, userRating, imdbRating, filePath);
            }
            catch(ModelException e)
            {
                throw new ModelException(e.getMessage());
            }
        }
        stage.close();
    }
    
    private Integer parseRating(String ratingInString) 
    {
            if(ratingInString == null || ratingInString.equals("-"))
        {
            return null;
        }
        else
        {
            return Integer.parseInt(ratingInString);
        }
    }

    @FXML
    private void clickCancel(ActionEvent event) 
    {
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickSelectUserRating(ActionEvent event) 
    {
        checkInputs();
    }

    @FXML
    private void clickSelectImdbRating(ActionEvent event) {
    }

    

}
