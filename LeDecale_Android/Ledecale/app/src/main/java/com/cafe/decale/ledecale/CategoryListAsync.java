package com.cafe.decale.ledecale;

import android.os.AsyncTask;

import com.cafe.decale.ledecale.model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by manut on 03/07/2017.
 */

class CategoryListAsync extends AsyncTask<String, Void, String[]> {
    public CategoryListAsync(CategoryListAsync.Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onLoaded(String[] categories);
        void onError();
    }
    private Listener mListener;
    @Override
    protected String[] doInBackground(String... params) {
        String rawResponse = null;
        ArrayList<String> jsonCategories = new ArrayList<>();
        try {
            rawResponse = EstablishConnection.loadJson(params[0], "category");

            JSONArray jsonArray = (JSONArray) new JSONTokener(rawResponse).nextValue();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonCategories.add(jsonArray.getJSONObject(i).getString("name"));
            }
            return  jsonCategories.toArray(new String[jsonCategories.size()]);
        }
        catch (JSONException | IOException e1){
            e1.printStackTrace();
        }
        return jsonCategories.toArray(new String[jsonCategories.size()]);
    }

    @Override
    protected void onPostExecute(String[] categories) {

        if (categories != null) {

            mListener.onLoaded(categories);

        } else {

            mListener.onError();
        }
    }

}
