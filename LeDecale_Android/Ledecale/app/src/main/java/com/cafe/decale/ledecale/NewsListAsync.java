package com.cafe.decale.ledecale;

import android.os.AsyncTask;
import android.util.Log;

import com.cafe.decale.ledecale.model.Game;
import com.cafe.decale.ledecale.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by manut on 21/06/2017.
 */

public class NewsListAsync extends AsyncTask<String, Void, ArrayList<News>> {

    public NewsListAsync(NewsListAsync.Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onLoaded(List<News> newsList);
        void onError();
    }

    private NewsListAsync.Listener mListener;

    @Override
    protected ArrayList<News> doInBackground(String... strings) {
        String rawResponse = null;
        ArrayList<News> jsonNews = new ArrayList<>();
        try {
            rawResponse = loadJson(strings[0]);

            JSONArray jsonArray = (JSONArray) new JSONTokener(rawResponse).nextValue();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                News news = new News(jsonObject.getString("title"), jsonObject.getString("content"),jsonObject.getString("dateCreation"), jsonObject.getString("urlImage"), jsonObject.getString("dateNews"));

                jsonNews.add(news);
            }
            return jsonNews;
        }
        catch (JSONException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonNews;
    }

    private String loadJson(String apiUrl) throws IOException {
        URL url = new URL(apiUrl + "news");

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
    protected void onPostExecute(ArrayList<News> newsList) {

        if (newsList != null) {

            mListener.onLoaded(newsList);

        } else {

            mListener.onError();
        }
    }

}
