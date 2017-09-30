package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    TextView categories;
    Button book;

    MySessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        final Game game = (Game) getIntent().getSerializableExtra("game");

        name = (TextView) findViewById(R.id.gameName);
        name.setText(game.getName());

        description = (TextView) findViewById(R.id.gameDescription);
        description.setText(Html.fromHtml(game.getDescription()));

        numPlayers = (TextView) findViewById(R.id.gameNumPlayers);
        numPlayers.setText("Max players : " +game.getPlayerMax() +" Min players : " + game.getPlayerMin());

        playTime = (TextView) findViewById(R.id.gamePlayTime);
        playTime.setText("Max play time : " + game.getMaxPlayTime() +"mn "+" Min play time : " + game.getMinPlayTime()+"mn ");

        age = (TextView) findViewById(R.id.gameAge);
        age.setText("Age : " + String.valueOf(game.getAge()));

        price = (TextView) findViewById(R.id.gamePrice);
        price.setText("Price : " + String.valueOf(game.getPrice()) +"€");

        weight = (TextView) findViewById(R.id.gameWeight);
        weight.setText("Weight : " + String.valueOf(game.getWeight()));

        rate = (TextView) findViewById(R.id.gameRate);
        rate.setText("Rate : " + String.valueOf(game.getRating()));

        picture = (ImageView) findViewById(R.id.gamePicture);
        Picasso.with(GameActivity.this).load(game.getThumbnail()).into(picture);

        categories = (TextView) findViewById(R.id.gameCategories);

        for (String category : game.getCategoryNames()){
            categories.append("\n" + category);
        }
        session = new MySessionManager(getApplicationContext());
        book = (Button) findViewById(R.id.book);
        if(session.getUserDetails().get(MySessionManager.KEY_TOKEN) != null && session.getUserDetails().get(MySessionManager.KEY_EMAIL) != null){
            book.setVisibility(View.VISIBLE);
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GameActivity.this, CalendarBookActivity.class);
                    intent.putExtra("ObjectId", (int)game.getObjectId());
                    intent.putExtra("Game Name", name.getText());
                    intent.putExtra("img", game.getThumbnail());
                    startActivity(intent);
                }
            });
        }

    }
}
