package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cafe.decale.ledecale.adapter.NewsAdapter;
import com.cafe.decale.ledecale.model.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manut on 19/06/2017.
 */

public class NewsListActivity extends Activity implements NewsListAsync.Listener, AdapterView.OnItemClickListener {

    private ListView list;
    private List<News> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list_activity);

        list = (ListView) findViewById(R.id.list_view);
        list.setOnItemClickListener(this);

        String URL = "https://ledecalebackend-dev.herokuapp.com/";
        new NewsListAsync(this).execute(URL);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
    }

    @Override
    public void onLoaded(List<News> newsList) {
        this.newsList = newsList;
        loadListView();
    }
    private void loadListView() {
        Log.i("DECALE", "loadListView");
        Log.i("DECALE", "" + this.newsList.size());
        NewsAdapter newsAdapter = new NewsAdapter(NewsListActivity.this, 0, newsList);

        list.setAdapter(newsAdapter);

    }
    @Override
    public void onError() {
        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }
}
