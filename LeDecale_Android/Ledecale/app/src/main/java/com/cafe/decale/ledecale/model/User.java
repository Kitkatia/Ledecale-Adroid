package com.cafe.decale.ledecale.model;

import java.io.Serializable;

/**
 * Created by manut on 21/06/2017.
 */

public class User implements Serializable {
    String lastName;
    String firstName;
    String email;
    String pseudo;
    Long id;
    boolean enable;

    public User() {
        id = 0L;
        lastName = "";
        firstName = "";
        email ="";
        pseudo="";
        enable = false;
    }
    public User(String lastName, String firstName, String email, String pseudo, boolean enable, Long id) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.pseudo = pseudo;
        this.enable = enable;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
