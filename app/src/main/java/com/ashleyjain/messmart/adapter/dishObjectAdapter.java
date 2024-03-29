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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.Fragment.MessprofileActivity;
import com.ashleyjain.messmart.Object.DishObject;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.function.RoundedImageView;
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

public class dishObjectAdapter extends BaseAdapter {

    Context context;
    List<DishObject> messList;
    public static int uid;

    public dishObjectAdapter(Context context, List<DishObject> messList) {
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
            convertView = mInflater.inflate(R.layout.dish_list_item, null);
        }

        TextView messTitle = (TextView) convertView.findViewById(R.id.title);
        TextView messDescription = (TextView) convertView.findViewById(R.id.description);
        TextView Prices = (TextView) convertView.findViewById(R.id.price);
        TextView name = (TextView) convertView.findViewById(R.id.messmakername);
        RoundedImageView messLogo = (RoundedImageView) convertView.findViewById(R.id.messmakerlogo);
        ImageView messimg = (ImageView) convertView.findViewById(R.id.messimg);
        ImageView vegimg = (ImageView) convertView.findViewById(R.id.imgveg);
        final Button book = (Button) convertView.findViewById(R.id.bookbutton);

        final DishObject row = messList.get(position);

        System.out.println(row.getBook()+"hereeee");
        book.setText(row.getBook() == 1 ? "Cancel" : "Book");
        final AlertDialog.Builder alertbuilder = new AlertDialog.Builder(context);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (row.getBook() == 0) {

                    alertbuilder.setTitle("Booking");
                    alertbuilder.setMessage("Mess : " + row.getName() + "\nAddress : " + row.getAddress() + "\nTiming : " + row.getTiming());
                    alertbuilder.setCancelable(true);
                    alertbuilder.setNegativeButton("Arrive on mess", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            final ProgressDialog dialog2 = ProgressDialog.show(context, "", "Booking.....", true);

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
                                                    Toast.makeText(context, "Booked", Toast.LENGTH_SHORT).show();
                                                    StartActivity.get().getSupportFragmentManager().popBackStack();
                                                    MessprofileActivity messprofileActivity = new MessprofileActivity(row.getId());
                                                    StartActivity.get().getSupportFragmentManager().beginTransaction()
                                                            .replace(R.id.fragment_not, messprofileActivity, messprofileActivity.toString())
                                                            .addToBackStack(messprofileActivity.toString())
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
                                    params.put("lord", row.getLord());
                                    params.put("dishid", "" + row.getDishId());
                                    params.put("mid", row.getId() + "");
                                    params.put("dishid", row.getDishId() + "");
                                    params.put("booktype", "m");
                                    params.put("action", "bookmeal");
                                    params.put("datetime", row.getDatetime());
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
                                                    Toast.makeText(context, "Booked", Toast.LENGTH_SHORT).show();
                                                    book.setText("booked");
                                                    StartActivity.get().getSupportFragmentManager().popBackStack();
                                                    MessprofileActivity messprofileActivity = new MessprofileActivity(row.getId());
                                                    StartActivity.get().getSupportFragmentManager().beginTransaction()
                                                            .replace(R.id.fragment_not, messprofileActivity, messprofileActivity.toString())
                                                            .addToBackStack(messprofileActivity.toString())
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
                                    params.put("lord", row.getLord());
                                    params.put("dishid", "" + row.getDishId());
                                    params.put("mid", row.getId() + "");
                                    params.put("dishid", row.getDishId() + "");
                                    params.put("booktype", "t");
                                    params.put("action", "bookmeal");
                                    params.put("datetime", row.getDatetime());
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
                } else if (row.getBook() == 1) {
                    alertbuilder.setTitle("Confirmation");
                    alertbuilder.setMessage("Are you sure?");
                    alertbuilder.setCancelable(true);
                    alertbuilder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
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
                                                    book.setText("Cancelled");
                                                    StartActivity.get().getSupportFragmentManager().popBackStack();
                                                    MessprofileActivity messprofileActivity = new MessprofileActivity(row.getId());
                                                    StartActivity.get().getSupportFragmentManager().beginTransaction()
                                                            .replace(R.id.fragment_not, messprofileActivity, messprofileActivity.toString())
                                                            .addToBackStack(messprofileActivity.toString())
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
                                    params.put("lord", row.getLord());
                                    params.put("dishid", "" + row.getDishId());
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
                    AlertDialog alertDialog = alertbuilder.create();
                    alertDialog.show();
                }


            }
        });



        messTitle.setText(row.getTitle());
        name.setText(row.getName());
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessprofileActivity messprofileActivity = new MessprofileActivity(row.getId());
                StartActivity.get().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_not, messprofileActivity, messprofileActivity.toString())
                        .addToBackStack(messprofileActivity.toString())
                        .commit();
            }
        });

        messDescription.setText(row.getDescription());
        Prices.setText("" + row.getPrice() + "/-");
        Picasso.with(context).load(StartActivity.host+row.getPic()).into(messimg);
        Picasso.with(context).load(StartActivity.host+row.getMessprofpic()).into(messLogo);
        vegimg.setImageResource(row.isVeg() ? R.drawable.veg : R.drawable.nonveg);
        TextView when = (TextView) convertView.findViewById(R.id.when);
        when.setText("*Serving as "+(row.getLord().equals("l")?"Lunch":"Dinner")+" on "+row.getDatetext());

        //sold out.....
        LinearLayout soldout = (LinearLayout) convertView.findViewById(R.id.soldout);
        if(row.getM_avai()>0 || row.getT_avai()>0){
            soldout.setVisibility(View.INVISIBLE);
            book.setVisibility(View.VISIBLE);
        }
        else{
            soldout.setVisibility(View.VISIBLE);
            if(row.getBook()==0)
                book.setVisibility(View.INVISIBLE);
            else
                book.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
