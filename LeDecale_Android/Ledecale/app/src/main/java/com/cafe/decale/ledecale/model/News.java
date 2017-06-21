package com.cafe.decale.ledecale.model;

import java.io.Serializable;
import java.security.Timestamp;

/**
 * Created by manut on 20/06/2017.
 */

public class News implements Serializable{
    String title;
    String content;
    String dateCreation;
    String urlImage;
    String dateNews;

    public News(String title, String content, String dateCreation, String urlImage, String dateNews) {
        this.title = title;
        this.content = content;
        this.dateCreation = dateCreation;
        this.urlImage = urlImage;
        this.dateNews = dateNews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDateNews() {
        return dateNews;
    }

    public void setDateNews(String dateNews) {
        this.dateNews = dateNews;
    }
}
