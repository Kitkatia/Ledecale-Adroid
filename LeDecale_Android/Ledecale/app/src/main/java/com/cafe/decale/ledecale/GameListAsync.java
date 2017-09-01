package com.cafe.decale.ledecale;

import android.os.AsyncTask;

import com.cafe.decale.ledecale.Utils.EstablishConnection;
import com.cafe.decale.ledecale.model.Category;
import com.cafe.decale.ledecale.model.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manut on 13/06/2017.
 */

public class GameListAsync extends AsyncTask<String, Void, ArrayList<Game>> {

    public GameListAsync(Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onLoaded(List<Game> games);
        void onError();
    }

    private Listener mListener;

    @Override
    protected ArrayList<Game> doInBackground(String... strings) {
        String rawResponse;
        ArrayList<Game> jsonGames = new ArrayList<>();
        try {
            rawResponse = EstablishConnection.loadJson(strings[0], "games");

            JSONArray jsonArray = (JSONArray) new JSONTokener(rawResponse).nextValue();
            int age = 0;
            String name = "";
            Long objectId = 0L;
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

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray jsonCategories = jsonObject.getJSONArray("categories");
                List<Category> categories = new ArrayList<>();

                for(int j = 0 ; j < jsonCategories.length(); j++){
                    JSONObject jsonCategory = jsonCategories.getJSONObject(j);
                    if(jsonCategory.has("name")){
                        name = jsonCategory.get("name").equals(null) ? "" : jsonCategory.getString("name");
                    }
                    if(jsonCategory.has("translatedName")){
                        translatedName = jsonCategory.get("translatedName").equals(null) ? "" : jsonCategory.getString("translatedName");
                    }
                    Category category = new Category(name, translatedName);
                    categories.add(category);
                }


                if(jsonObject.has("objectId") ){
                    objectId = jsonObject.get("objectId").equals(null) ? 0L : jsonObject.getLong("objectId");
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
                    minPlayTime = jsonObject.get("minPlayTime").equals(null) ? 0 : jsonObject.getInt("minPlayTime");
                }
                if(jsonObject.has("maxPlayTime")){
                    maxPlayTime = jsonObject.get("maxPlayTime").equals(null) ? 0 : jsonObject.getInt("maxPlayTime");
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

                jsonGames.add(new Game(objectId, age, name, description, playerMin, playerMax, minPlayTime, maxPlayTime, price, thumbnail, rating, weight, categories));
            }
            return jsonGames;
        }
        catch (JSONException | IOException e1) {
            e1.printStackTrace();
        }
        return jsonGames;
    }

    @Override
    protected void onPostExecute(ArrayList<Game> games) {

        if (games != null) {

            mListener.onLoaded(games);

        } else {

            mListener.onError();
        }
    }


}
