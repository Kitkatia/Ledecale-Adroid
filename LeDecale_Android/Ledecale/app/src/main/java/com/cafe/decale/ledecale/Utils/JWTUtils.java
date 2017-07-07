package com.cafe.decale.ledecale.Utils;

import android.util.Base64;

import com.cafe.decale.ledecale.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by manut on 06/07/2017.
 */

public class JWTUtils {
    public static User decoded(String jwtEncoded){
        User user = new User();
        try {
            String[] split = jwtEncoded.split("\\.");
            JSONObject jsonUser = getJson(split[1]);
            if(jsonUser.has("name")){
                user.setFirstName(jsonUser.get("name").equals(null) ? "" : jsonUser.getString("name"));
            }
            if(jsonUser.has("lastName")){
                user.setLastName(jsonUser.get("lastName").equals(null) ? "" : jsonUser.getString("lastName"));
            }
            if(jsonUser.has("pseudo")){
                user.setPseudo(jsonUser.get("pseudo").equals(null) ? "" : jsonUser.getString("pseudo"));
            }
            if(jsonUser.has("mail")){
                user.setEmail(jsonUser.get("mail").equals(null) ? "" : jsonUser.getString("mail"));
            }
            if(jsonUser.has("id")){
                user.setId(jsonUser.get("id").equals(null) ? 0L : jsonUser.getLong("id"));
            }
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
            return user;
        }
    }

    private static JSONObject getJson(String userEncoded) throws JSONException {
        byte[] decodedBytes = Base64.decode(userEncoded, Base64.URL_SAFE);
        return new JSONObject(new String(decodedBytes));
    }
}
