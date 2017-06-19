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


public class MainActivity extends Activity {
    Button gameList = null;
    TextView text = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.welcomeText);

        gameList = (Button) findViewById(R.id.gamesButton);

        gameList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, GameListActivity.class);
                startActivity(intent);
            }
        });


    }
}
