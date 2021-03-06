package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cafe.decale.ledecale.adapter.GameAdapter;
import com.cafe.decale.ledecale.model.Category;
import com.cafe.decale.ledecale.model.Game;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by manut on 12/06/2017.
 */

public class GameListActivity extends Activity implements GameListAsync.Listener, AdapterView.OnItemClickListener {

    private ListView list;
    private List<Game> gameList = new ArrayList<>();
    int numPlayer;
    int maxPlayTime;
    String query;
    ArrayList<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_list_activity);

        numPlayer = getIntent().getIntExtra("numPlayer", 100);
        numPlayer = numPlayer == 0 ? 20 : numPlayer;
        maxPlayTime = getIntent().getIntExtra("maxPlayTime", 180);
        maxPlayTime = maxPlayTime == 0 ? 300 : maxPlayTime;
        if(getIntent().hasExtra("query"))
            query = getIntent().getStringExtra("query");

        categories = (ArrayList<Category>) getIntent().getSerializableExtra("categories");

        list = (ListView) findViewById(R.id.list_view);
        list.setOnItemClickListener(this);

        String url = "https://ledecalebackend-dev.herokuapp.com/";
        new GameListAsync(this).execute(url);
    }

    @Override
    public void onError() {

        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(GameListActivity.this, GameActivity.class);
        intent.putExtra("game", gameList.get(i));
        startActivity(intent);
    }

    @Override
    public void onLoaded(List<Game> games) {
        Log.i("DECALE", "onLoaded");
        ArrayList<Game> queryGames = new ArrayList<>();

        for(Game game : games){
            if(query!=null) {
                if (game.getName().toLowerCase().contains(query.toLowerCase()) || query.toLowerCase().contains(game.getName().toLowerCase())) {
                    queryGames.add(game);
                }
            }
            else{
                queryGames.add(game);
            }
        }
        for (Game game : queryGames) {
            if (!this.categories.isEmpty()) {
                for (Category category : this.categories) {
                    for (String gameCategoryName : game.getCategoryNames()) {
                        if (gameCategoryName.equals(category.getName())) {
                            if (game.getMaxPlayTime() <= maxPlayTime && game.getPlayerMax() <= numPlayer)
                                gameList.add(game);
                        } else {
                            continue;
                        }
                    }
                }
            }
            else if (game.getMaxPlayTime() <= maxPlayTime && game.getPlayerMax() <= numPlayer)
                gameList.add(game);
        }
        loadListView();
    }

    private void loadListView() {
        Log.i("DECALE", "loadListView");
        Log.i("DECALE", "" + gameList.size());
        GameAdapter gameAdapter = new GameAdapter(GameListActivity.this, 0, gameList);

        list.setAdapter(gameAdapter);

    }
}