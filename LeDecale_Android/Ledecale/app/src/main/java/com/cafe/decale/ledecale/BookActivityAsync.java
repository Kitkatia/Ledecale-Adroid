package com.cafe.decale.ledecale;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by manut on 02/10/2017.
 */

class BookActivityAsync extends AsyncTask<String, Void, Boolean> {

    public BookActivityAsync(BookActivityAsync.Listener listener) {
        this.mListener = listener;
    }

    public interface Listener{
        void onLoaded(Boolean isBooked);
        void onError();
    }
String response;
    private BookActivityAsync.Listener mListener;

    @Override
    protected Boolean doInBackground(String... params) {
        HttpsURLConnection connection;
        OutputStream request;

        JSONObject holder = new JSONObject();
        try {
            holder.put("type", params[1]);
            holder.put("startDate", params[2]);
            holder.put("finishDate", params[3]);
            holder.put("game", params[4]);
            holder.put("userMaster", params[5]);
            holder.put("descriptionBooking", "");
            holder.put("maxUsers", params[6]);
            holder.put("title", params[7]);
            holder.put("urlImage", params[8]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            connection = (HttpsURLConnection) (new URL(params[0]).openConnection());

            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            connection.connect();

            request = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(request, "UTF-8"));
            writer.write(holder.toString());
            writer.close();
            request.close();

            if (connection.getResponseCode() == 201) {
                return true;
            } else {
                InputStream error = connection.getErrorStream();
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(error));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    this.response = response.toString();
                    in.close();
                    return false;

                } catch (Exception e) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean isBooked) {
        if(isBooked){
            mListener.onLoaded(isBooked);
        }
        else{
            mListener.onError();
        }
    }
}
