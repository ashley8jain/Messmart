package com.ashleyjain.messmart;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ashleyjain on 19/05/16.
 */
public class messObjectAdapter extends BaseAdapter {

    Context context;
    List<MessObject> messList;

    public messObjectAdapter(Context context, List<MessObject> messList) {
        this.context = context;
        this.messList = messList;

    }

    @Override
    public int getCount() {
        return messList.size();
    }

    @Override
    public Object getItem(int position) {
        return messList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return messList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.mess_list_item, null);
        }

        TextView messTitle = (TextView) convertView.findViewById(R.id.title);
        TextView messDescription = (TextView) convertView.findViewById(R.id.description);
        TextView Prices = (TextView) convertView.findViewById(R.id.price);
        TextView name = (TextView) convertView.findViewById(R.id.messmakername);
        ImageView messimg = (ImageView) convertView.findViewById(R.id.messimg);
        ImageView vegimg = (ImageView) convertView.findViewById(R.id.imgveg);

        MessObject row = messList.get(position);

        messTitle.setText(row.getTitle());
        messDescription.setText(row.getDescription());
        name.setText(row.getName());
        Prices.setText("" + row.getPrice() + "/-");
        Picasso.with(context).load(StartActivity.host+row.getPic()).into(messimg);
        vegimg.setImageResource(row.isVeg()?R.drawable.veg:R.drawable.nonveg);


        return convertView;
    }
}
