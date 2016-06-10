package com.ashleyjain.messmart.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.Fragment.OrderFragment;
import com.ashleyjain.messmart.Fragment.MessListTabLayout;
import com.ashleyjain.messmart.Fragment.MessprofileActivity;
import com.ashleyjain.messmart.Object.OrderObject;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.function.StringRequestCookies;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Button lCancel = (Button) convertView.findViewById(R.id.lcancel);
        Button dCancel = (Button) convertView.findViewById(R.id.dcancel);


        final OrderObject row = orderList.get(position);
        if(row.getlOIDid()==0){
            lunchdetail.setVisibility(View.GONE);
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
            lunchempty.setVisibility(View.GONE);
            lunchdetail.setVisibility(View.VISIBLE);
        }

        if(row.getdOIDid()==0){
            dinnerdetail.setVisibility(View.GONE);
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
            dinnerempty.setVisibility(View.GONE);
            dinnerdetail.setVisibility(View.VISIBLE);
        }

        dateTitle.setText(row.getDate());
        loid.setText(""+row.getlOIDid());
        doid.setText(""+row.getdOIDid());
        ldish.setText(row.getlDish());
        ddish.setText(row.getdDish());
        lmess.setText(Html.fromHtml("<font color=#039be5>" + row.getlMess() + "</font>" + "(" + (row.getlBooktype().equals("m") ? "onMess" : "Tiffin") + ")"));
        lmess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessprofileActivity messprofileActivity = new MessprofileActivity(row.getlMid());
                StartActivity.get().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_not, messprofileActivity, messprofileActivity.toString())
                        .addToBackStack(messprofileActivity.toString())
                        .commit();
            }
        });
        dmess.setText(Html.fromHtml("<font color=#039be5>" + row.getdMess() + "</font>" + "(" + (row.getdBooktype().equals("m") ? "onMess" : "Tiffin") + ")"));
        dmess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessprofileActivity messprofileActivity = new MessprofileActivity(row.getdMid());
                StartActivity.get().getSupportFragmentManager().popBackStack();
                StartActivity.get().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_not, messprofileActivity, messprofileActivity.toString())
                        .addToBackStack(messprofileActivity.toString())
                        .commit();
            }
        });
        lCancel.setOnClickListener(new View.OnClickListener() {
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
                                        Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show();
                                        StartActivity.get().getSupportFragmentManager().popBackStack();
                                        OrderFragment ofragment = new OrderFragment();
                                        StartActivity.get().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment_not, ofragment, ofragment.toString())
                                                .addToBackStack(ofragment.toString())
                                                .commit();
                                    } else {
                                        Toast.makeText(context, StartActivity.errorcode.getString("" + ec), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                                dialog2.dismiss();
                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Log.d("debug", "posting param");
                        Map<String, String> params = new HashMap<String, String>();

                        // the POST parameters:
                        params.put("lord","l");
                        params.put("dishid", "" + row.getlDishid());
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
        dCancel.setOnClickListener(new View.OnClickListener() {
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
                                        Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show();
                                        StartActivity.get().getSupportFragmentManager().popBackStack();
                                        OrderFragment ofragment = new OrderFragment();
                                        StartActivity.get().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment_not, ofragment, ofragment.toString())
                                                .addToBackStack(ofragment.toString())
                                                .commit();
                                    } else {
                                        Toast.makeText(context, StartActivity.errorcode.getString("" + ec), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                                dialog2.dismiss();
                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Log.d("debug", "posting param");
                        Map<String, String> params = new HashMap<String, String>();

                        // the POST parameters:
                        params.put("lord","d");
                        params.put("dishid", "" + row.getdDishid());
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
        lprice.setText(row.getlPrice()+"/-");
        dprice.setText(row.getdPrice()+"/-");
        lstatus.setText(row.getlStatus());
        dstatus.setText(row.getdStatus());

        return convertView;
    }
}
