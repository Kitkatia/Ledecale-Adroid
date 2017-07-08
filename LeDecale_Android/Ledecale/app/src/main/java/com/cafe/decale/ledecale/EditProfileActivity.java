package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cafe.decale.ledecale.Utils.JWTUtils;
import com.cafe.decale.ledecale.model.User;

import java.util.List;

/**
 * Created by manut on 06/07/2017.
 */

public class EditProfileActivity extends Activity implements EditProfileAsync.Listener{
    EditText email;
    Button submit;
    ImageButton logout;

    MySessionManager session;
    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);

        email = (EditText) findViewById(R.id.userEmail);

        submit = (Button) findViewById(R.id.ok);
        logout = (ImageButton) findViewById(R.id.logout);

        session = new MySessionManager(getApplicationContext());

        logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                session.logoutUser();
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = JWTUtils.decoded(session.getUserDetails().get(MySessionManager.KEY_TOKEN));
                String editMail = email.getText().toString();
                if(editMail.trim().length()==0){
                    alert.showAlertDialog(EditProfileActivity.this, "Edit failed", "All fields must be filled", false);
                }
                else if(editMail.equals(user.getEmail())){
                    alert.showAlertDialog(EditProfileActivity.this, "Edit failed", "No changes", false);
                }
                else{
                    new EditProfileAsync(EditProfileActivity.this).execute("https://ledecalebackend-dev.herokuapp.com/user/",
                            session.getUserDetails().get(MySessionManager.KEY_TOKEN), email.getText().toString(), user.getId().toString());
                }
            }
        });


    }


    @Override
    public void onLoaded(Boolean hasChanged) {
        if(hasChanged){
            Toast.makeText(EditProfileActivity.this, "Profile edited : Your profile has been succesfully edited", Toast.LENGTH_LONG);
            session.reConnect();
            finish();
        }
        else {
            alert.showAlertDialog(getApplicationContext(), "Profile unchanged", "Your profile has not changed", false);
        }
    }

    @Override
    public void onError() {
        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
