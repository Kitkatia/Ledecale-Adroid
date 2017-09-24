package com.cafe.decale.ledecale.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cafe.decale.ledecale.Utils.AlertDialogManager;
import com.cafe.decale.ledecale.JoinBookingAsync;
import com.cafe.decale.ledecale.MySessionManager;
import com.cafe.decale.ledecale.R;
import com.cafe.decale.ledecale.UnbookAsync;
import com.cafe.decale.ledecale.model.Booking;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by manut on 22/06/2017.
 */

public class BookingAdapter extends ArrayAdapter<Booking> implements JoinBookingAsync.Listener{
    private Context context;
    AlertDialogManager alert;
    Button book;
    Button unbook;

    public BookingAdapter(Context context, int resource, List<Booking> bookings) {
        super(context, resource, bookings);
        this.context = context;
        alert = new AlertDialogManager();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater unLayoutInflater = LayoutInflater.from(getContext());
        View view = unLayoutInflater.inflate(R.layout.booking_list_item, null);

        TextView name = (TextView) view.findViewById(R.id.bookingName);
        TextView startDate = (TextView) view.findViewById(R.id.startDate);
        TextView endDate = (TextView) view.findViewById(R.id.textViewEndDate);
        TextView information = (TextView) view.findViewById(R.id.information);
        //ListView users= (ListView) view.findViewById(R.id.users);
        ImageView bookingImage = (ImageView) view.findViewById(R.id.bookingImage);
        TextView creator = (TextView) view.findViewById(R.id.userName);
        TextView gameName = (TextView) view.findViewById(R.id.gameName);

        book = (Button) view.findViewById(R.id.book);
        unbook = (Button) view.findViewById(R.id.unbook);

        MySessionManager session = new MySessionManager(context);

        final String token = session.getUserDetails().get(MySessionManager.KEY_TOKEN);
        final Booking booking = getItem(position);
        final String email = session.getUserDetails().get(MySessionManager.KEY_EMAIL);
        if(email != null && token!= null) {
            if(booking.contains(email)) {
                unbook.setVisibility(View.VISIBLE);
                book.setVisibility(View.INVISIBLE);
            }
            else{
                unbook.setVisibility(View.INVISIBLE);
                book.setVisibility(View.VISIBLE);
            }

            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new JoinBookingAsync(BookingAdapter.this).execute("https://ledecalebackend-dev.herokuapp.com/booking/addUser", token, booking.getId().toString());

                }
            });
            unbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new UnbookAsync(BookingAdapter.this).execute("https://ledecalebackend-dev.herokuapp.com/booking/delUser", token, booking.getId().toString());
                }
            });
        }

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

    @Override
    public void onLoaded(boolean hasJoined, String status) {
        if(hasJoined){
            if(status.equals("joined")) {
                book.setVisibility(View.INVISIBLE);
                unbook.setVisibility(View.VISIBLE);
            }
            else if(status.equals("unsubscribed")){
                book.setVisibility(View.VISIBLE);
                unbook.setVisibility(View.INVISIBLE);
            }
            alert.showAlertDialog(context, status, "You have successfully "+status +" the event", true);
        }
        else {
            alert.showAlertDialog(context, "Fail", "Cannot "+status +" the current event", false);
        }
    }

    @Override
    public void onError(String status) {
        alert.showAlertDialog(context, "Fail", "Cannot "+status+"join the current event", false);
    }
}
