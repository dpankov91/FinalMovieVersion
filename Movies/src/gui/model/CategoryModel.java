/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import be.Category;
import bll.BllInterface;
import bll.BllManager;
import bll.exceptions.bllException;
import gui.exceptions.ModelException;

/**
 *
 * @author dpank
 */
public class CategoryModel {

    private static CategoryModel instance;
    private final BllInterface bllManager;
    private ObservableList<Category> categories;
    private final MainModel mainModel;
    private ObservableList<Category> allCategory;

    private CategoryModel() throws ModelException, bllException {
        bllManager = new BllManager();

        try {
            categories = FXCollections.observableArrayList(bllManager.getAllCategories());
        } catch (bllException ex) {
            throw new ModelException(ex.getMessage());
        }
        mainModel = MainModel.createInstance();
    }

    public static CategoryModel createInstance() throws ModelException, bllException {
        if (instance == null) {
            instance = new CategoryModel();
        }
        return instance;
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }

    public void createCategory(int ID, String name) throws ModelException, bllException {
        if (isCategoryExisting(name)) {
            throw new ModelException(name + " is already in your categories");
        }
        Category createdCategory = bllManager.createCategory(ID, name);
        categories.add(createdCategory);
    }

    private boolean isCategoryExisting(String name) {
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void deleteCategory(Category category) throws ModelException, bllException {
        try {
            bllManager.deleteCategory(category);
            mainModel.deleteCategoryFromAllMovies(category);
            categories.remove(category);
        } catch (bllException e) {
            throw new ModelException(e.getMessage());
        }
    }
    
    public ObservableList<Category> getAllCategories() throws ModelException{
        allCategory = FXCollections.observableArrayList();
        try{
            allCategory.addAll(bllManager.getAllCategories());
        } catch (bllException ex) {
            throw new ModelException(ex.getMessage());
    }
    return allCategory;
}

    public ObservableList<Category> getCurrentCategories() {
        return allCategory;
    }
}
    

