package com.cafe.decale.ledecale;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by manut on 19/06/2017.
 */

public class EventListActivity extends Activity{
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list_activity);

        list = (ListView) findViewById(R.id.list_view);

    }
}
