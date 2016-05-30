package com.ashleyjain.messmart.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashleyjain.messmart.MessListTabLayout;
import com.ashleyjain.messmart.Object.OrderObject;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;

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
        LinearLayout lunchdetail = (LinearLayout) convertView.findViewById(R.id.lunchde);
        LinearLayout dinnerempty = (LinearLayout) convertView.findViewById(dinnerlinear);
        LinearLayout dinnerdetail = (LinearLayout) convertView.findViewById(R.id.dinnerde);
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


        final OrderObject row = orderList.get(position);
        if(row.getlOIDid()==0){
            lunchdetail.setVisibility(View.INVISIBLE);
            lunchempty.setVisibility(View.VISIBLE);
            TextView booknow = (TextView) convertView.findViewById(R.id.booknowl);
            booknow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("particularday2: "+row.getDatetime());
                    MessListTabLayout fragment = new MessListTabLayout(row.getDatetime(),"l",true);
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("days", StartActivity.days.toString());
                    bundle3.putString("days2", StartActivity.days2.toString());
                    fragment.setArguments(bundle3);
                    StartActivity.get().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_not, fragment, fragment.toString())
                            .addToBackStack(fragment.toString())
                            .commit();
                }
            });
        }
        else{
            lunchempty.setVisibility(View.INVISIBLE);
            lunchdetail.setVisibility(View.VISIBLE);
        }

        if(row.getdOIDid()==0){
            dinnerdetail.setVisibility(View.INVISIBLE);
            dinnerempty.setVisibility(View.VISIBLE);
            TextView booknow = (TextView) convertView.findViewById(R.id.booknowd);
            booknow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("particularday2: " + row.getDatetime());
                    MessListTabLayout fragment = new MessListTabLayout(row.getDatetime(), "d", true);
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("days", StartActivity.days.toString());
                    bundle3.putString("days2", StartActivity.days2.toString());
                    fragment.setArguments(bundle3);
                    StartActivity.get().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_not, fragment, fragment.toString())
                            .addToBackStack(fragment.toString())
                            .commit();
                }
            });
        }
        else{
            dinnerempty.setVisibility(View.INVISIBLE);
            dinnerdetail.setVisibility(View.VISIBLE);
        }

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
