package com.cafe.decale.ledecale;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by manut on 03/07/2017.
 */

public class CategoryListActivity extends Activity implements CategoryListAsync.Listener, AdapterView.OnItemClickListener{
    ListView categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_list_activity);

        categories = (ListView) findViewById(R.id.categories);

        String url = "https://ledecalebackend-dev.herokuapp.com/";
        new CategoryListAsync(this).execute(url);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onLoaded(String[] categoryList) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(CategoryListActivity.this,
                android.R.layout.simple_list_item_1, categoryList);
        categories.setAdapter(adapter);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }
}

