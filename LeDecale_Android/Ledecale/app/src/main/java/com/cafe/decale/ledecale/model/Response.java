package com.cafe.decale.ledecale.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manut on 15/06/2017.
 */

public class Response {
    private List<Game> games = new ArrayList<>();

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Game> getGames(){
        return  this.games;
    }
}
