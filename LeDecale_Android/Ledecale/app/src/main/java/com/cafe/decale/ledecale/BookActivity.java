package com.cafe.decale.ledecale;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by manut on 08/07/2017.
 */

public class BookActivity extends Activity {
    EditText numberPlayers, endDate, startDate;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);
        numberPlayers = (EditText) findViewById(R.id.numPlayers);
        endDate = (EditText) findViewById(R.id.endDate);
        startDate = (EditText) findViewById(R.id.startDate);
        name = (TextView) findViewById(R.id.gameName);

        name.setText((String) getIntent().getSerializableExtra("Game Name"));



    }
}
