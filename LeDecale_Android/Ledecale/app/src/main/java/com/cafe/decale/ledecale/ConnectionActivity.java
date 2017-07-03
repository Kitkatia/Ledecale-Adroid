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

public class ConnectionActivity extends Activity implements ConnectionAsync.Listener{
    Button connection;
    EditText email, password;

    AlertDialogManager alert = new AlertDialogManager();

    MySessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_activity);

        session = new MySessionManager(getApplicationContext());

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        connection = (Button) findViewById(R.id.connection);
        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eMail = email.getText().toString();
                String pass = password.getText().toString();
                if(eMail.trim().length() > 0 && pass.trim().length() > 0){
                    new ConnectionAsync(ConnectionActivity.this).execute("https://ledecalebackend-dev.herokuapp.com/authentification", eMail, pass);
                }
                else {
                    alert.showAlertDialog(ConnectionActivity.this, "Login failed", "Please enter username and password", false);
                }
            }
        });
    }


    @Override
    public void onLoaded(String token) {
        if(token!= null) {
            session.createLoginSession(token, email.getText().toString());
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    @Override
    public void onError() {
        alert.showAlertDialog(ConnectionActivity.this, "Login failed", "Email or password not found", false);
    }

    @Override
    public void onBackPressed() {
        if(!session.isLogged()){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
