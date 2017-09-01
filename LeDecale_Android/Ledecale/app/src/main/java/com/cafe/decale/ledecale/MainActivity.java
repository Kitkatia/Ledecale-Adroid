package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.HashMap;


public class MainActivity extends Activity {
    Button findGames;
    Button eventItemList;
    Button newsItemList;
    ImageButton myProfile;
    ImageButton btnLogout;

    AlertDialogManager alert = new AlertDialogManager();

    MySessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new MySessionManager(getApplicationContext());

        findGames = (Button) findViewById(R.id.gamesButton);
        eventItemList = (Button) findViewById(R.id.eventsButton);
        newsItemList = (Button)  findViewById(R.id.newsButton);

        HashMap<String, String> user = session.getUserDetails();

        String token = user.get(MySessionManager.KEY_TOKEN);

        String email = user.get(MySessionManager.KEY_EMAIL);
        myProfile = (ImageButton) findViewById(R.id.profile);
        if(token != null && email != null){
            myProfile.setVisibility(View.VISIBLE);
            btnLogout = (ImageButton) findViewById(R.id.logout);
            btnLogout.setVisibility(View.VISIBLE);
            btnLogout.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    session.logoutUser();
                    finish();
                }
            });
            myProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                    startActivity(intent);
                }
            });
        }


        findGames.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FindGameActivity.class);
                startActivity(intent);
            }
        });

        eventItemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookingListActivity.class);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, FirstActivity.class);
        startActivity(intent);
        finish();
    }
}
