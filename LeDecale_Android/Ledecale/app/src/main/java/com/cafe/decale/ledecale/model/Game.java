package com.cafe.decale.ledecale.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import java.util.jar.Pack200;

import static android.R.attr.category;

/**
 * Created by manut on 15/06/2017.
 */

public class Game implements Serializable {
    double objectId;
    int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
    String description;
    int playerMin;
    int playerMax;
    int minPlayTime;
    int maxPlayTime;
    double price;
    String thumbnail;
    double rating;
    double weight;
    List<Category> categories;

    public Game(double objectId, int age, String name, String description, int playerMin, int playerMax, int minPlayTime, int maxPlayTime, double price, String thumbnail, double rating, double weight, List<Category> categories) {
        this.objectId = objectId;
        this.age = age;
        this.name = name;
        this.description = description;
        this.playerMin = playerMin;
        this.playerMax = playerMax;
        this.minPlayTime = minPlayTime;
        this.maxPlayTime = maxPlayTime;
        this.price = price;
        this.thumbnail = thumbnail;
        this.rating = rating;
        this.weight = weight;
        this.categories = categories;
    }

    public double getObjectId() {
        return objectId;
    }

    public void setObjectId(double objectId) {
        this.objectId = objectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlayerMin() {
        return playerMin;
    }

    public void setPlayerMin(int plaayerMin) {
        this.playerMin = plaayerMin;
    }

    public int getPlayerMax() {
        return playerMax;
    }

    public void setPlayerMax(int playerMax) {
        this.playerMax = playerMax;
    }

    public int getMinPlayTime() {
        return minPlayTime;
    }

    public void setMinPlayTime(int minPlayTime) {
        this.minPlayTime = minPlayTime;
    }

    public int getMaxPlayTime() {
        return maxPlayTime;
    }

    public void setMaxPlayTime(int maxPlayTime) {
        this.maxPlayTime = maxPlayTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

/*    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(thumbnail);
        dest.writeInt(playerMin);
        dest.writeInt(playerMax);
        dest.writeInt(maxPlayTime);
        dest.writeInt(minPlayTime);
        dest.writeDouble(rating);
        dest.writeDouble(weight);

        for (Category category : categories)
            dest.writeSerializable(category);
    }*/
}
