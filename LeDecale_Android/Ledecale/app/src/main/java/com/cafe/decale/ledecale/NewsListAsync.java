package com.cafe.decale.ledecale;

import android.os.AsyncTask;

import com.cafe.decale.ledecale.Utils.EstablishConnection;
import com.cafe.decale.ledecale.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            rawResponse = EstablishConnection.loadJson(strings[0], "news");

            JSONArray jsonArray = (JSONArray) new JSONTokener(rawResponse).nextValue();

            String title = "";
            String content = "";
            String dateCreation = "";
            String urlImage = "";
            String dateNews = "";

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.has("title")){
                    title = jsonObject.get("title").equals(null) ? "" : jsonObject.getString("title");
                }
                if(jsonObject.has("content")){
                    content = jsonObject.get("content").equals(null) ? "" : jsonObject.getString("content");
                }
                if(jsonObject.has("dateCreation")){
                    dateCreation = jsonObject.get("dateCreation").equals(null) ? "" : jsonObject.getString("dateCreation");
                }
                if(jsonObject.has("urlImage")){
                    urlImage = jsonObject.get("urlImage").equals(null) ? "" : jsonObject.getString("urlImage");
                }
                if(jsonObject.has("dateNews")){
                    dateNews = jsonObject.get("dateNews").equals(null) ? "" : jsonObject.getString("dateNews");
                }


                jsonNews.add(new News(title, content,dateCreation, urlImage, dateNews));
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

    @Override
    protected void onPostExecute(ArrayList<News> newsList) {

        if (newsList != null) {

            mListener.onLoaded(newsList);

        } else {

            mListener.onError();
        }
    }

}
