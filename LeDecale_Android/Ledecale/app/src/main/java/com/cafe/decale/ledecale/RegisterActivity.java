package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cafe.decale.ledecale.Utils.AlertDialogManager;

import java.util.regex.Pattern;

/**
 * Created by manut on 01/09/2017.
 */

public class RegisterActivity extends Activity implements RegisterAsync.Listener{
    EditText pseudoE;
    EditText lastNameE;
    EditText firstNameE;
    EditText emailE;
    EditText passwordE;
    EditText confirmedPass;
    Button register;

    AlertDialogManager alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        pseudoE = (EditText) findViewById(R.id.pseudo);
        lastNameE = (EditText) findViewById(R.id.name);
        firstNameE = (EditText) findViewById(R.id.surName);
        emailE = (EditText) findViewById(R.id.email);
        passwordE = (EditText) findViewById(R.id.password);
        confirmedPass = (EditText) findViewById(R.id.confirmedPAss);
        register = (Button) findViewById(R.id.register);

        alert = new AlertDialogManager();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                String pseudo = pseudoE.getText().toString();
                String lastName = lastNameE.getText().toString();
                String firstName = firstNameE.getText().toString();
                String email = emailE.getText().toString();
                String password = passwordE.getText().toString();
                if(pseudo.length() == 0 ){
                    alert.showAlertDialog(RegisterActivity.this, "Fail", "you have to specify a pseudo", false);
                    return;
                }
                else if(lastName.length() == 0 ){
                    alert.showAlertDialog(RegisterActivity.this, "Fail", "you have to specify a last Name", false);
                    return;
                }
                else if(firstName.length() == 0 ){
                    alert.showAlertDialog(RegisterActivity.this, "Fail", "you have to specify a first Name", false);
                    return;
                }
                else if(email.length() == 0 ){
                    alert.showAlertDialog(RegisterActivity.this, "Fail", "you have to specify an email", false);
                    return;
                }
                else if(password.length() == 0 ){
                    alert.showAlertDialog(RegisterActivity.this, "Fail", "you have to specify a password", false);
                    return;
                }
                else if(confirmedPass.getText().length() == 0 ){
                    alert.showAlertDialog(RegisterActivity.this, "Fail", "you have to confirm the password", false);
                    return;
                }
                else if(!pattern.matcher(email).matches()){
                    alert.showAlertDialog(RegisterActivity.this, "Fail", "you have to specify a valid email", false);
                    return;
                }
                else if(!password.equals(confirmedPass.getText().toString())){
                    alert.showAlertDialog(RegisterActivity.this, "Fail", "The passwords do not match!", false);
                    return;
                }
                else {
                    new RegisterAsync(RegisterActivity.this).execute("https://ledecalebackend-dev.herokuapp.com/user", pseudo, lastName, firstName, email, password);
                }
            }
        });
    }

    @Override
    public void onLoaded(Boolean isRegistered) {
        if(isRegistered){
            alert.showAlertDialog(this, "Success", "You have been successfully registered, you can login", true);
            alert.alertDialog.getButton(-1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RegisterActivity.this, ConnectionActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public void onError(String respose) {
        alert.showAlertDialog(RegisterActivity.this, "Registration failed", respose, false);
    }
}
