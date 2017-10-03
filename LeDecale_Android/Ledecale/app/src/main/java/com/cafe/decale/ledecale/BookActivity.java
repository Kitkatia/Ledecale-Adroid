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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cafe.decale.ledecale.Utils.AlertDialogManager;
import com.cafe.decale.ledecale.Utils.JWTUtils;
import com.cafe.decale.ledecale.model.Booking;
import com.cafe.decale.ledecale.model.User;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by manut on 08/07/2017.
 */

public class BookActivity extends Activity implements BookActivityAsync.Listener {
    TextView name;
    Button endTime;
    Button startDate;
    Button startTime;
    Button book;
    Switch openBooking;
    ImageView gameImage;
    EditText numberPlayers;

    Boolean isOpen;
    String calendarDate;
    static final int DATE_DIALOG_ID = 999;
    static final int START_DIALOG_ID = 0;
    static final int END_DIALOG_ID = 888;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);
        numberPlayers = (EditText) findViewById(R.id.numPlayers);
        name = (TextView) findViewById(R.id.gameName);
        gameImage = (ImageView) findViewById(R.id.gameImage);
        endTime = (Button) findViewById(R.id.timeEnd);
        startDate = (Button) findViewById(R.id.dateStart);
        startTime = (Button) findViewById(R.id.time);
        openBooking = (Switch) findViewById(R.id.openBooking);
        book = (Button) findViewById(R.id.findAvailability);

        openBooking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isOpen = isChecked;
            }
        });
        name.setText((String) getIntent().getSerializableExtra("Game Name"));
        final String objectId = String.valueOf((int)getIntent().getSerializableExtra("ObjectId"));
        Picasso.with(BookActivity.this).load(getIntent().getSerializableExtra("img").toString()).into(gameImage);
        calendarDate = getIntent().getSerializableExtra("Date").toString() ;
        startDate.setText(calendarDate);



        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(START_DIALOG_ID);
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(END_DIALOG_ID);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = JWTUtils.decoded(new MySessionManager(getApplicationContext()).getUserDetails().get(MySessionManager.KEY_TOKEN));
                Long startingDate, endDate;
                if(!compareToCalendarDate()){
                    alert.showAlertDialog(getApplicationContext(), "Check Calendar", "Would you please check the calendar for the selected date?", false);
                    alert.alertDialog.getButton(-1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BookActivity.super.onBackPressed();
                        }
                    });
                }
                if(notEmpty(numberPlayers) &&  notEmpty(startDate) && notEmpty(startTime) && notEmpty(endTime)){
                    try {
                        startingDate = Long.valueOf(getMillis(startDate.getText().toString(), startTime.getText().toString()));
                        endDate = Long.valueOf(getMillis(startDate.getText().toString(), endTime.getText().toString()));
                        if(startingDate < endDate) {
                            String opnCls = isOpen == null || isOpen ? "CLS" : "OPN";
                            new BookActivityAsync(BookActivity.this).execute("https://ledecalebackend-dev.herokuapp.com/booking", opnCls,
                                    startingDate.toString(), endDate.toString(), objectId, user.getId().toString(), numberPlayers.getText().toString(),
                                    name.getText().toString(), getIntent().getSerializableExtra("Date").toString());

                        }
                        else{
                            alert.showAlertDialog(BookActivity.this, "Date problem", "the ending date is before the starting date", false);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private boolean notEmpty(Button date) {
        if(date.getText().length()== 0){
            alert.showAlertDialog(BookActivity.this, "missing date", "", false);
            return false;
        }
        return date.getText().length()!= 0;
    }

    private String getMillis(String startDate, String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return Long.toString(sdf.parse(startDate+" "+ time).getTime());
    }

    private boolean notEmpty(EditText numberPlayers) {
        if(numberPlayers.getText().length() == 0){
            alert.showAlertDialog(BookActivity.this, "num players missing", "", false);
            return false;
        }
        else
            return Integer.parseInt(numberPlayers.getText().toString()) < 6 && Integer.parseInt(numberPlayers.getText().toString()) >0;
    }

    private Boolean compareToCalendarDate (){
        return(startDate.getText().toString().equals(calendarDate));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
            case START_DIALOG_ID:
                return new TimePickerDialog(this, mTimeSetListener,
                        hour, minute, false);
            case END_DIALOG_ID:
                return new TimePickerDialog(this, msTimeSetListener,
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
                    startTime.setText(hour + ":"+ minute);
                }
            };

    private TimePickerDialog.OnTimeSetListener msTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
                    hour = hourOfDay;
                    minute = minuteOfDay;
                    endTime.setText(hour + ":"+ minute);
                }
            };

    @Override
    public void onLoaded(Boolean isBooked) {
        if(isBooked){
            alert.showAlertDialog(BookActivity.this, "Success", "Your booking is created", true);
        }
    }

    @Override
    public void onError() {
        alert.showAlertDialog(BookActivity.this, "Fail", "There has been an error", false);
    }
}
