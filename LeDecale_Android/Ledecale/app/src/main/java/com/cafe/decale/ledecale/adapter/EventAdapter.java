package com.cafe.decale.ledecale.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cafe.decale.ledecale.R;
import com.cafe.decale.ledecale.model.Event;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by manut on 22/06/2017.
 */

public class EventAdapter extends ArrayAdapter<Event> {
    private Context context;

    public EventAdapter(Context context, int resource, List<Event> events) {
        super(context, resource, events);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater unLayoutInflater = LayoutInflater.from(getContext());
        View view = unLayoutInflater.inflate(R.layout.event_list_item, null);

        TextView name = (TextView) view.findViewById(R.id.eventName);
        TextView creationDate = (TextView) view.findViewById(R.id.creationDate);
        TextView startDate = (TextView) view.findViewById(R.id.startDate);
        TextView endDate = (TextView) view.findViewById(R.id.endDate);
        TextView information = (TextView) view.findViewById(R.id.information);
        //ListView users= (ListView) view.findViewById(R.id.users);
        TextView creator = (TextView) view.findViewById(R.id.userName);


        Event event = getItem(position);

        name.setText(event.getName());
        name.setTextColor(Color.BLACK);

        creationDate.setText("Created: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(event.getCreationDate()))));
        creationDate.setTextColor(Color.BLACK);

        startDate.setText("Scheduled for: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(event.getStartDate()))));
        startDate.setTextColor(Color.BLACK);

        endDate.setText("Ends: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(event.getEndDate()))));
        endDate.setTextColor(Color.BLACK);

        information.setText(Html.fromHtml(event.getInformation()));
        information.setTextColor(Color.BLACK);

        creator.setText(event.getCreator().getPseudo());
        creator.setTextColor(Color.BLACK);

        return view;


    }
}
