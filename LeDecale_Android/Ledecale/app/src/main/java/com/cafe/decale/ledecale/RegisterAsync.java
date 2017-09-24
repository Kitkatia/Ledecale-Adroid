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
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by manut on 01/09/2017.
 */

public class RegisterAsync extends AsyncTask<String, Void, Boolean> {

    public RegisterAsync(RegisterAsync.Listener listener) {
        mListener = listener;
    }

    public interface Listener {
        void onLoaded(Boolean isRegistered);

        void onError(String message);
    }

    private RegisterAsync.Listener mListener;
    String response;
    @Override
    protected Boolean doInBackground(String... params) {
        HttpsURLConnection connection;
        OutputStream request;

        JSONObject holder = new JSONObject();
        try {
            holder.put("lastname", params[2]);
            holder.put("firstname", params[3]);
            holder.put("email", params[4]);
            holder.put("pseudo", params[1]);
            holder.put("password", params[5]);
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
    protected void onPostExecute(Boolean isRegistered) {

        if (isRegistered) {

            mListener.onLoaded(isRegistered);

        } else {

            mListener.onError(response);
        }
    }
}
