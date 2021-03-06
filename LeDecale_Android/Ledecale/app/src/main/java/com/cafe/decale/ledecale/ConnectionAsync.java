package com.cafe.decale.ledecale;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by manut on 01/07/2017.
 */

public class ConnectionAsync extends AsyncTask<String, Void, String> {
    public ConnectionAsync(Listener listener) {
        this.mListener = listener;
    }

    public interface Listener{
        void onLoaded(String token);
        void onError();
    }

    private Listener mListener;

    @Override
    protected String doInBackground(String... params) {
        HttpsURLConnection connection;
        OutputStream request;

        JSONObject holder = new JSONObject();
        try {
            holder.put("email", params[1]);
            holder.put("password", params[2]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            connection = (HttpsURLConnection) (new URL(params[0]).openConnection());

            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.setRequestMethod("POST");
            connection.connect();

            request = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(request, "UTF-8"));
            writer.write(holder.toString());
            writer.close();
            request.close();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            bufferedReader.close();

            return getTokenFromJson(stringBuilder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getTokenFromJson(String s) throws JSONException {
        JSONObject jsonToken = (JSONObject) new JSONTokener(s).nextValue();
        if(jsonToken.has("token")){
            return jsonToken.getString("token");
        }
        return "";
    }

    @Override
    protected void onPostExecute(String token) {

        if (token != null) {

            mListener.onLoaded(token);

        } else {

            mListener.onError();
        }
    }
}
