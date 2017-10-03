package com.cafe.decale.ledecale;

import android.os.AsyncTask;

import com.cafe.decale.ledecale.Utils.EstablishConnection;
import com.cafe.decale.ledecale.model.Booking;
import com.cafe.decale.ledecale.model.Category;
import com.cafe.decale.ledecale.model.Game;
import com.cafe.decale.ledecale.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manut on 22/06/2017.
 */

class BookingListAsync extends AsyncTask<String, Void, ArrayList<Booking>> {

    public BookingListAsync(BookingListAsync.Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onLoaded(List<Booking> bookings);
        void onError();
    }

    private Listener mListener;

    @Override
    protected ArrayList<Booking> doInBackground(String... strings) {
        String rawResponse;
        ArrayList<Booking> jsonBookings = new ArrayList<>();
        try {
            rawResponse = EstablishConnection.loadJson(strings[0], "booking/calendar");

            JSONArray jsonArray = (JSONArray) new JSONTokener(rawResponse).nextValue();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonBookings.add(getBookingFromJson(jsonArray.getJSONObject(i).getJSONObject("booking")));
            }
            return jsonBookings;
        }
        catch (JSONException| IOException e) {
            e.printStackTrace();
            return jsonBookings;
        }
    }

    private Booking getBookingFromJson(JSONObject jsonObject) throws JSONException {
        String startDate="";
        String finishDate="";
        String information="";
        String name="";
        String urlImage="";
        String type="";
        int id = 0;
        int maxUsers = 0;
        ArrayList<User> users = new ArrayList<>();
        User user;
        Game game;
        if(jsonObject.has("users")) {
            JSONArray jsonUsers = jsonObject.getJSONArray("users");
            for (int j = 0; j < jsonUsers.length(); j++) {
                JSONObject jsonUser = jsonUsers.getJSONObject(j);
                users.add(getUserFromJson(jsonUser));
            }
        }
        if(jsonObject.has("userMaster")){
            user = getUserFromJson(jsonObject.getJSONObject("userMaster"));
        }
        else {
            user = new User();
        }
        if(jsonObject.has("game")) {
            JSONObject jsonGame = jsonObject.getJSONObject("game");
            game = getGameFromJson(jsonGame);
        }
        else {
            game = new Game();
        }

        if(jsonObject.has("type")){
            type = jsonObject.get("type").equals(null) ? "" : jsonObject.getString("type");
        }
        if(jsonObject.has("id")){
            id = jsonObject.get("id").equals(null) ? 0 : (int) jsonObject.get("id");
        }
        if(jsonObject.has("startDate")){
            startDate = jsonObject.get("startDate").equals(null) ? "" : jsonObject.getString("startDate");
        }
        if(jsonObject.has("finishDate")){
            finishDate = jsonObject.get("finishDate").equals(null) ? "" : jsonObject.getString("finishDate");
        }
        if(jsonObject.has("descriptionBooking")){
            information = jsonObject.get("descriptionBooking").equals(null) ? "" : jsonObject.getString("descriptionBooking");
        }
        if(jsonObject.has("maxUsers")){
            maxUsers = jsonObject.get("maxUsers").equals(null) ? 0 : jsonObject.getInt("maxUsers");
        }
        if(jsonObject.has("title")){
            name = jsonObject.get("title").equals(null) ? "" : jsonObject.getString("title");
        }
        if(jsonObject.has("urlImage")){
            urlImage = jsonObject.get("urlImage").equals(null) ? "" : jsonObject.getString("urlImage");
        }
        return new Booking(type, id, startDate, finishDate, user, users, game, information, maxUsers, name, urlImage);


    }

    private Game getGameFromJson(JSONObject jsonObject) throws JSONException {
        int age = 0;
        String name = "";
        int objectId = 0;
        Double rating = 0.0;
        Double weight = 0.0;
        int playerMin = 0;
        int playerMax = 0;
        int minPlayTime = 0;
        int maxPlayTime = 0;
        double price = 0.0;
        String thumbnail = "";
        String description = "";
        String translatedName = "";

        JSONArray jsonCategories = jsonObject.getJSONArray("categories");
        List<Category> categories = new ArrayList<>();

        for(int j = 0 ; j < jsonCategories.length(); j++){
            JSONObject jsonCategory = jsonCategories.getJSONObject(j);
            if(jsonCategory.has("name")){
                name = jsonCategory.get("name").equals(null) ? "" : jsonCategory.getString("name");
            }
            if(jsonCategory.has("translatedName")){
                translatedName = jsonCategory.get("translatedName").equals(null) ? "" : jsonCategory.getString("trnaslatedName");
            }
            Category category = new Category(name, translatedName);
            categories.add(category);
        }

        if(jsonObject.has("objectId") ){
            objectId = jsonObject.get("objectId").equals(null) ? 0 : (int) jsonObject.get("objectId");
        }
        if(jsonObject.has("age")){
            age = jsonObject.get("age").equals(null) ? 0 : jsonObject.getInt("age");
        }
        if(jsonObject.has("name")){
            name = jsonObject.get("name").equals(null) ? "" : jsonObject.getString("name");
        }
        if(jsonObject.has("description")){
            description = jsonObject.get("description").equals(null) ? "" : jsonObject.getString("description");
        }
        if(jsonObject.has("player_min")){
            playerMin = jsonObject.get("player_min").equals(null) ? 0 : jsonObject.getInt("player_min");
        }
        if(jsonObject.has("player_max")){
            playerMax = jsonObject.get("player_max").equals(null) ? 0 : jsonObject.getInt("player_max");
        }
        if(jsonObject.has("minPlayTime")){
            playerMin = jsonObject.get("minPlayTime").equals(null) ? 0 : jsonObject.getInt("minPlayTime");
        }
        if(jsonObject.has("maxPlayTime")){
            playerMax = jsonObject.get("maxPlayTime").equals(null) ? 0 : jsonObject.getInt("maxPlayTime");
        }
        if(jsonObject.has("price")){
            price = jsonObject.get("price").equals(null) ? 0.0 : jsonObject.getDouble("price");
        }
        if(jsonObject.has("rating") ){
            rating = jsonObject.get("rating").equals(null) ? 0L : jsonObject.getDouble("rating");
        }
        if(jsonObject.has("weight") ){
            weight = jsonObject.get("weight").equals(null) ? 0L : jsonObject.getDouble("weight");
        }
        if(jsonObject.has("thumbnail")){
            thumbnail = jsonObject.get("thumbnail").equals(null) ? "" : jsonObject.getString("thumbnail");
        }
        return new Game(objectId, age, name, description, playerMin, playerMax, minPlayTime, maxPlayTime, price, thumbnail, rating, weight, categories);
    }

    private User getUserFromJson(JSONObject jsonUser) throws JSONException {
        String lastname="", firstname="", pseudo="", email="";
        Long id = 0L;
        Boolean enabled = false;

        if(jsonUser.has("lastname")){
            lastname = jsonUser.get("lastname").equals(null) ? "" : jsonUser.getString("lastname");
        }
        if(jsonUser.has("name")){
            firstname = jsonUser.get("firstname").equals(null) ? "" : jsonUser.getString("firstname");
        }
        if(jsonUser.has("pseudo")){
            pseudo = jsonUser.get("pseudo").equals(null) ? "" : jsonUser.getString("pseudo");
        }
        if(jsonUser.has("email")){
            email = jsonUser.get("email").equals(null) ? "" : jsonUser.getString("email");
        }
        if(jsonUser.has("id")){
            id = jsonUser.get("id").equals(null) ? 0L : jsonUser.getLong("id");
        }
        if(jsonUser.has("enabled")){
            enabled = jsonUser.get("enabled").equals(null) ? false : jsonUser.getBoolean("enabled");
        }
        return new User(lastname, firstname,  email, pseudo, enabled, id);

    }

    @Override
    protected void onPostExecute(ArrayList<Booking> bookings) {

        if (bookings != null) {

            mListener.onLoaded(bookings);

        } else {

            mListener.onError();
        }
    }
}
