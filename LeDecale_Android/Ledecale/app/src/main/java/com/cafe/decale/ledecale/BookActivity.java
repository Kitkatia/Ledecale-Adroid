package com.cafe.decale.ledecale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squareup.picasso.Picasso;

/**
 * Created by manut on 08/07/2017.
 */

public class BookActivity extends Activity {
    EditText numberPlayers;
    TextView name;
    ImageView gameImage;
    EditText duration;
    Button startDate;
    Button time;
    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 0;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);
        numberPlayers = (EditText) findViewById(R.id.numPlayers);
        name = (TextView) findViewById(R.id.gameName);
        gameImage = (ImageView) findViewById(R.id.gameImage);
        duration = (EditText) findViewById(R.id.duration);
        startDate = (Button) findViewById(R.id.dateStart);
        time = (Button) findViewById(R.id.time);

        name.setText((String) getIntent().getSerializableExtra("Game Name"));
        Picasso.with(BookActivity.this).load(getIntent().getSerializableExtra("img").toString()).into(gameImage);



        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, mTimeSetListener,
                        hour, minute, false);
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

            startDate.setText(""+day+"/"+(month+1)+"/"+year);

        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
                    hour = hourOfDay;
                    minute = minuteOfDay;
                    time.setText(hour + ":"+ minute);
                }
            };
}
