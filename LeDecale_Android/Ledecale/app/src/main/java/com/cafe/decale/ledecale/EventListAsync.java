package com.cafe.decale.ledecale;

import android.os.AsyncTask;
import android.util.Log;

import com.cafe.decale.ledecale.model.Event;
import com.cafe.decale.ledecale.model.Game;
import com.cafe.decale.ledecale.model.User;

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
 * Created by manut on 22/06/2017.
 */

class EventListAsync extends AsyncTask<String, Void, ArrayList<Event>> {

    public EventListAsync(EventListAsync.Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onLoaded(List<Event> events);
        void onError();
    }

    private Listener mListener;

    @Override
    protected ArrayList<Event> doInBackground(String... strings) {
        String rawResponse = null;
        ArrayList<Event> jsonEvents = new ArrayList<>();
        try {
            rawResponse = loadJson(strings[0]);

            JSONArray jsonArray = (JSONArray) new JSONTokener(rawResponse).nextValue();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JSONArray jsonUsers = jsonObject.getJSONArray("users");
                ArrayList<User> users = new ArrayList<>();

                for(int j = 0 ; j < jsonUsers.length(); j++){
                    JSONObject jsonUser = jsonUsers.getJSONObject(j);
                    User userTemp = new User(jsonUser.getString("lastname"), jsonUser.getString("firstname"),  jsonUser.getString("email"), jsonUser.getString("pseudo"), jsonUser.getBoolean("enabled"));
                    users.add(userTemp);
                }
                User user;
                if(jsonObject.getJSONArray("user").length() == 0){
                    user = new User();
                }
                else {
                    JSONObject jsonUser = (JSONObject) jsonObject.getJSONArray("user").get(0);
                    user = new User(jsonUser.getString("lastname"), jsonUser.getString("firstname"), jsonUser.getString("email"), jsonUser.getString("pseudo"), jsonUser.getBoolean("enabled"));
                }
                Event event = new Event(jsonObject.getString("name"), jsonObject.getString("creationDate"), jsonObject.getString("startDate"), jsonObject.getString("endDate"), users, jsonObject.getString("information"), user);

                jsonEvents.add(event);

            }
            Log.d(TAG, "doInBackground: "+ jsonEvents.size());
            return jsonEvents;
        }
        catch (JSONException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonEvents;
    }

    private String loadJson(String apiUrl) throws IOException {
        URL url = new URL(apiUrl + "event");

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
    protected void onPostExecute(ArrayList<Event> events) {

        if (events != null) {

            mListener.onLoaded(events);

        } else {

            mListener.onError();
        }
    }
}
