package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cafe.decale.ledecale.adapter.CategoryAdapter;
import com.cafe.decale.ledecale.model.Category;

import java.util.ArrayList;


/**
 * Created by manut on 03/07/2017.
 */

public class CategoryListActivity extends Activity implements CategoryListAsync.Listener, AdapterView.OnItemClickListener{
    ListView categories;
    Button findGameByCat;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categoryList = new ArrayList<>();

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_list_activity);

        categories = (ListView) findViewById(R.id.categories);
        findGameByCat = (Button) findViewById(R.id.findSelected);

        findGameByCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Category> categoriesSelected = new ArrayList<>();
                StringBuffer response = new StringBuffer();
                response.append("The following were selected...\n");
                if(categoryList.isEmpty()){
                    return;
                }
                for(Category category : categoryList){
                    if(category.getIsSelected()){
                        response.append("\n" + category.getName());
                        categoriesSelected.add(category);
                    }
                }
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", categoriesSelected);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
       String url = "https://ledecalebackend-dev.herokuapp.com/";
        new CategoryListAsync(this).execute(url);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Category category = (Category) parent.getItemAtPosition(position);
    }

    @Override
    public void onLoaded(ArrayList<Category> categoryList) {
        this.categoryList.addAll(categoryList);
        categoryAdapter = new CategoryAdapter (CategoryListActivity.this, categoryList);
        categories.setAdapter(categoryAdapter);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }
}

