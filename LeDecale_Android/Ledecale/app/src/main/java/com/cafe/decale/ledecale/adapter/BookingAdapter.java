package com.cafe.decale.ledecale.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cafe.decale.ledecale.BookActivity;
import com.cafe.decale.ledecale.MyBookings;
import com.cafe.decale.ledecale.MySessionManager;
import com.cafe.decale.ledecale.R;
import com.cafe.decale.ledecale.model.Booking;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by manut on 22/06/2017.
 */

public class BookingAdapter extends ArrayAdapter<Booking> {
    private Context context;

    public BookingAdapter(Context context, int resource, List<Booking> bookings) {
        super(context, resource, bookings);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater unLayoutInflater = LayoutInflater.from(getContext());
        View view = unLayoutInflater.inflate(R.layout.booking_list_item, null);

        TextView name = (TextView) view.findViewById(R.id.bookingName);
        TextView startDate = (TextView) view.findViewById(R.id.startDate);
        TextView endDate = (TextView) view.findViewById(R.id.endDate);
        TextView information = (TextView) view.findViewById(R.id.information);
        //ListView users= (ListView) view.findViewById(R.id.users);
        ImageView bookingImage = (ImageView) view.findViewById(R.id.bookingImage);
        TextView creator = (TextView) view.findViewById(R.id.userName);
        TextView gameName = (TextView) view.findViewById(R.id.gameName);

        Button book = (Button) view.findViewById(R.id.truc);

        MySessionManager session = new MySessionManager(context);

        if(session.getUserDetails().get(MySessionManager.KEY_EMAIL)!= null && session.getUserDetails().get(MySessionManager.KEY_TOKEN)!= null) {
            book.setVisibility(View.VISIBLE);
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BookActivity.class);
                    context.startActivity(intent);
                }
            });
        }
        Booking booking = getItem(position);

        name.setText(booking.getName());
        name.setTextColor(Color.BLACK);

        startDate.setText("Scheduled for: " + new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date(Long.parseLong(booking.getStartDate()))));
        startDate.setTextColor(Color.BLACK);

        endDate.setText("Ends: " + new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date(Long.parseLong(booking.getEndDate()))));
        endDate.setTextColor(Color.BLACK);

        information.setText(Html.fromHtml(booking.getInformation()));
        information.setTextColor(Color.BLACK);

        creator.setText(booking.getCreator().getPseudo());
        creator.setTextColor(Color.BLACK);

        gameName.setText(booking.getName());
        gameName.setTextColor(Color.BLACK);

        Picasso.with(context).load(booking.getUrlImage()).into(bookingImage);

        return view;


    }
}
