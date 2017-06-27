package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by manut on 24/06/2017.
 */

public class FirstActivity extends Activity {

    Button connexion;
    Button leDecale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        connexion = (Button) findViewById(R.id.connexion);
        leDecale = (Button) findViewById(R.id.leDecale);

        leDecale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, ConnectionActivity.class);
                startActivity(intent);
            }
        });
    }
}