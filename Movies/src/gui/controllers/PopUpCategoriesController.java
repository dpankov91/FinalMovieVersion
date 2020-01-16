/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers;

import be.Category;
import bll.exceptions.bllException;
import gui.exceptions.ModelException;
import gui.model.CategoryModel;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;        

/**
 * FXML Controller class
 *
 * @author Vicneas
 */
public class PopUpCategoriesController implements Initializable {

    @FXML
    private Label specificFunctionLabel;
    @FXML
    private TextField categoryNameField;

    private CategoryModel categoryModel;
    private boolean isEditing = false;
    private Category editingList;
    private int categoryIndex;
    MainWindowController controller1;
    private ObservableList<Category> observableListCategory;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            categoryModel = CategoryModel.createInstance();
        } catch (ModelException ex) {
            Logger.getLogger(PopUpCategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (bllException ex) {
            Logger.getLogger(PopUpCategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        observableListCategory = categoryModel.getCurrentCategories(); //Loads all movies
    }
        

    @FXML
    private void saveCategoryName(ActionEvent event) throws ModelException {
        boolean isFound = false;
        String name = categoryNameField.getText().trim(); //Eliminates all white spaces (fron and back of the string)
        if (name != null && name.length() > 0 && name.length() < 50) { //If the string is not null and doesnt excede the databases char length
            for (Category category : observableListCategory) {
                if (category.getName() == null ? name == null : category.getName().equals(name)) {
                    isFound = true;
                    break;
                }
            }
        }
        }

    @FXML
    private void goBackFromCategory(ActionEvent event) {
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        stage.close();
    }
    
    void setInfo(Category selectedItem, int categoryIndex) {
        isEditing = true;
        editingList = selectedItem;
        categoryNameField.setText(selectedItem.getName());
        this.categoryIndex = categoryIndex;
    }
        
    void setController(MainWindowController controller1) {
        this.controller1 = controller1;
        if (isEditing) {
            specificFunctionLabel.setText("Create Category");
        }
    }
    
}
