package com.cafe.decale.ledecale;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manut on 09/06/2017.
 */

public class ItemActivity extends Activity {
    ListView list = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);

        list =(ListView) findViewById(R.id.list_view);
        List<String> example = new ArrayList<>();
        example.add("1");
        example.add("2");
        example.add("3");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, example);
        list.setAdapter(adapter);

        //textView = (TextView) findViewById(R.id.textView);

    }
}