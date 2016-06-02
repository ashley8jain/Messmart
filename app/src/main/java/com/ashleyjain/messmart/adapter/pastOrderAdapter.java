package com.ashleyjain.messmart.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ashleyjain.messmart.Object.PastOrderObject;
import com.ashleyjain.messmart.R;

import java.util.List;

import static com.ashleyjain.messmart.R.id.titleeee;

/**
 * Created by ashleyjain on 02/06/16.
 */
public class pastOrderAdapter extends BaseAdapter {

    Context context;
    List<PastOrderObject> orderList;

    public pastOrderAdapter(Context context, List<PastOrderObject> orderList) {
        this.context = context;
        this.orderList = orderList;

    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.past_order_list_item, null);

        }

        TextView title = (TextView) convertView.findViewById(titleeee);
        PastOrderObject row = orderList.get(position);
        title.setText(row.getDish());

        return convertView;
    }
}
