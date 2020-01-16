/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author dpank
 */
public class MainWindowController implements Initializable {

    @FXML
    private TextField searchTextBox;
    @FXML
    private TableView<?> tableCollectionView;
    @FXML
    private TableColumn<?, ?> columnTitle;
    @FXML
    private TableColumn<?, ?> columnCategory;
    @FXML
    private TableColumn<?, ?> columnIMDBRating;
    @FXML
    private TableColumn<?, ?> columnUserRating;
    @FXML
    private TableColumn<?, ?> columnLastView;
    @FXML
    private Button playButton;
    @FXML
    private Button rateMovie;
    @FXML
    private ChoiceBox<?> ratingChoice;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void createMovie(ActionEvent event) {
    }

    @FXML
    private void createNewEditCategory(ActionEvent event) {
    }

    @FXML
    private void deleteMovie(ActionEvent event) {
    }

    @FXML
    private void deleteCategory(ActionEvent event) {
    }

    @FXML
    private void playMovie(ActionEvent event) {
    }

    @FXML
    private void rateMovie(ActionEvent event) {
    }
    
}
