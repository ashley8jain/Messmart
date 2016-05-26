package com.ashleyjain.messmart.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.Object.MessObject;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.function.StringRequestCookies;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ashleyjain on 19/05/16.
 */
public class messObjectAdapter extends BaseAdapter {

    Context context;
    List<MessObject> messList;

    public messObjectAdapter(Context context, List<MessObject> messList) {
        this.context = context;
        this.messList = messList;

    }

    @Override
    public int getCount() {
        return messList.size();
    }

    @Override
    public Object getItem(int position) {
        return messList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return messList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.mess_list_item, null);
        }

        TextView messTitle = (TextView) convertView.findViewById(R.id.title);
        TextView messDescription = (TextView) convertView.findViewById(R.id.description);
        TextView Prices = (TextView) convertView.findViewById(R.id.price);
        TextView name = (TextView) convertView.findViewById(R.id.messmakername);
        ImageView messimg = (ImageView) convertView.findViewById(R.id.messimg);
        ImageView vegimg = (ImageView) convertView.findViewById(R.id.imgveg);
        Button book = (Button) convertView.findViewById(R.id.bookbutton);

        final MessObject row = messList.get(position);


        book.setText(row.getBook()==1?"cancel":"book");
        final AlertDialog.Builder alertbuilder = new AlertDialog.Builder(context);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(row.getBook()==0){
                    if(StartActivity.isLogin){

                        alertbuilder.setTitle("Booking");
                        alertbuilder.setMessage("Mess:"+row.getName()+"\n Address:"+row.getAddress()+"\n Timing:"+row.getTiming());
                        alertbuilder.setCancelable(true);
                        alertbuilder.setNegativeButton("Arrive on mess",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id) {

                                final ProgressDialog dialog2 = ProgressDialog.show(context, "", "Booking.....", true);
                                String url = StartActivity.host+"index.php/ajaxactions";

                                StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.d("Response", response);
                                                JSONObject jsonResponse = null;
                                                try {
                                                    jsonResponse = new JSONObject(response);
                                                    Integer ec = jsonResponse.getInt("ec");
                                                    dialog2.dismiss();
                                                    if(ec==-5){
                                                        Toast.makeText(context, "Insufficient arguments", Toast.LENGTH_LONG).show();
                                                    }
                                                    else if(ec==1){
                                                        Toast.makeText(context, "Booked", Toast.LENGTH_LONG).show();
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
                                        params.put("lord",row.getLord());
                                        params.put("dishid",""+row.getDishId());
                                        params.put("mid", row.getId()+"");
                                        params.put("dishid",row.getDishId()+"");
                                        params.put("booktype","m");
                                        params.put("action", "bookmeal");
                                        params.put("datetime",row.getDatetime());
                                        System.out.println(params);
                                        return params;
                                    }
                                };

                                // add it to the RequestQueue
                                StartActivity.get().getRequestQueue().add(postRequest);
                            }
                        });
                        alertbuilder.setPositiveButton("Order Tiffin", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final ProgressDialog dialog2 = ProgressDialog.show(context, "", "Booking.....", true);
                                String url = StartActivity.host+"index.php/ajaxactions";

                                StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.d("Response", response);
                                                JSONObject jsonResponse = null;
                                                try {
                                                    jsonResponse = new JSONObject(response);
                                                    Integer ec = jsonResponse.getInt("ec");
                                                    dialog2.dismiss();
                                                    if(ec==-5){
                                                        Toast.makeText(context, "Insufficient arguments", Toast.LENGTH_LONG).show();
                                                    }
                                                    else if(ec==1){
                                                        Toast.makeText(context, "Booked", Toast.LENGTH_LONG).show();
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
                                        params.put("lord",row.getLord());
                                        params.put("dishid",""+row.getDishId());
                                        params.put("mid", row.getId()+"");
                                        params.put("dishid",row.getDishId()+"");
                                        params.put("booktype","t");
                                        params.put("action", "bookmeal");
                                        params.put("datetime",row.getDatetime());
                                        System.out.println(params);
                                        return params;
                                    }
                                };

                                // add it to the RequestQueue
                                StartActivity.get().getRequestQueue().add(postRequest);
                            }
                        });
                        AlertDialog alertDialog = alertbuilder.create();
                        alertDialog.show();
                    }
                    else{
                        Toast.makeText(context, "Sorry!! You need to login first", Toast.LENGTH_LONG).show();
                    }
                }
                else if(row.getBook()==1){
                    alertbuilder.setTitle("Confirmation");
                    alertbuilder.setMessage("Are you sure?");
                    alertbuilder.setCancelable(true);
                    alertbuilder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            final ProgressDialog dialog2 = ProgressDialog.show(context, "", "Cancelling.....", true);
                            String url = StartActivity.host+"index.php/ajaxactions";

                            StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.d("Response", response);
                                            JSONObject jsonResponse = null;
                                            try {
                                                jsonResponse = new JSONObject(response);
                                                Integer ec = jsonResponse.getInt("ec");
                                                dialog2.dismiss();
                                                if(ec==-5){
                                                    Toast.makeText(context, "Insufficient arguments", Toast.LENGTH_LONG).show();
                                                }
                                                else if(ec==1){
                                                    Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show();
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
                                    params.put("lord",row.getLord());
                                    params.put("dishid",""+row.getDishId());
                                    params.put("datetime",row.getDatetime());
                                    params.put("action", "cancelmeal");
                                    params.put("status","-1");
                                    params.put("uid",StartActivity.loginid);
                                    System.out.println(params);
                                    return params;
                                }
                            };

                            // add it to the RequestQueue
                            StartActivity.get().getRequestQueue().add(postRequest);
                        }
                    });
                    AlertDialog alertDialog = alertbuilder.create();
                    alertDialog.show();
                }


            }
        });



        messTitle.setText(row.getTitle());
        messDescription.setText(row.getDescription());
        name.setText(row.getName());
        Prices.setText("" + row.getPrice() + "/-");
        Picasso.with(context).load(StartActivity.host+row.getPic()).into(messimg);
        vegimg.setImageResource(row.isVeg() ? R.drawable.veg : R.drawable.nonveg);


        return convertView;
    }
}
