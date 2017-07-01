package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class MainActivity extends Activity {
    Button gameItemList;
    Button eventItemList;
    Button newsItemList;
    TextView text;

    AlertDialogManager alert = new AlertDialogManager();

    MySessionManager session;

//    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new MySessionManager(getApplicationContext());

        text = (TextView) findViewById(R.id.welcomeText);

        gameItemList = (Button) findViewById(R.id.gamesButton);
        eventItemList = (Button) findViewById(R.id.eventsButton);
        newsItemList = (Button)  findViewById(R.id.newsButton);

        //btnLogout = (Button) findViewById(R.id.btnLogout);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLogged(), Toast.LENGTH_LONG).show();
        //session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();

        String pseudo = user.get(MySessionManager.KEY_PSEUDO);

        String email = user.get(MySessionManager.KEY_EMAIL);

 /*       btnLogout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });
*/
        gameItemList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, GameListActivity.class);
                startActivity(intent);
            }
        });

        eventItemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventListActivity.class);
                startActivity(intent);
            }
        });

        newsItemList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
              Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
                startActivity(intent);
            }
        });

    }
}
