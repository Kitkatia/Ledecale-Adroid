package com.cafe.decale.ledecale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cafe.decale.ledecale.adapter.BookingAdapter;
import com.cafe.decale.ledecale.model.Booking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manut on 08/07/2017.
 */

public class MyBookings extends Activity implements MyBookingsAsync.Listener, AdapterView.OnItemClickListener{
    private ListView list;
     private List<Booking> bookingList = new ArrayList<>();
    MySessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_list_activity);

        list = (ListView) findViewById(R.id.list_view);
        list.setOnItemClickListener(this);

        session = new MySessionManager(getApplicationContext());

        String URL = "https://ledecalebackend-dev.herokuapp.com/";
        new MyBookingsAsync(this).execute(URL, session.getUserDetails().get(MySessionManager.KEY_TOKEN));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onLoaded(List<Booking> bookings) {
        Log.i("DECALE", "onLoaded");
        bookingList = bookings;
        loadListView();
    }

    @Override
    public void onError() {

        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }

    private void loadListView() {
        Log.i("DECALE", "loadListView");
        Log.i("DECALE", "" + bookingList.size());
        BookingAdapter bookingAdapter = new BookingAdapter(MyBookings.this, 0, bookingList);

        list.setAdapter(bookingAdapter);

    }
}
