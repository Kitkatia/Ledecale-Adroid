package com.cafe.decale.ledecale;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cafe.decale.ledecale.model.Game;
import com.squareup.picasso.Picasso;

/**
 * Created by manut on 20/06/2017.
 */

public class GameActivity extends Activity{
    TextView name;
    TextView description;
    ImageView picture;
    TextView numPlayers;
    TextView playTime;
    TextView age;
    TextView price;
    TextView weight;
    TextView rate;

    //ListView categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        Game game = (Game) getIntent().getSerializableExtra("game");

        name = (TextView) findViewById(R.id.gameName);
        name.setText(game.getName());

        description = (TextView) findViewById(R.id.gameDescription);
        description.setText(game.getDescription());

        numPlayers = (TextView) findViewById(R.id.gameNumPlayers);
        numPlayers.setText("Max : " +game.getPlayerMax() +" Min: " + game.getPlayerMin());

        playTime = (TextView) findViewById(R.id.gamePlayTime);
        playTime.setText("Max: " + game.getMaxPlayTime() +" Min: " + game.getMinPlayTime());

        age = (TextView) findViewById(R.id.gameAge);
        age.setText(String.valueOf(game.getAge()));

        price = (TextView) findViewById(R.id.gamePrice);
        price.setText(String.valueOf(game.getPrice()));

        weight = (TextView) findViewById(R.id.gameWeight);
        weight.setText(String.valueOf(game.getWeight()));

        rate = (TextView) findViewById(R.id.gameRate);
        rate.setText(String.valueOf(game.getRating()));

        ImageView picture = (ImageView) findViewById(R.id.gamePicture);
        Picasso.with(GameActivity.this).load(game.getThumbnail()).into(picture);
    }
}
