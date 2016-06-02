package com.ashleyjain.messmart.Fragment.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashleyjain.messmart.Object.PastOrderObject;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.adapter.pastOrderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashleyjain on 02/06/16.
 */
public class PastOrderList extends ListFragment {

    String orderList;
    JSONArray datelist;

    private String[] datetime;
    private String[] date;
    private String[] lord;
    private int[] OIDid;
    private String[] Dish;
    private String[] Mess;
    private int[] Dishid,Price;
    private String[] Status;
    private int[] Mid;
    private String[] Booktype;

    private List<PastOrderObject> orderObjectList;
    pastOrderAdapter adapter;

    public PastOrderList(String orderList){
        this.orderList = orderList;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            datelist = new JSONArray(orderList);
            Integer len=0;
            System.out.println("datelistttt: "+datelist.toString());
            for(int i=0;i<datelist.length();i++){
                JSONArray unused = datelist.getJSONArray(i);
                //String datetim = unused.getString(0);
                //System.out.println("datetime: "+datetim);
                JSONArray subsubarr = unused.getJSONArray(1);
                len=len+subsubarr.length();
//                for(int j=0;j<subsubarr.length();j++) {
//                    JSONObject orderitem = subsubarr.getJSONObject(j);
//                    System.out.println("orderitem: "+orderitem.toString());
//
//                }
            }
            datetime = new String[len];
            date = new String[len];
            lord = new String[len];
            OIDid = new int[len];
            Dish = new String[len];
            Mess = new String[len];
            Dishid = new int[len];
            Price = new int[len];
            Status = new String[len];
            Mid = new int[len];
            Booktype = new String[len];

            for(int i=0;i<datelist.length();i++){
                JSONArray unused = datelist.getJSONArray(i);
                String datetim = unused.getString(0);
                System.out.println("datetime: "+datetim);
                JSONArray subsubarr = unused.getJSONArray(1);
                len=len+subsubarr.length();
                for(int j=0;j<subsubarr.length();j++) {
                    JSONObject orderitem = subsubarr.getJSONObject(j);
                    System.out.println("orderitem: "+orderitem.toString());
                    date[i+j]=orderitem.getString("datetimetext");
                    datetime[i+j]=orderitem.getString("time");
                    lord[i+j]=orderitem.getString("lordtext");
                    OIDid[i+j]=orderitem.getInt("id");
                    Dish[i+j]=orderitem.getString("dishtitle");
                    Mess[i+j]=orderitem.getString("mname");
                    Dishid[i+j]=orderitem.getInt("dishid");
                    Price[i+j]=orderitem.getInt("price");
                    Status[i+j]=orderitem.getString("status_text");
                    Mid[i+j]=orderitem.getInt("mid");
                    Booktype[i+j]=orderitem.getString("booktype");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        orderObjectList = new ArrayList<PastOrderObject>();
        for(int i = 0;i<date.length;i++){
            PastOrderObject items = new PastOrderObject(datetime[i],date[i],lord[i], OIDid[i],Dish[i],Mess[i],Dishid[i],Price[i],Status[i],Mid[i],Booktype[i]);
            orderObjectList.add(items);
        }
        System.out.println(orderObjectList.size());
        adapter = new pastOrderAdapter(getActivity(), orderObjectList);
        setListAdapter(adapter);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }
}
