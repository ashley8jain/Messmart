package com.ashleyjain.messmart.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.function.StringRequestCookies;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class MessjoinActivity extends android.support.v4.app.Fragment {
    EditText name;
    EditText mobile;
    EditText emailid;
    EditText address;
    EditText password;
    EditText editTextelse;
    Button buttonSubmit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_messjoin, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = (EditText) view.findViewById(R.id.EditTextName);
        mobile = (EditText) view.findViewById(R.id.EditTextMobile);
        emailid = (EditText) view.findViewById(R.id.EditTextEmailid);
        address = (EditText) view.findViewById(R.id.EditTextAddress);
        password = (EditText) view.findViewById(R.id.EditTextPassword);
        editTextelse = (EditText) view.findViewById(R.id.EditTextElse);
        buttonSubmit = (Button) view.findViewById(R.id.ButtonSubmit);


        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                name.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mobile.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        emailid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                emailid.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                address.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                password.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(name.getText().length() == 0
                        || mobile.getText().length() == 0
                        || emailid.getText().length() == 0
                        || address.getText().length() == 0
                        || password.getText().length() == 0
                        || !mobile.getText().toString().matches("\\d{10}")
                        || !emailid.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$"))) {

                    final String url = "http://192.168.0.102/mess/index.php/ajaxactions";
                    StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String ec_val="";
                                    int colon_occ=0;
                                    for(int i=0;i<response.length();i++){
                                        if(response.charAt(i)==':'){
                                            colon_occ++;
                                            if(colon_occ==2){
                                                ec_val=response.substring(i + 2, response.length() - 1);
                                            }
                                        }
                                    }
                                    final String ec_val_str = ec_val;
                                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {

                                                    try {
                                                        if(ec_val_str.equals("1")){
                                                            AlertDialog.Builder alertbuilder = new AlertDialog.Builder(getActivity());
                                                            alertbuilder.setTitle("Submitted Succesfully");
                                                            alertbuilder.setMessage("Thanks for submitting forms,we will get back to you within one business day!");
                                                            alertbuilder.setCancelable(true);
                                                            alertbuilder.setPositiveButton("OK", null);
                                                            AlertDialog alertDialog = alertbuilder.create();
                                                            alertDialog.show();
                                                        }
                                                        else{
                                                            Toast.makeText(getActivity(),StartActivity.errorcode.getString(ec_val_str), Toast.LENGTH_LONG).show();
                                                        }

                                                    } catch (JSONException e) {
                                                        Toast.makeText(getActivity(),"Error :"+e.getMessage(),Toast.LENGTH_LONG).show();

                                                    }


                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                        @Override
                                        public Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("action", "getinit");

                                            return params;
                                        }
                                    };

                                    // add it to the RequestQueue
                                    StartActivity.get().getRequestQueue().add(postRequest);


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("action", "messjoinus");
                            params.put("lat", "0");
                            params.put("lng", "0");
                            params.put("name", name.getText().toString());
                            params.put("mobile", mobile.getText().toString());
                            params.put("email", emailid.getText().toString());
                            params.put("address", address.getText().toString());
                            params.put("password", password.getText().toString());
                            params.put("info", editTextelse.getText().toString());
                            return params;
                        }
                    };

                    // add it to the RequestQueue
                    StartActivity.get().getRequestQueue().add(postRequest);

                } else {
                    if (name.getText().length() == 0) {
                        name.setError("This field can't be empty!");
                    }
                    if (mobile.getText().length() == 0) {
                        mobile.setError("This field can't be empty!");
                    }
                    if (!mobile.getText().toString().matches("\\d{10}")) {
                        mobile.setError("Invalid mobile number!");
                    }
                    if (emailid.getText().length() == 0) {
                        emailid.setError("This field can't be empty!");
                    }
                    if(!emailid.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$")){
                        emailid.setError("Invalid email-id!");
                    }
                    if (address.getText().length() == 0) {
                        address.setError("This field can't be empty!");
                    }
                    if (password.getText().length() == 0) {
                        password.setError("This field can't be empty!");
                    }
                }

            }
        });
    }

}
