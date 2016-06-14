package com.ashleyjain.messmart.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.Fragment.List.DishList;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.function.RoundedImageView;
import com.ashleyjain.messmart.function.StringRequestCookies;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MessprofileActivity extends Fragment {
    RoundedImageView messlogo;
    TextView mess_name;
    TextView mess_aboutme;
    int uid;
    JSONArray menulist;

    @SuppressLint("ValidFragment")
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

        messlogo = (RoundedImageView)view.findViewById(R.id.messProfile);

        mess_name = (TextView)view.findViewById(R.id.mess_name);
        mess_aboutme = (TextView)view.findViewById(R.id.mess_aboutme);
        final LinearLayout ll = (LinearLayout) view.findViewById(R.id.right_display);

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
        StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, StartActivity.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        //response JSON from url
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject data = jsonResponse.getJSONObject("data");
                            menulist = data.getJSONArray("menu_list");
                            DishList dishlist = new DishList();
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("menulist", menulist.toString());
                            dishlist.setArguments(bundle2);
                            StartActivity.get().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fraglist, dishlist, dishlist.toString())
                                    .commit();
                            JSONObject uinfo = data.getJSONObject("uinfo");
                            String name = uinfo.getString("name");
                            String aboutus = uinfo.getString("aboutus");
                            String profilepic = StartActivity.host+uinfo.getString("profilepic");
                            JSONArray profile_right_display = data.getJSONArray("profile_right_display");
                            LinearLayoutCompat.LayoutParams lparams = new LinearLayoutCompat.LayoutParams(
                                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                            LinearLayoutCompat.LayoutParams lparams2 = new LinearLayoutCompat.LayoutParams(
                                    LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

                            for(int i=0;i<profile_right_display.length();i++){
                                TextView tv=new TextView(getActivity());
                                tv.setLayoutParams(lparams);
                                tv.setText(profile_right_display.getJSONArray(i).getString(0));
                                tv.setTypeface(Typeface.DEFAULT_BOLD);
                                tv.setTextSize(16);
                                tv.setPadding(0,0,0,16);
                                ll.addView(tv);

                                TextView tv2=new TextView(getActivity());
                                tv2.setLayoutParams(lparams);
                                tv2.setText(profile_right_display.getJSONArray(i).getString(1));
                                tv2.setPadding(0,0,0,5);
                                ll.addView(tv2);

                                if(i!=profile_right_display.length()-1){
                                    View view = new View(getActivity());
                                    view.setMinimumHeight(2);
                                    view.setLayoutParams(lparams2);
                                    view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                                    ll.addView(view);
                                }
                            }

                            mess_name.setText(name);
                            mess_aboutme.setText(aboutus);
                            Picasso.with(getActivity()).load(profilepic).into(messlogo);

                            dialog.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
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

    }
}
