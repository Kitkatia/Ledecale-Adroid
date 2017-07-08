package com.cafe.decale.ledecale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.HardwarePropertiesManager;
import android.text.InputType;

import java.util.HashMap;

/**
 * Created by manut on 26/06/2017.
 */

public class MySessionManager {
    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LeDecalePref";

    private static final String IS_LOGIN = "IsLogged";

    public static final String KEY_TOKEN = "token";

    public static final String KEY_EMAIL = "email";

    public MySessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String token, String email){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_EMAIL, email);

        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        return user;
    }

    public void checkLogin(){
        if(!this.isLogged()){
            Intent i = new Intent(context, ConnectionActivity.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);
        }
    }

    public void reConnect(){
        Intent intent = new Intent(context, ConnectionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, ConnectionActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);
    }

    public boolean isLogged(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
