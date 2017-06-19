package com.cafe.decale.ledecale.model;

/**
 * Created by manut on 15/06/2017.
 */

public class Category {
    String name;
    String translatedName;

    public Category(String name, String translatedName){
        this.name = name;
        this.translatedName = translatedName;
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
}
