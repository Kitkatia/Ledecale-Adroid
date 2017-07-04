package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cafe.decale.ledecale.adapter.CategoryAdapter;
import com.cafe.decale.ledecale.model.Category;

import java.util.ArrayList;

import static android.R.id.list;

/**
 * Created by manut on 03/07/2017.
 */

public class CategoryListActivity extends Activity implements CategoryListAsync.Listener, AdapterView.OnItemClickListener{
    ListView categories;
    ArrayList<Category> categoyList;


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
        //categoyList.get(position).setValue(1);
    }

    @Override
    public void onLoaded(ArrayList<Category> categoryList) {
        CategoryAdapter categoryAdapter = new CategoryAdapter (CategoryListActivity.this, categoryList);
        categories.setAdapter(categoryAdapter);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }
}

