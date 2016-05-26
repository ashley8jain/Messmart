package com.ashleyjain.messmart.Fragment.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.Object.MessObject;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.adapter.messObjectAdapter;
import com.ashleyjain.messmart.function.StringRequestCookies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessList extends ListFragment {

    String lord,pd;

//    private int[] messId = {12,43,32,12,32,123,432};
//    private String[] messTitle = {"Puri with gobhi ki Sabji","cow-dunk with chana masala","rajma chawal","chole bhuri","dal roti","chicken egg","icecream scoop"};
//    private String[] messDescription = {"We also make puri sabji with alu ke pranthe.","Puri Sabji. It is very sweet. We also make puri sabji with alu ke prant","defcesrcfresf","fercfercfedcf","frefcerwcfesfced","frecfserfecds","fcewcesd"};
//    private int[] messPrice = {321,987,321,432,543,234,543};
//    private boolean[] messIsVeg = {true,false,true,true,true,false,true};
//    private String[] messPic = {"photo/food1.jpg","photo/food2.jpg","photo/food3.jpg","photo/food4.jpg","photo/food5.jpg","photo/food6.jpg","photo/food7.jpg"};
//    private String[] messName = {"vinay","akash","rohit","vaibhav","deepika","ads","fewfs"};

    private int[] messId;
    private String[] messTitle;
    private String[] messDescription;
    private int[] messPrice;
    private boolean[] messIsVeg;
    private String[] messPic;
    private String[] messName;

    private List<MessObject> messObjectList;
    messObjectAdapter adapter;

    public MessList(String lord,String pd){
        this.lord = lord;
        this.pd = pd;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading.....", true);
        String url = StartActivity.host+"index.php/ajaxactions";

        StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        //response JSON from url
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject dataobject = jsonResponse.getJSONObject("data");
                            JSONArray foodarray = dataobject.getJSONArray("foodlist");
                            System.out.println(foodarray);
                            Integer len = foodarray.length();
                            messId = new int[len];
                            messTitle = new String[len];
                            messDescription = new String[len];
                            messPrice = new int[len];
                            messIsVeg = new boolean[len];
                            messPic = new String[len];
                            messName = new String[len];
                            for(int i=0;i<len;i++){
                                JSONObject fooditem = foodarray.getJSONObject(i);
                                messId[i] = fooditem.getInt("id");
                                messTitle[i] = fooditem.getString("title");
                                messDescription[i] = fooditem.getString("descp");
                                messPrice[i] = fooditem.getInt("price");
                                messIsVeg[i] = fooditem.getString("isveg")=="n"?false:true;
                                messPic[i] = fooditem.getString("pic");
                                messName[i] = fooditem.getString("name");
                            }

                            dialog.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(),e.toString(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                        messObjectList = new ArrayList<MessObject>();
                        for(int i = 0;i<messId.length;i++){
                            MessObject items = new MessObject(messId[i],messTitle[i],messDescription[i],messPrice[i],messIsVeg[i],messPic[i],messName[i]);
                            messObjectList.add(items);
                        }
                        System.out.println(messObjectList.size());
                        adapter = new messObjectAdapter(getActivity(), messObjectList);
                        setListAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Log.d("debug", "posting param");
                Map<String, String> params = new HashMap<String, String>();

                // the POST parameters:
                params.put("datetime",pd);
                params.put("lord", lord);
                params.put("action", "menulist");
                System.out.println(params);
                return params;
            }
        };

        // add it to the RequestQueue
        StartActivity.get().getRequestQueue().add(postRequest);

//        messId = new int[2];
//        messTitle = new String[2];
//        messDescription = new String[2];
//        messPic = new String[2];
//        messPrice = new int[2];
//        messIsVeg = new boolean[2];

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mess_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
