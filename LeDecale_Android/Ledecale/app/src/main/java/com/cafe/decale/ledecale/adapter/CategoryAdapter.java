package com.cafe.decale.ledecale.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cafe.decale.ledecale.R;
import com.cafe.decale.ledecale.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manut on 04/07/2017.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {
    List<Category> categories = null;
    private Context context;

    public CategoryAdapter(Context context, List<Category> categories) {
        super(context, R.layout.category_list_item, categories);
        this.context = context;
        this.categories = categories;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.category_list_item, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.categoryName);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.categoryChecked);
        name.setText(categories.get(position).getName());
        if(categories.get(position).getValue() == 1)
            cb.setChecked(true);
        else
            cb.setChecked(false);
        return convertView;
    }
}

