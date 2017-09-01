package com.cafe.decale.ledecale.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by manut on 21/06/2017.
 */

public class Booking implements Serializable {
    Long id;
    Game game;
    String type;
    String name;
    User creator;
    int maxUsers;
    String endDate;
    String urlImage;
    String startDate;
    String information;
    ArrayList<User> users;

    public Booking(String type, Long id, String startDate, String endDate, User creator, ArrayList<User> users, Game game, String information, int maxUsers, String name, String urlImage) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.game = game;
        this.users = users;
        this.creator = creator;
        this.endDate = endDate;
        this.maxUsers = maxUsers;
        this.urlImage = urlImage;
        this.startDate = startDate;
        this.information = information;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public String getUrlImage() {
        return urlImage;
    }
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean contains(String email){
        boolean isAlreadyIn = false;
        if(users!= null){
            for(User user : users){
                if(user.getEmail().equals(email.toLowerCase())){
                    isAlreadyIn = true;
                }
            }
        }
        return isAlreadyIn;
    }
}
