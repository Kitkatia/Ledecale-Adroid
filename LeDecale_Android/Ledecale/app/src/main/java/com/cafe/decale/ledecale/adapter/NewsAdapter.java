package com.cafe.decale.ledecale.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cafe.decale.ledecale.R;
import com.cafe.decale.ledecale.model.News;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by manut on 20/06/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private Context context;

    public NewsAdapter(Context context, int resource, List<News> listNews) {
        super(context, resource, listNews);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater unLayoutInflater = LayoutInflater.from(getContext());
        View view = unLayoutInflater.inflate(R.layout.news_list_item, null);

        TextView title = (TextView) view.findViewById(R.id.newsTitle);
        TextView content = (TextView) view.findViewById(R.id.newsContent);
        ImageView picture = (ImageView) view.findViewById(R.id.newsPicture);
        TextView creationDate = (TextView) view.findViewById(R.id.creationDate);
        TextView newsDate = (TextView) view.findViewById(R.id.newsDate);

        News news = getItem(position);

        title.setText(news.getTitle());
        title.setTextColor(Color.BLACK);

        content.setText(Html.fromHtml(news.getContent()));
        content.setTextColor(Color.BLACK);

        creationDate.setText("Created: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(news.getDateCreation()))));
        creationDate.setTextColor(Color.BLACK);

        newsDate.setText("Scheduled for: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(news.getDateNews()))));
        creationDate.setTextColor(Color.BLACK);

        Picasso.with(context).load(news.getUrlImage()).into(picture);

        return view;


    }
}
