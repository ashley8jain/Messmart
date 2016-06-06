package com.ashleyjain.messmart.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.function.KeyboardDown;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends android.support.v4.app.Fragment {

    Button forgot_password_button;
    EditText forgot_mob;
    TextView login_link;
    int error_code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RelativeLayout rlayout = (RelativeLayout) view.findViewById(R.id.activity_forgot_password);
        rlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                KeyboardDown.keyboardDown();
            }

        });

        forgot_mob = (EditText)view.findViewById(R.id.forgot_mob);
        forgot_password_button = (Button)view.findViewById(R.id.forgot_password_button);
        login_link = (TextView)view.findViewById(R.id.login_link);

        forgot_mob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    KeyboardDown.keyboardDown();
                }

            }
        });

        forgot_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
                KeyboardDown.keyboardDown();
            }

        });

        login_link.setText(Html.fromHtml("<font color=#039be5> Login </font><br><br>"));
        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity loginActivity = new LoginActivity();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_not, loginActivity, loginActivity.toString())
                        .addToBackStack(loginActivity.toString())
                        .commit();
            }
        });


    }

    private void forgotPassword(){
        String url = "http://192.168.0.106/mess/index.php/ajaxactions";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int ec = jsonObject.getInt("ec");
                            error_code = ec;
                            //Toast.makeText(getActivity(),ec+"" , Toast.LENGTH_LONG).show();

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
                params.put("action", "rpassword");
                params.put("phone",forgot_mob.getText().toString());
                System.out.println(params);
                return params;
            }
        };

        // add it to the RequestQueue
        StartActivity.get().getRequestQueue().add(postRequest);
        getMessage();

    }
    private void getMessage(){
        String url = "http://192.168.0.106/mess/index.php/ajaxactions";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try{
                            if(error_code == 1){
                                forgot_password_button.setText("SENT");
                                Toast.makeText(getActivity(),"Message has been sent!",Toast.LENGTH_LONG).show();
                            }
                            else{
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONObject ec1 = data.getJSONObject("ec");
                                String message = ec1.getString(error_code+"");
                                Toast.makeText(getActivity(),message , Toast.LENGTH_LONG).show();
                            }


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


}


