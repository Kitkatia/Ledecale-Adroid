package com.cafe.decale.ledecale;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by manut on 06/07/2017.
 */

class EditProfileAsync extends AsyncTask<String, Void, Boolean> {
    public EditProfileAsync(EditProfileAsync.Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onLoaded(Boolean hasChanged);
        void onError();
    }

    private EditProfileAsync.Listener mListener;
    @Override
    protected Boolean doInBackground(String... params) {
        String rawResponse = null;
        try {
            URL obj = new URL(params[0]+ params[3]);
            HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();

            //add reuqest header

            connection.setRequestMethod("POST");
            connection.addRequestProperty("Authorization", "Basic "+params[1]);
            connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");


            // Send post request
            connection.setDoOutput(true);
            connection.setDoInput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(params[2]);
            wr.flush();
            wr.close();
            
            BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonUser = (JSONObject) new JSONTokener(rawResponse).nextValue();
            if(jsonUser.has("email") && !params[2].equals(jsonUser.getString("email"))){
                return true;
            }
            return false;

            } catch (JSONException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    protected void onPostExecute(Boolean hasChanged) {

        if (hasChanged) {

            mListener.onLoaded(hasChanged);

        } else {

            mListener.onError();
        }
    }
}
