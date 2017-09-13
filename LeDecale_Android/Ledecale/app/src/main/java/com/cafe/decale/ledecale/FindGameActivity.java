package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SearchView;

import com.cafe.decale.ledecale.model.Category;

import java.util.ArrayList;


/**
 * Created by manut on 07/08/2017.
 */

public class FindGameActivity extends Activity implements NumberPicker.OnValueChangeListener{
    NumberPicker numPlayerPicker;
    NumberPicker maxPlayPicker;
    ArrayList<Category> categories = new ArrayList<>();

     @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     if(data!= null && requestCode ==1 && data.hasExtra("result")){
         this.categories = (ArrayList<Category>) data.getSerializableExtra("result");
     }
 }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_game);
        SearchView search = (SearchView) findViewById(R.id.search);
        final CharSequence query = search.getQuery();

        final Button categoriesFilter = (Button) findViewById(R.id.categories);
        categoriesFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindGameActivity.this, CategoryListActivity.class);
                startActivityForResult(intent, 1);

            }
        });

        numPlayerPicker = (NumberPicker) findViewById(R.id.numMaxPLayer);
        numPlayerPicker.setMinValue(0);
        numPlayerPicker.setMaxValue(20);
        numPlayerPicker.setWrapSelectorWheel(true);
        numPlayerPicker.setOnValueChangedListener(this);

        maxPlayPicker = (NumberPicker) findViewById(R.id.maxPlayTime);
        maxPlayPicker.setMaxValue(5);
        //maxPlayPicker.setWrapSelectorWheel(true);
        maxPlayPicker.setDisplayedValues(new String []{"30", "60", "90", "120", "150", "180"});
        maxPlayPicker.setOnValueChangedListener(this);

        Button findGame = (Button) findViewById(R.id.findGame);
        findGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindGameActivity.this, GameListActivity.class);
                intent.putExtra("numPlayer", numPlayerPicker.getValue());
                String [] displayedValues = maxPlayPicker.getDisplayedValues();
                intent.putExtra("maxPlayTime", displayedValues[maxPlayPicker.getValue()]);
                if(query.length() != 0)
                    intent.putExtra("query", query.toString());
                intent.putExtra("categories", categories);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if(picker == numPlayerPicker){
            numPlayerPicker.setValue(newVal);
        }
        else{
            maxPlayPicker.setValue(newVal);
        }
    }
}
