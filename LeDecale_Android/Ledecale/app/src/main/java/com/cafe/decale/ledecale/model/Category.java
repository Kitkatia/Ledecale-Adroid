package com.cafe.decale.ledecale.model;

import java.io.Serializable;

/**
 * Created by manut on 15/06/2017.
 */

public class Category implements Serializable{
    String name;
    String translatedName;
    boolean isSelected;

    public Category(String name, String translatedName){
        this.name = name;
        this.translatedName = translatedName;
        isSelected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslatedName() {
        return translatedName;
    }

    public void setTranslatedName(String translatedName) {
        this.translatedName = translatedName;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
