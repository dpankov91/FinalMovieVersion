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
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Vicneas
 */
public class MainWindowController implements Initializable {

    @FXML
    private TextField searchTextBox;
    @FXML
    private TableView<Movie> tableCollectionView;
    @FXML
    private TableColumn<Movie, String> columnTitle;
    @FXML
    private TableColumn<Movie, String> columnCategory;
    @FXML
    private TableColumn<Movie, Integer> columnIMDBRating;
    @FXML
    private TableColumn<Movie, Integer> columnUserRating;
    @FXML
    private TableColumn<Movie, Date> columnLastView;
    @FXML
    private Button playButton;
    @FXML
    private Button rateMovie;
    @FXML
    private ChoiceBox<Integer> ratingChoice;

    private ObservableList<Movie> observableListMovie;
    private ObservableList<Category> observableListCategory;
    private MainModel mainModel;
    private CategoryModel categoryModel;

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }
    
    public MainWindowController() throws ModelException, bllException {
        mainModel = new MainModel();
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ratingChoice.setItems(FXCollections.observableArrayList(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        );
        
        tableCollectionView.setItems(mainModel.getAllMovies());

        try {

            categoryModel = CategoryModel.createInstance();
            mainModel = MainModel.createInstance();
        } catch (ModelException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (bllException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    @FXML
    private void createMovie(ActionEvent event) {
        try {
            loadAny("/gui/view/PopUpMovies.fxml");
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadAny(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void createNewEditCategory(ActionEvent event) {
        
        try {
            loadAny("/gui/view/PopUpCategory.fxml");
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void deleteMovie(ActionEvent event) {
        if (tableCollectionView.getSelectionModel().getSelectedIndex() != -1) {

            try {
                mainModel.deleteMovie(tableCollectionView.getSelectionModel().getSelectedItem());
            } catch (ModelException ex) {
                Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
            refreshMovieList();

        }
    }

    @FXML
    private void deleteCategory(ActionEvent event) {
        
        /*Category selectedCategory = categoryList.getSelectionModel().getSelectedItem();
            mainModel.deleteCategory(selectedCategory);
            categoryList.getSelectionModel().clearSelection();*/
        
    }

    @FXML
    private void playMovie(ActionEvent event) throws bllException, ModelException {
        try {
            play();
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void play() throws IOException, bllException, ModelException {
        Desktop.getDesktop().open(new File(tableCollectionView.getSelectionModel().getSelectedItem().getFilePath()));
        mainModel.updateMovieLastView(tableCollectionView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void search(KeyEvent event) {
        if (searchTextBox.getText() == null || searchTextBox.getText().length() <= 0) {
            refreshMovieList();
        } else {
            ObservableList<Movie> foundMovieList;
            try {
                foundMovieList = mainModel.search(mainModel.getCurrentMovies(), searchTextBox.getText());
                if (foundMovieList != null) {
                    tableCollectionView.setItems(foundMovieList);
                }
            } catch (ModelException ex) {
                setUpAlert(ex.getMessage());
            }
        }
    }

    @FXML
    private void rateMovie(ActionEvent event) {
        if (tableCollectionView.getSelectionModel().getSelectedIndex() != -1 && ratingChoice.getSelectionModel().getSelectedIndex() != -1) {
            Movie movieToBeRated = null;
            int movieIndex = 0;
            for (Movie movie : tableCollectionView.getItems()) {
                if (movie.getName().equals(tableCollectionView.getSelectionModel().getSelectedItem().getName())) {
                    movieToBeRated = tableCollectionView.getItems().get(movieIndex);
                    break;
                }
                movieIndex++;
            }
            if (movieToBeRated != null) {
                mainModel.updateMovieRating(movieToBeRated,movieIndex,ratingChoice.getSelectionModel().getSelectedItem());
                refreshMovieList();
            }
    }

}

   protected void refreshMovieList() {

            try {
                observableListMovie = mainModel.getCurrentMovies();
                tableCollectionView.setItems(observableListMovie);
            } catch (ModelException ex) {
                setUpAlert (ex.getMessage());
            }
        
   }


    
    
   protected void setUpAlert(String text) {
       Alert alert = new Alert(AlertType.INFORMATION);
       alert.setTitle("Alert");
       alert.setHeaderText(null);
       alert.setContentText(text);
       alert.showAndWait();
   }  

    
    
}
