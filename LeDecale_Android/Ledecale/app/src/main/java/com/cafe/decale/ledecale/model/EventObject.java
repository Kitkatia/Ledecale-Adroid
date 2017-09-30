package com.cafe.decale.ledecale.model;

import java.util.Date;

/**
 * Created by manut on 28/09/2017.
 */

public class EventObject {
    private int id;

    private Date date;

    private Date end;

    private String message;

    public EventObject( Date date, Date end) {
        this.date = date;
        this.end = end;
        this.message = "Available";
    }

    public EventObject(int id, Date date, Date end) {
        this.date = date;
        this.id = id;
        this.end = end;
        this.message = "Available";
    }

    public String getMessage() {
        return message;
    }
    public Date getEnd(){
        return end;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
}
