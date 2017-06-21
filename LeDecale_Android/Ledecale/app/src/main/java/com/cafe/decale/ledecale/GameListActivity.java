package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cafe.decale.ledecale.adapter.GameAdapter;
import com.cafe.decale.ledecale.model.Game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by manut on 12/06/2017.
 */

public class GameListActivity extends Activity implements GameListAsync.Listener, AdapterView.OnItemClickListener {

    private ListView list;
    private List<Game> gameList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_list_activity);

        list = (ListView) findViewById(R.id.list_view);
        list.setOnItemClickListener(this);

        String URL = "https://ledecalebackend-dev.herokuapp.com/";
        new GameListAsync(this).execute(URL);
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
        gameList = games;
        loadListView();
    }

    private void loadListView() {
        Log.i("DECALE", "loadListView");
        Log.i("DECALE", "" + gameList.size());
        GameAdapter gameAdapter = new GameAdapter(GameListActivity.this, 0, gameList);

        list.setAdapter(gameAdapter);

    }
}