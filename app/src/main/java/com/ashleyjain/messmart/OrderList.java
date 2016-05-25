package com.ashleyjain.messmart;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class OrderList extends ListFragment {

    private String[] date = {"Order for Wed,May24","Order for Wed,May25","Order for Wed,May26","Order for Wed,May27","Order for Wed,May28","Order for Wed,May29","Order for Wed,May30"};
    private int[] lOIDid = {12,32,12,32,543,643,543};
    private int[] dOIDid = {1,2,3,4,5,6,7};
    private String[] lDish = {"ewd","fw","fwef","fwefdw","fewfw","fwesw","fewse"};
    private String[] dDish = {"ewd","fw","fwef","fwefdw","fewfw","fwesw","fewse"};;
    private String[] lMess = {"fres","Ky","kuyfr","ewq","cdfe","gre","vre"};
    private String[] dMess= {"fres","Ky","kuyfr","ewq","cdfe","gre","vre"};
    private int[] lPrice = {32,43,65,234,76,45,23};
    private int[] dPrice = {234,53,34,543,345,43,34};
    private String[] lStatus = {"Cancel","Cancel","Cancel","Cancel","Cancel","Cancel","Cancel"};
    private String[] dStatus = {"Cancel","Failed","Cancel","Cancel","Cancel","Cancel","Cancel"};

    private List<OrderObject> orderObjectList;
    orderObjectAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        messId = new int[2];
//        messTitle = new String[2];
//        messDescription = new String[2];
//        messPic = new String[2];
//        messPrice = new int[2];
//        messIsVeg = new boolean[2];

        orderObjectList = new ArrayList<OrderObject>();
        for(int i = 0;i<date.length;i++){
            OrderObject items = new OrderObject(date[i],lOIDid[i],dOIDid[i],lDish[i],dDish[i],lMess[i],dMess[i],lPrice[i],dPrice[i],lStatus[i],dStatus[i]);
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
