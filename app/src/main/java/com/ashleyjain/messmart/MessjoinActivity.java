package com.ashleyjain.messmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MessjoinActivity extends AppCompatActivity {
    EditText name;
    EditText mobile;
    EditText emailid;
    EditText address;
    EditText password;
    EditText editTextelse;
    Button buttonSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messjoin);

        name = (EditText) findViewById(R.id.EditTextName);
        mobile = (EditText) findViewById(R.id.EditTextMobile);
        emailid = (EditText) findViewById(R.id.EditTextEmailid);
        address = (EditText) findViewById(R.id.EditTextAddress);
        password = (EditText) findViewById(R.id.EditTextPassword);
        editTextelse = (EditText) findViewById(R.id.EditTextElse);
        buttonSubmit = (Button) findViewById(R.id.ButtonSubmit);


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
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
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
                                                            Toast.makeText(MessjoinActivity.this, "You have successfully registered with us.", Toast.LENGTH_LONG).show();
                                                        }
                                                        else{
                                                            JSONObject jsonResponse = new JSONObject(response);
                                                            JSONObject data = jsonResponse.getJSONObject("data");
                                                            JSONObject ec = data.getJSONObject("ec");
                                                            String message = ec.getString(ec_val_str);
                                                            Toast.makeText(MessjoinActivity.this, message, Toast.LENGTH_LONG).show();
                                                        }

                                                    } catch (JSONException e) {
                                                        Toast.makeText(MessjoinActivity.this,"Error :"+e.getMessage(),Toast.LENGTH_LONG).show();

                                                    }


                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(MessjoinActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                        @Override
                                        public Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("action", "getinit");

                                            return params;
                                        }
                                    };

                                    Volley.newRequestQueue(MessjoinActivity.this).add(postRequest);


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MessjoinActivity.this, error.toString(), Toast.LENGTH_LONG).show();
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

                    Volley.newRequestQueue(MessjoinActivity.this).add(postRequest);

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
