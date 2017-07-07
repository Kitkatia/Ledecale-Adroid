package com.cafe.decale.ledecale;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cafe.decale.ledecale.adapter.BookingAdapter;
import com.cafe.decale.ledecale.model.Booking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manut on 19/06/2017.
 */

public class BookingListActivity extends Activity implements BookingListAsync.Listener, AdapterView.OnItemClickListener{
    private ListView list;
    private List<Booking> bookingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_list_activity);

        list = (ListView) findViewById(R.id.list_view);
        list.setOnItemClickListener(this);

        String URL = "https://ledecalebackend-dev.herokuapp.com/";
        new BookingListAsync(this).execute(URL);
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
        BookingAdapter bookingAdapter = new BookingAdapter(BookingListActivity.this, 0, bookingList);

        list.setAdapter(bookingAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
