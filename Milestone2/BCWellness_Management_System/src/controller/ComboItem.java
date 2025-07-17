/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author tarina
 */
public class ComboItem {
    //this is a helperclass for the counselor combobox
    private final int id;
    private final String displayText;

    public ComboItem(int id, String displayText) {
        this.id = id;
        this.displayText = displayText;
    }

    @Override
    public String toString() {
        return displayText;
    }

    public int getId() {
        return id;
    }
    
}
