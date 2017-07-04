package com.cafe.decale.ledecale.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cafe.decale.ledecale.R;
import com.cafe.decale.ledecale.model.Category;

import java.util.ArrayList;


/**
 * Created by manut on 04/07/2017.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {
    public ArrayList<Category> categories;
    private Context context;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        super(context, R.layout.category_list_item, categories);
        this.context = context;
        this.categories = new ArrayList<>();
        this.categories.addAll(categories);
    }
    private class ViewHolder{
        TextView code;
        CheckBox name;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.category_list_item, parent, false);
            holder = new ViewHolder();

            holder.code = (TextView) convertView.findViewById(R.id.categoryName);
            holder.name = (CheckBox) convertView.findViewById(R.id.categoryChecked);
            convertView.setTag(holder);

            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Category category = (Category) cb.getTag();
                    category.setIsSelected(cb.isChecked());
                }
            });
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        Category category = categories.get(position);
        holder.code.setText(category.getName());
        holder.name.setChecked(category.getIsSelected());
        holder.name.setTag(category);

        return convertView;
    }

}

