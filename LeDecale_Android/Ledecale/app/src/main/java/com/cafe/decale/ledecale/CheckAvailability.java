package com.cafe.decale.ledecale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by manut on 26/09/2017.
 */

public class CheckAvailability extends Activity {
    Button checkAvailability;
    Button dateStart;
    ImageView gameImage;
    TextView gameName;

    private int year;
    private int month;
    private int day;
    static final int DATE_DIALOG_ID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_availability);

        checkAvailability = (Button) findViewById(R.id.availability);
        dateStart = (Button) findViewById(R.id.date);
        gameImage = (ImageView) findViewById(R.id.gameImage);
        gameName = (TextView) findViewById(R.id.gameName);

        gameName.setText(getIntent().getSerializableExtra("Game Name").toString());
        Picasso.with(CheckAvailability.this).load(CheckAvailability.this.getIntent().getSerializableExtra("img").toString()).into(gameImage);

        dateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        checkAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckAvailability.this, CalendarBookActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            dateStart.setText(""+day+"/"+(month+1)+"/"+year);

        }
    };
}
