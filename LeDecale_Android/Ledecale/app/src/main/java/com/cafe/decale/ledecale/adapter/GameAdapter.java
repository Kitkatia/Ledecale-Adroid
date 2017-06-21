package com.cafe.decale.ledecale.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cafe.decale.ledecale.R;
import com.cafe.decale.ledecale.model.Game;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GameAdapter extends ArrayAdapter<Game> {

    private Context context;

    public GameAdapter(Context context, int resource, List<Game> listGames) {
        super(context, resource, listGames);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater unLayoutInflater = LayoutInflater.from(getContext());
        View view = unLayoutInflater.inflate(R.layout.game_list_item, null);

        TextView name = (TextView) view.findViewById(R.id.gameName);
        TextView rate = (TextView) view.findViewById(R.id.gameRate);
        ImageView picture = (ImageView) view.findViewById(R.id.gamePicture);

        Game g = getItem(position);

        name.setText(g.getName()+ "\n");
        name.setTextColor(Color.BLACK);

        String rating = context.getString(R.string.rate) + String.valueOf(g.getRating());

        rate.setText(rating);
        rate.setTextColor(Color.BLACK);

        Picasso.with(context).load(g.getThumbnail()).into(picture);



        return view;
    }
}
