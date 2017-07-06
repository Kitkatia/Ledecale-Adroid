package com.cafe.decale.ledecale;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cafe.decale.ledecale.Utils.JWTUtils;
import com.cafe.decale.ledecale.model.User;

/**
 * Created by manut on 05/07/2017.
 */

public class MyProfileActivity extends Activity{
    TextView name, lastName, pseudo, email;
    Button editProfile, myBookings;

    MySessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_activity);

        name = (TextView) findViewById(R.id.userName);
        lastName = (TextView) findViewById(R.id.userLastName);
        pseudo = (TextView) findViewById(R.id.userPseudo);
        email = (TextView) findViewById(R.id.userEmail);

        editProfile = (Button) findViewById(R.id.editProfile);
        myBookings = (Button) findViewById(R.id.myBookings);

        session = new MySessionManager(getApplicationContext());

        User user = JWTUtils.decoded(session.getUserDetails().get(MySessionManager.KEY_TOKEN));

        name.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        pseudo.setText(user.getPseudo());
        email.setText(user.getEmail());
    }
}
