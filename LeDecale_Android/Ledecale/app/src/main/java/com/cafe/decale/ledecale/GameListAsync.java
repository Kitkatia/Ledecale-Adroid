package com.cafe.decale.ledecale;

import android.os.AsyncTask;
import android.util.Log;

import com.cafe.decale.ledecale.model.Category;
import com.cafe.decale.ledecale.model.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

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
        String rawResponse = null;
        ArrayList<Game> jsonGames = new ArrayList<>();
        try {
            rawResponse = loadJson(strings[0]);
            // Log.d("RES", rawResponse)
            JSONArray jsonArray = (JSONArray) new JSONTokener(rawResponse).nextValue();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray jsonCategories = jsonObject.getJSONArray("categories");
                List<Category> categories = new ArrayList<>();

                for(int j = 0 ; j < jsonCategories.length(); j++){
                    JSONObject jsonCategory = jsonCategories.getJSONObject(j);
                    Category category = new Category(jsonCategory.getString("name"), jsonCategory.getString("translatedName"));
                    categories.add(category);
                }
                double price = jsonObject.get("price").equals(null) ? 0.0 : jsonObject.getDouble("price");
                Game game = new Game(jsonObject.getDouble("objectId"), jsonObject.getInt("age"),
                                    jsonObject.getString("name"), jsonObject.getString("description"),
                                    jsonObject.getInt("player_min"), jsonObject.getInt("player_max"),
                                    jsonObject.getInt("minPlayTime"), jsonObject.getInt("maxPlayTime"),
                                    price, jsonObject.getString("thumbnail"),
                                    jsonObject.getDouble("rating"), jsonObject.getDouble("weight"), categories);

                jsonGames.add(game);
            }
            return jsonGames;
        }
        catch (JSONException | IOException e1) {
            e1.printStackTrace();
        }
        return jsonGames;
    }

    private String loadJson(String apiUrl) throws IOException{
        URL url = new URL(apiUrl + "games");

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        bufferedReader.close();
        return stringBuilder.toString();
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
