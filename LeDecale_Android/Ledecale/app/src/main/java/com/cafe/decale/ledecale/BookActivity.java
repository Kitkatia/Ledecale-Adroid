package com.cafe.decale.ledecale;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by manut on 08/07/2017.
 */

public class BookActivity extends Activity {
    EditText numberPlayers;
    TextView name;
    ImageView gameImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);
        numberPlayers = (EditText) findViewById(R.id.numPlayers);
        name = (TextView) findViewById(R.id.gameName);
        gameImage = (ImageView) findViewById(R.id.gameImage);
        name.setText((String) getIntent().getSerializableExtra("Game Name"));
        Picasso.with(BookActivity.this).load(getIntent().getSerializableExtra("img").toString()).into(gameImage);



    }
}
