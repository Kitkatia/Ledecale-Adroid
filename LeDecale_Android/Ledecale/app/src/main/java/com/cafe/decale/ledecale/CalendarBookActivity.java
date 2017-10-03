package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cafe.decale.ledecale.Utils.AlertDialogManager;
import com.cafe.decale.ledecale.model.EventObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by manut on 24/09/2017.
 */

public class CalendarBookActivity extends Activity implements EventsAsync.Listener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView previousDay;
    private ImageView nextDay;
    private TextView currentDate;
    private Button ok;
    private ArrayList<TextView> textViews = new ArrayList<>();
    private Calendar cal = Calendar.getInstance();
    private RelativeLayout mLayout;
    HashMap<Integer, ArrayList<EventObject>> eventObjects = new HashMap<>();
    AlertDialogManager alert = new AlertDialogManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_book_activity);
        mLayout = (RelativeLayout)findViewById(R.id.left_event_column);
        currentDate = (TextView)findViewById(R.id.display_current_date);
        currentDate.setText(displayDateInString(cal.getTime()));
        final int id = (int) getIntent().getSerializableExtra("ObjectId");
        new EventsAsync(this).execute("https://ledecalebackend-dev.herokuapp.com/", String.valueOf(id));

        previousDay = (ImageView)findViewById(R.id.previous_day);
        nextDay = (ImageView)findViewById(R.id.next_day);
        previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousCalendarDate();
            }
        });
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCalendarDate();
            }
        });
        ok = (Button) findViewById(R.id.book);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarBookActivity.this, BookActivity.class);
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                intent.putExtra("ObjectId", id);
                intent.putExtra("Date", format1.format(cal.getTime()));
                intent.putExtra("img", CalendarBookActivity.this.getIntent().getSerializableExtra("img"));
                intent.putExtra("Game Name", CalendarBookActivity.this.getIntent().getSerializableExtra("Game Name"));
                startActivity(intent);
            }
        });
    }
    private void previousCalendarDate(){
        for(TextView tv : textViews){
            mLayout.removeView(tv);
        }
        Date curDate = Calendar.getInstance().getTime();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        if(cal.getTime().getDate() == curDate.getDate()){

        }
        else if(cal.getTime().before(curDate)) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            alert.showAlertDialog(this, "Error", "Cannot go back in time" , false);
            return;
        }
        currentDate.setText(displayDateInString(cal.getTime()));
        displayDailyEvents();
    }
    private void nextCalendarDate(){
        for(TextView tv : textViews){
            mLayout.removeView(tv);
        }
        Date curDate = Calendar.getInstance().getTime();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        if(daysBetween(curDate, cal.getTime()) >= 14){
            cal.add(Calendar.DAY_OF_MONTH, -1);
            alert.showAlertDialog(this, "Error", "Cannot go in the future" , false);
            return;
        }
        currentDate.setText(displayDateInString(cal.getTime()));
        displayDailyEvents();
    }
    public static Calendar getDatePart(Date date){
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;                                  // return the date part
    }
    public static long daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }
    private String displayDateInString(Date mDate){
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH);
        return formatter.format(mDate);
    }
    private void displayDailyEvents(){
        if(!this.eventObjects.containsKey(cal.getTime().getDate())){
            return;
        }
        for(EventObject eObject : this.eventObjects.get(cal.getTime().getDate())){
            Date eventDate = eObject.getDate();
            Date endDate = eObject.getEnd();
            int eventBlockHeight = getEventTimeFrame(eventDate, endDate);
            Log.d(TAG, "Height " + eventBlockHeight);
            displayEventSection(eventDate, eventBlockHeight, eObject.getMessage());
        }
    }
    private int getEventTimeFrame(Date start, Date end){
        long timeDifference = end.getTime() - start.getTime();
        int conversion = 1000 * 60 * 60;
        int minutes =  (int) timeDifference % conversion <60000 ? (int) timeDifference % conversion : ((int) timeDifference) % conversion /60000;

        int hours = (int) timeDifference / conversion;
        return (hours * 60) + ((minutes * 60) / 100);
    }
    private void displayEventSection(Date eventDate, int height, String message){
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String displayValue = timeFormatter.format(eventDate);
        String[]hourMinutes = displayValue.split(":");
        int hours = Integer.parseInt(hourMinutes[0]) - 3;
        int minutes = Integer.parseInt(hourMinutes[1]);
        Log.d(TAG, "Hour value " + hours);
        Log.d(TAG, "Minutes value " + minutes);
        int topViewMargin = (hours * 60) + ((minutes * 60) / 100);
        Log.d(TAG, "Margin top " + topViewMargin);
        createEventView(topViewMargin, height, message);
    }
    private void createEventView(int topMargin, int height, String message){
        TextView mEventView = new TextView(CalendarBookActivity.this);
        RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
        lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lParam.topMargin = topMargin * 2;
        lParam.leftMargin = 24;
        mEventView.setLayoutParams(lParam);
        mEventView.setPadding(24, 0, 24, 0);
        mEventView.setHeight(height * 2);
        mEventView.setGravity(0x11);
        mEventView.setTextColor(Color.parseColor("#ffffff"));
        mEventView.setBackgroundColor(Color.parseColor("#3F51B5"));
        mEventView.setText(message);
        mLayout.addView(mEventView);
        textViews.add(mEventView);
    }


    @Override
    public void onLoaded(HashMap<Integer, ArrayList<EventObject>> events) {
        this.eventObjects.putAll(events);
        displayDailyEvents();
    }

    @Override
    public void onError() {
        alert.showAlertDialog(CalendarBookActivity.this, "Error", "There has been an error", false);
    }
}
