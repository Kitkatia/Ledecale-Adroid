package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cafe.decale.ledecale.adapter.EventAdapter;
import com.cafe.decale.ledecale.model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manut on 19/06/2017.
 */

public class EventListActivity extends Activity implements EventListAsync.Listener, AdapterView.OnItemClickListener{
    private ListView list;
    private List<Event> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list_activity);

        list = (ListView) findViewById(R.id.list_view);
        list.setOnItemClickListener(this);

        String URL = "https://ledecalebackend-dev.herokuapp.com/";
        new EventListAsync(this).execute(URL);
    }

    @Override
    public void onLoaded(List<Event> events) {
        Log.i("DECALE", "onLoaded");
        eventList = events;
        loadListView();
    }

    @Override
    public void onError() {

        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }

    private void loadListView() {
        Log.i("DECALE", "loadListView");
        Log.i("DECALE", "" + eventList.size());
        EventAdapter eventAdapter = new EventAdapter(EventListActivity.this, 0, eventList);

        list.setAdapter(eventAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
