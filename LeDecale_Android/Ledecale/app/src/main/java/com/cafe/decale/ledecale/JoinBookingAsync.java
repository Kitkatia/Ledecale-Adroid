package com.cafe.decale.ledecale;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by manut on 09/07/2017.
 */

public class JoinBookingAsync extends BookUnbook {
    public JoinBookingAsync(Listener listener){
        super(listener);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String rawResponse = null;
        try {
            URL obj = new URL(params[0]+"/"+ Long.parseLong(params[2]));
            HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();

            connection.setRequestMethod("POST");
            connection.addRequestProperty("Authorization", params[1]);
            connection.addRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);
            connection.setDoInput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(params[1]);
            wr.flush();
            wr.close();


            if (connection.getResponseCode() == 202) {
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
                    in.close();
                    return false;

                } catch (Exception e) {
                    return false;
                }
            }
        } catch ( IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    protected void onPostExecute(Boolean hasJoined) {

        if (hasJoined) {

            mListener.onLoaded(hasJoined, "joined");

        } else {

            mListener.onError("join");
        }
    }
}
