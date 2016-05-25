package com.ashleyjain.messmart.Fragment.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashleyjain.messmart.Object.MessObject;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.adapter.messObjectAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessList extends ListFragment {

    private int[] messId = {12,43,32,12,32,123,432};
    private String[] messTitle = {"Puri with gobhi ki Sabji","cow-dunk with chana masala","rajma chawal","chole bhuri","dal roti","chicken egg","icecream scoop"};
    private String[] messDescription = {"We also make puri sabji with alu ke pranthe.","Puri Sabji. It is very sweet. We also make puri sabji with alu ke prant","defcesrcfresf","fercfercfedcf","frefcerwcfesfced","frecfserfecds","fcewcesd"};
    private int[] messPrice = {321,987,321,432,543,234,543};
    private boolean[] messIsVeg = {true,false,true,true,true,false,true};
    private String[] messPic = {"photo/food1.jpg","photo/food2.jpg","photo/food3.jpg","photo/food4.jpg","photo/food5.jpg","photo/food6.jpg","photo/food7.jpg"};
    private String[] messName = {"vinay","akash","rohit","vaibhav","deepika","ads","fewfs"};

    private List<MessObject> messObjectList;
    messObjectAdapter adapter;

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

        messObjectList = new ArrayList<MessObject>();
        for(int i = 0;i<messId.length;i++){
            MessObject items = new MessObject(messId[i],messTitle[i],messDescription[i],messPrice[i],messIsVeg[i],messPic[i],messName[i]);
            messObjectList.add(items);
        }
        System.out.println(messObjectList.size());
        adapter = new messObjectAdapter(getActivity(), messObjectList);
        setListAdapter(adapter);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mess_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
