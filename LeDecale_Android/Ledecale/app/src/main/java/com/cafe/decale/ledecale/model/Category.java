package com.cafe.decale.ledecale.model;

import java.io.Serializable;

/**
 * Created by manut on 15/06/2017.
 */

public class Category implements Serializable{
    String name;
    String translatedName;
    int value;

    public Category(String name, String translatedName){
        this.name = name;
        this.translatedName = translatedName;
        value = 0;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
