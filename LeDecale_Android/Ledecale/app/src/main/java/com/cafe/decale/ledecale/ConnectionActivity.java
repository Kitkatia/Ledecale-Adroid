package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by manut on 24/06/2017.
 */

public class ConnectionActivity extends Activity {
    Button connection;
    EditText login, password;

    AlertDialogManager alert = new AlertDialogManager();

    MySessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_activity);

        session = new MySessionManager(getApplicationContext());

        login = (EditText) findViewById(R.id.pseudo);
        password = (EditText) findViewById(R.id.password);

        Toast.makeText(getApplicationContext(), "User Login Status: "+ session.isLogged(), Toast.LENGTH_LONG).show();

        connection = (Button) findViewById(R.id.connection);
        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pseudo = login.getText().toString();
                String pass = login.getText().toString();
                if(pseudo.trim().length() > 0 && pass.trim().length() > 0){
                    if(pseudo.equals("test") && pass.equals("test")){
                        session.createLoginSession("LeDecale", "ledecale@gmail.com");
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        alert.showAlertDialog(ConnectionActivity.this, "Login faled...", "Username/Password is incorrect", false);
                    }
                }
                else {
                    alert.showAlertDialog(ConnectionActivity.this, "Login failed", "Please enter username and password", false);
                }
            }
        });
    }
}
