package com.cafe.decale.ledecale.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by manut on 21/06/2017.
 */

public class Event implements Serializable {
    String name;
    String creationDate;
    String startDate;
    String endDate;
    ArrayList<User> users;
    String information;
    User creator;


    public Event(String name, String creationDate, String startDate, String endDate, ArrayList<User> users, String information, User creator) {
        this.name = name;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.users = users;
        this.information = information;
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
