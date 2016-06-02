package com.ashleyjain.messmart;

        import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.function.StringRequestCookies;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserprofileActivity extends Fragment {
    ImageView userlogo;
    static int uid;
    static TextView inputUsername1,inputUserwallet1,inputUseraddress1;
    String name,address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_userprofile, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_userprofile);
        userlogo = (ImageView)view.findViewById(R.id.profImg);
        inputUsername1 = (TextView)view.findViewById(R.id.userinputname1);
        inputUseraddress1 = (TextView)view.findViewById(R.id.userinputaddress1);
        inputUserwallet1 = (TextView)view.findViewById(R.id.userinputwallet1);
        getIdUser();
        setInfo();

        TextView showDialogedit = (TextView) view.findViewById(R.id.showdialogedit);
        showDialogedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View view = (LayoutInflater.from(getActivity())).inflate(R.layout.user_input, null);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                alertBuilder.setView(view);
                final EditText userInputname = (EditText) view.findViewById(R.id.userinputname);
                final EditText userInputaddress = (EditText) view.findViewById(R.id.userinputaddress);
                userInputname.setText(name);
                userInputaddress.setText(address);

                alertBuilder.setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //userinputtext.setText(userInput.getText());
                                String url = "http://192.168.0.106/mess/index.php/ajaxactions";
                                StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

                                            }
                                        },
                                        new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                                                //dialog.dismiss();
                                            }
                                        }

                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Log.d("debug", "posting param");
                                        Map<String, String> params = new HashMap<String, String>();

                                        // the POST parameters:
                                        params.put("action", "saveprofile");
                                        params.put("name", userInputname.getText().toString());
                                        params.put("address",userInputaddress.getText().toString());
                                        System.out.println(params);
                                        return params;
                                    }
                                };

                                // add it to the RequestQueue
                                StartActivity.get().getRequestQueue().add(postRequest);


                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });
    }

    private void getIdUser(){
        int id =0;
        String url = "http://192.168.0.106/mess/index.php/ajaxactions";
        StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONObject drawer = data.getJSONObject("drawer");
                            UserprofileActivity.uid = drawer.getInt("loginid");
                            Toast.makeText(getActivity(),UserprofileActivity.uid +"",Toast.LENGTH_LONG).show();

                        }
                        catch(JSONException je){
                            Toast.makeText(getActivity(),je.toString(),Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        //dialog.dismiss();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Log.d("debug", "posting param");
                Map<String, String> params = new HashMap<String, String>();

                // the POST parameters:
                params.put("action", "getinit");
                System.out.println(params);
                return params;
            }
        };

        // add it to the RequestQueue
        StartActivity.get().getRequestQueue().add(postRequest);

    }


    private void setInfo(){
        String url = "http://192.168.0.106/mess/index.php/ajaxactions";
        StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONObject uinfo = data.getJSONObject("uinfo");
                            name = uinfo.getString("name");
                            address = uinfo.getString("address");
                            inputUsername1.setText(name);
                            inputUseraddress1.setText(address);
                            inputUserwallet1.setText(uinfo.getString("wallet")+"/-");
                            Picasso.with(getActivity()).load("http://192.168.0.106/mess/" + uinfo.getString("profilepic")).into(userlogo);

                        }
                        catch(JSONException je){
                            Toast.makeText(getActivity(),je.toString(),Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        //dialog.dismiss();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Log.d("debug", "posting param");
                Map<String, String> params = new HashMap<String, String>();

                // the POST parameters:
                params.put("action", "profile");
                params.put("uid", UserprofileActivity.uid+"");
                System.out.println(params);
                return params;
            }
        };

        // add it to the RequestQueue
        StartActivity.get().getRequestQueue().add(postRequest);

    }
}