package com.cafe.decale.ledecale;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by manut on 05/07/2017.
 */

public class ResetPasswordAsync extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        HttpsURLConnection connection;
        OutputStream request;

        JSONObject holder = new JSONObject();
        try {
            holder.put("email", params[0]);
            holder.put("url", params[1]);
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

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
