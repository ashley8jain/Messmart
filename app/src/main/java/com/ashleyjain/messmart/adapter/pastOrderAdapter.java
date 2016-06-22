package com.ashleyjain.messmart.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.Fragment.MessprofileActivity;
import com.ashleyjain.messmart.Fragment.OrderFragment;
import com.ashleyjain.messmart.Object.PastOrderObject;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.function.StringRequestCookies;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        TextView fdate = (TextView) convertView.findViewById(R.id.fulldate);
        TextView oid = (TextView) convertView.findViewById(R.id.oid);
        TextView lord = (TextView) convertView.findViewById(R.id.lord);
        TextView dish = (TextView) convertView.findViewById(R.id.dish);
        TextView mess = (TextView) convertView.findViewById(R.id.mess);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView status = (TextView) convertView.findViewById(R.id.status);
        Button cancel = (Button) convertView.findViewById(R.id.cancel);

        final PastOrderObject row = orderList.get(position);
        fdate.setText(row.getDate());
        oid.setText(""+row.getOIDid());
        lord.setText(row.getLord());
        dish.setText(row.getDish());
        mess.setText(Html.fromHtml("<font color=#039be5>" + row.getMess() + "</font>" + "(" + (row.getBooktype().equals("m") ? "onMess" : "Tiffin") + ")"));
        mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessprofileActivity messprofileActivity = new MessprofileActivity(row.getMid());
                StartActivity.get().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_not, messprofileActivity, messprofileActivity.toString())
                        .addToBackStack(messprofileActivity.toString())
                        .commit();
            }
        });
        price.setText(row.getPrice() + "/-");
        status.setText(row.getStatus());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog2 = ProgressDialog.show(context, "", "Cancelling.....", true);
                StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, StartActivity.url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                                JSONObject jsonResponse = null;
                                try {
                                    jsonResponse = new JSONObject(response);
                                    Integer ec = jsonResponse.getInt("ec");
                                    dialog2.dismiss();
                                    if (ec == 1) {
                                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                                        StartActivity.get().getSupportFragmentManager().popBackStack();
                                        OrderFragment ofragment = new OrderFragment();
                                        StartActivity.get().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment_not, ofragment, ofragment.toString())
                                                .addToBackStack(ofragment.toString())
                                                .commit();
                                    } else {
                                        Toast.makeText(context, StartActivity.errorcode.getString("" + ec), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                dialog2.dismiss();
                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Log.d("debug", "posting param");
                        Map<String, String> params = new HashMap<String, String>();

                        // the POST parameters:
                        params.put("lord",row.getLord().equals("Lunch")?"l":"d");
                        params.put("dishid", "" + row.getDishid());
                        params.put("datetime", row.getDatetime());
                        params.put("action", "cancelmeal");
                        params.put("status", "-1");
                        params.put("uid", StartActivity.loginid);
                        System.out.println(params);
                        return params;
                    }
                };

                // add it to the RequestQueue
                StartActivity.get().getRequestQueue().add(postRequest);
            }
        });


        return convertView;
    }
}
