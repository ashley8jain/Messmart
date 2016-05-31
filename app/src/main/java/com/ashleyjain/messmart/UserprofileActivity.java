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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.function.StringRequestCookies;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UserprofileActivity extends Fragment {
    ImageView userlogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_userprofile, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_userprofile);
        userlogo = (ImageView)view.findViewById(R.id.userlogo);
        Picasso.with(getActivity()).load("http://i.imgur.com/DvpvklR.png").into(userlogo);

        Button showDialogedit = (Button) view.findViewById(R.id.showdialogedit);
        showDialogedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View view = (LayoutInflater.from(getActivity())).inflate(R.layout.user_input, null);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                alertBuilder.setView(view);
                final EditText userInputname = (EditText) view.findViewById(R.id.userinputname);
                final EditText userInputaddress = (EditText) view.findViewById(R.id.userinputaddress);

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
}
