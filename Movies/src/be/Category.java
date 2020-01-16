/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be;

/**
 *
 * @author chris
 */
public class Category {

    private final int ID;
    private String name;

    public Category(int ID, String name) {
        this.ID = ID;
        this.name = name;

    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" + "ID=" + ID + ", name=" + name + '}';
    }

}
