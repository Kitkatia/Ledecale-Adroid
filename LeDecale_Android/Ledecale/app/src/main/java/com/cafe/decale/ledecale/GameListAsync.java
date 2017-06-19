package com.cafe.decale.ledecale;

import android.os.AsyncTask;
import android.util.Log;

import com.cafe.decale.ledecale.model.Category;
import com.cafe.decale.ledecale.model.Game;
import com.cafe.decale.ledecale.model.Response;

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

/**
 * Created by manut on 13/06/2017.
 */

public class GameListAsync extends AsyncTask<String, Void, Response> {

    public GameListAsync(Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onLoaded(List<Game> games);
        void onError();
    }

    private Listener mListener;

    @Override
    protected Response doInBackground(String... strings) {
        String apiUrl = "https://ledecalebackend-dev.herokuapp.com/";
        String rawResponse = null;
        ArrayList<Game> jsonGames = new ArrayList<>();
        Response games = new Response();
        try {
            rawResponse = loadJson(apiUrl);
            // Log.d("RES", rawResponse)
            JSONArray jsonArray = (JSONArray) new JSONTokener(rawResponse).nextValue();
            Log.d("NUM", Integer.toString(jsonArray.length()));

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray jsonCategories = jsonObject.getJSONArray("categories");
                List<Category> categories = new ArrayList<>();

                for(int j = 0 ; j < jsonCategories.length(); j++){
                    JSONObject jsonCategory = jsonCategories.getJSONObject(j);
                    Category category = new Category(jsonCategory.getString("name"), jsonCategory.getString("translatedName"));
                    categories.add(category);
                }
                Game game = new Game(jsonObject.getDouble("objectId"), jsonObject.getInt("age"),
                                    jsonObject.getString("name"), jsonObject.getString("description"),
                                    jsonObject.getInt("player_min"), jsonObject.getInt("player_max"),
                                    jsonObject.getInt("minPlayTime"), jsonObject.getInt("maxPlayTime"),
                                    jsonObject.getDouble("price"), jsonObject.getString("thumbnail"),
                                    jsonObject.getDouble("rating"), jsonObject.getDouble("weight"), categories);

                jsonGames.add(game);
            }
            games.setGames(jsonGames);
            return games;
        }
        catch (JSONException | IOException e1) {
            e1.printStackTrace();
        }
        return games;
    }

    private String loadJson(String apiUrl) throws IOException{
        URL url = new URL(apiUrl + "games");
        //Log.d("URL", url.toString());
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
    protected void onPostExecute(Response response) {

        if (response != null) {

            mListener.onLoaded(response.getGames());

        } else {

            mListener.onError();
        }
    }


}
