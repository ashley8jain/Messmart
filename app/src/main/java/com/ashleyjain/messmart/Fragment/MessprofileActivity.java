package com.ashleyjain.messmart.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.Fragment.List.DishList;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.function.StringRequestCookies;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MessprofileActivity extends Fragment {
    ImageView messlogo;
    TextView mess_name;
    TextView mess_address;
    TextView mess_aboutme;
    TextView mess_lunchtime;
    TextView mess_dinnertime;
    int uid;
    JSONArray menulist;

    public MessprofileActivity(int uid){
        this.uid = uid;
    }


    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("MesSmart");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Mess Profile");
        return inflater.inflate(R.layout.activity_messprofile, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);

        messlogo = (ImageView)view.findViewById(R.id.messProfile);

        mess_name = (TextView)view.findViewById(R.id.mess_name);
        mess_address = (TextView)view.findViewById(R.id.mess_address);
        mess_aboutme = (TextView)view.findViewById(R.id.mess_aboutme);
        mess_lunchtime = (TextView)view.findViewById(R.id.mess_lunchtime);
        mess_dinnertime = (TextView)view.findViewById(R.id.mess_dinnertime);


        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
        String url = StartActivity.host+"index.php/ajaxactions";
        StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        //response JSON from url
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject data = jsonResponse.getJSONObject("data");
                            menulist = data.getJSONArray("menu_list");
//                            DishList dishlist = new DishList();
//                            Bundle bundle2 = new Bundle();
//                            bundle2.putString("menulist", menulist.toString());
//                            dishlist.setArguments(bundle2);
//                            StartActivity.get().getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.fraglist, dishlist, dishlist.toString())
//                                    .commit();
                            JSONObject uinfo = data.getJSONObject("uinfo");
                            String name = uinfo.getString("name");
                            String aboutus = uinfo.getString("aboutus");
                            String address = uinfo.getString("address");
                            String profilepic = StartActivity.host+uinfo.getString("profilepic");
                            JSONArray profile_right_display = data.getJSONArray("profile_ri//ght_display");
                            mess_name.setText(name);
                            mess_address.setText(address);
                            // mess_aboutme.setText(aboutus);
                            Picasso.with(getActivity()).load(profilepic).into(messlogo);
                            String lunchtime = profile_right_display.getJSONArray(1).getString(1);
                            String dinnertime = profile_right_display.getJSONArray(2).getString(1);
                            mess_lunchtime.setText(lunchtime);
                            mess_dinnertime.setText(dinnertime);
                            dialog.dismiss();
                        } catch (JSONException e) {
                            //Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

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
                params.put("action", "profile");
                params.put("uid", uid+"");
                System.out.println(params);
                return params;
            }
        };

        // add it to the RequestQueue
        StartActivity.get().getRequestQueue().add(postRequest);

        TextView dishlist = (TextView) view.findViewById(R.id.dishlist);
        dishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DishList dishlist = new DishList();
                Bundle bundle2 = new Bundle();
                bundle2.putString("menulist", menulist.toString());
                dishlist.setArguments(bundle2);
                StartActivity.get().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_not, dishlist, dishlist.toString())
                        .addToBackStack(dishlist.toString())
                        .commit();
            }
        });


    }
}
