package com.cafe.decale.ledecale;

import android.os.AsyncTask;

import com.cafe.decale.ledecale.Utils.EstablishConnection;
import com.cafe.decale.ledecale.model.Event;
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
            rawResponse = EstablishConnection.loadJson(strings[0], "event");

            JSONArray jsonArray = (JSONArray) new JSONTokener(rawResponse).nextValue();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JSONArray jsonUsers = jsonObject.getJSONArray("users");
                ArrayList<User> users = new ArrayList<>();
                String lastname="", firstname="", pseudo="", email="";
                Long id = 0L;
                Boolean enabled = false;
                for(int j = 0 ; j < jsonUsers.length(); j++){
                    JSONObject jsonUser = jsonUsers.getJSONObject(j);
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
                    User userTemp = new User(lastname, firstname,  email, pseudo, enabled, id);
                    users.add(userTemp);
                }
                User user;
                if(jsonObject.getJSONArray("user").length() == 0){
                    user = new User();
                }
                else {
                    JSONObject jsonUser = (JSONObject) jsonObject.getJSONArray("user").get(0);
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
                    user = new User(lastname, firstname, email, pseudo, enabled, id);
                }
                Event event = new Event(jsonObject.getString("name"), jsonObject.getString("creationDate"), jsonObject.getString("startDate"), jsonObject.getString("endDate"), users, jsonObject.getString("information"), user);

                jsonEvents.add(event);

            }
            return jsonEvents;
        }
        catch (JSONException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonEvents;
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
