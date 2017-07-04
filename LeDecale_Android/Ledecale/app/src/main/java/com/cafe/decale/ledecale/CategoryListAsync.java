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

class CategoryListAsync extends AsyncTask<String, Void, ArrayList<Category>> {
    public CategoryListAsync(CategoryListAsync.Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onLoaded(ArrayList<Category> categories);
        void onError();
    }
    private Listener mListener;
    @Override
    protected ArrayList<Category> doInBackground(String... params) {
        String rawResponse = null;
        ArrayList<Category> jsonCategories = new ArrayList<>();
        try {
            rawResponse = EstablishConnection.loadJson(params[0], "category");

            JSONArray jsonArray = (JSONArray) new JSONTokener(rawResponse).nextValue();
            String name = "";
            String translatedName = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonCategory = jsonArray.getJSONObject(i);
                if(jsonCategory.has("name")){
                    name = jsonCategory.get("name").equals(null) ? "" : jsonCategory.getString("name");
                }
                if(jsonCategory.has("translatedName")){
                    translatedName = jsonCategory.get("translatedName").equals(null) ? "" : jsonCategory.getString("translatedName");
                }
                jsonCategories.add(new Category(name, translatedName));
            }
            return  jsonCategories;
        }
        catch (JSONException | IOException e1){
            e1.printStackTrace();
        }
        return jsonCategories;
    }

    @Override
    protected void onPostExecute(ArrayList<Category> categories) {

        if (categories != null) {

            mListener.onLoaded(categories);

        } else {

            mListener.onError();
        }
    }

}
