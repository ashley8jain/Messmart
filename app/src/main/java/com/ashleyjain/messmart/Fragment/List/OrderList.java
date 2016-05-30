package com.ashleyjain.messmart.Fragment.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashleyjain.messmart.Object.OrderObject;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.adapter.orderObjectAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderList extends ListFragment {

    String orderList;
    JSONArray datelist;

    private String[] datetime,date;
    private int[] lOIDid;
    private int[] dOIDid;
    private String[] lDish;
    private String[] dDish;;
    private String[] lMess;
    private String[] dMess;
    private int[] lPrice;
    private int[] dPrice;
    private String[] lStatus;
    private String[] dStatus;

    private List<OrderObject> orderObjectList;
    orderObjectAdapter adapter;

    public OrderList(String orderList){
        this.orderList = orderList;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            datelist = new JSONArray(orderList);
            Integer len = datelist.length();
            datetime = new String[len];
            date=new String[len];
            lOIDid=new int[len];
            dOIDid=new int[len];
            lDish=new String[len];
            dDish=new String[len];
            lMess=new String[len];
            dMess=new String[len];
            lPrice=new int[len];
            dPrice=new int[len];
            lStatus=new String[len];
            dStatus=new String[len];
            for(int i=0;i<datelist.length();i++){
                JSONArray unused = datelist.getJSONArray(i);
                String datetim = unused.getString(0);
                datetime[i] = datetim;
                System.out.println("orderdatetime: "+datetime[i]);
                JSONArray subsubarr = unused.getJSONArray(1);
                for(int j=0;j<subsubarr.length();j++){
                    JSONObject orderitem = subsubarr.getJSONObject(j);

                    if(j==0){
                        date[i]=orderitem.getString("datetimetext");
                        try{
                            orderitem.getInt("dishid");
                            lOIDid[i]=orderitem.getInt("id");
                            lDish[i]=orderitem.getString("dishtitle");
                            lMess[i]=orderitem.getString("mname");
                            lPrice[i]=orderitem.getInt("price");
                            lStatus[i]=orderitem.getString("status_text");
                        }
                        catch (JSONException e) {
                            lOIDid[i]=0;
                            lDish[i]="-";
                            lMess[i]="-";
                            lPrice[i]=0;
                            lStatus[i]="-";
                        }
                    }
                    else{
                        try {
                            dOIDid[i]=orderitem.getInt("id");
                            dDish[i]=orderitem.getString("dishtitle");
                            dMess[i]=orderitem.getString("mname");
                            dPrice[i]=orderitem.getInt("price");
                            dStatus[i]=orderitem.getString("status_text");
                        }
                        catch (JSONException e) {
                            //System.out.println(orderitem.getString("dishid"));
                            dOIDid[i]=0;
                            dDish[i]="-";
                            dMess[i]="-";
                            dPrice[i]=0;
                            dStatus[i]="-";
                        }
                    }
                }
                System.out.println("orderarray: "+i+" "+subsubarr.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //        messId = new int[2];
//        messTitle = new String[2];
//        messDescription = new String[2];
//        messPic = new String[2];
//        messPrice = new int[2];
//        messIsVeg = new boolean[2];
        //System.out.println("orderarray: "+orderList);



        orderObjectList = new ArrayList<OrderObject>();
        for(int i = 0;i<date.length;i++){
            OrderObject items = new OrderObject(datetime[i],date[i],lOIDid[i],dOIDid[i],lDish[i],dDish[i],lMess[i],dMess[i],lPrice[i],dPrice[i],lStatus[i],dStatus[i]);
            orderObjectList.add(items);
        }
        System.out.println(orderObjectList.size());
        adapter = new orderObjectAdapter(getActivity(), orderObjectList);
        setListAdapter(adapter);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
