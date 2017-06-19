package com.cafe.decale.ledecale;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cafe.decale.ledecale.model.Game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by manut on 12/06/2017.
 */

public class GameListActivity extends Activity implements GameListAsync.Listener, AdapterView.OnItemClickListener {

    private ListView list;
    private List<String> gameList = new ArrayList<>();

    private static final String KEY_NAME = "name";
    private static final String KEY_RATE = "rate";
    private static final String KEY_IMG = "img";

    private List<HashMap<String, String>> gameDetailMapList = new ArrayList<>();


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

        Toast.makeText(this, gameDetailMapList.get(i).get(KEY_NAME),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaded(List<Game> games) {
        for (Game game : games) {
            HashMap<String, String> map = new HashMap<>();

            map.put(KEY_NAME, game.getName());
            map.put(KEY_RATE, String.valueOf(game.getRating()));
            map.put(KEY_IMG, game.getThumbnail());

            gameDetailMapList.add(map);

        }

        loadListView();
    }

    private void loadListView() {
        ListAdapter adapter = new SimpleAdapter(GameListActivity.this, gameDetailMapList, R.layout.item_activity,
                new String[] { KEY_NAME, KEY_RATE, KEY_IMG },
                new int[] { R.id.gameName,R.id.gameRate, R.id.gameImage });

        list.setAdapter(adapter);

    }
}