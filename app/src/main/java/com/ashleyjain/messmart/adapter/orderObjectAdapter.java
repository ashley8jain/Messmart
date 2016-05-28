package com.ashleyjain.messmart.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashleyjain.messmart.Object.OrderObject;
import com.ashleyjain.messmart.R;

import java.util.List;

import static com.ashleyjain.messmart.R.id.dinnerlinear;

/**
 * Created by ashleyjain on 24/05/16.
 */
public class orderObjectAdapter extends BaseAdapter {

    Context context;
    List<OrderObject> orderList;

    public orderObjectAdapter(Context context, List<OrderObject> orderList) {
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
            convertView = mInflater.inflate(R.layout.order_list_item, null);
        }

        LinearLayout lunchempty = (LinearLayout) convertView.findViewById(R.id.lunchlinear);
        LinearLayout dinnerempty = (LinearLayout) convertView.findViewById(dinnerlinear);
        TextView dateTitle = (TextView) convertView.findViewById(R.id.date);
        TextView loid = (TextView) convertView.findViewById(R.id.oid);
        TextView doid = (TextView) convertView.findViewById(R.id.oid2);
        TextView ldish = (TextView) convertView.findViewById(R.id.dish);
        TextView ddish = (TextView) convertView.findViewById(R.id.dish2);
        TextView lmess = (TextView) convertView.findViewById(R.id.mess);
        TextView dmess = (TextView) convertView.findViewById(R.id.mess2);
        TextView lprice = (TextView) convertView.findViewById(R.id.price);
        TextView dprice = (TextView) convertView.findViewById(R.id.price2);
        TextView lstatus = (TextView) convertView.findViewById(R.id.status);
        TextView dstatus = (TextView) convertView.findViewById(R.id.status2);


        OrderObject row = orderList.get(position);

        lunchempty.setBackgroundColor(Color.parseColor("#ffffff"));
        dateTitle.setText(row.getDate());
        loid.setText(""+row.getlOIDid());
        doid.setText(""+row.getdOIDid());
        ldish.setText(row.getlDish());
        ddish.setText(row.getdDish());
        lmess.setText(row.getlMess());
        dmess.setText(row.getdMess());
        lprice.setText(""+row.getlPrice()+"/-");
        dprice.setText(""+row.getdPrice()+"/-");
        lstatus.setText(row.getlStatus());
        dstatus.setText(row.getdStatus());

        return convertView;
    }
}
