package com.ashleyjain.messmart.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.function.StringRequestCookies;
import com.ashleyjain.messmart.function.checkError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Fragment {

    EditText mobileno,pass;
    Button login;
    TextView signup,joinus,forgetpass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"YuppySC-Regular.ttf");

        mobileno = (EditText) view.findViewById(R.id.phone);
        mobileno.setTypeface(font);
        mobileno.addTextChangedListener(new checkError(mobileno));

        pass = (EditText) view.findViewById(R.id.confirmotp);
        pass.setTypeface(font);
        pass.addTextChangedListener(new checkError(pass));

        login = (Button) view.findViewById(R.id.loginbutton);

        signup = (TextView) view.findViewById(R.id.signup);
        signup.setText(Html.fromHtml("Don't have an account? "+"<font color=#039be5>"+"Sign Up"+"</font><br><br>"));

        joinus = (TextView) view.findViewById(R.id.joinus);
        joinus.setText(Html.fromHtml("Are you a mess? "+"<font color=#039be5>"+"Join us"+"</font><br><br>"));
        joinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessjoinActivity messFragment = new MessjoinActivity();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_not, messFragment, messFragment.toString())
                        .addToBackStack(messFragment.toString())
                        .commit();
            }
        });

        forgetpass = (TextView) view.findViewById(R.id.forgot);
        forgetpass.setText(Html.fromHtml("<font color=#039be5> Forgot your password ? </font><br><br>"));

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity signupfragment = new SignUpActivity();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_not, signupfragment, signupfragment.toString())
                        .addToBackStack(signupfragment.toString())
                        .commit();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Logging in.....", true);
                final String mbnb = mobileno.getText().toString();
                final String pwd = pass.getText().toString();

                String url = StartActivity.host+"index.php/ajaxactions";

                StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                                //response JSON from url
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    Integer ec = jsonResponse.getInt("ec");
                                    if(ec == 1){
                                        Toast.makeText(getActivity(),"Login Successful", Toast.LENGTH_LONG).show();
                                        Intent re = new Intent(getContext(),StartActivity.class);
                                        startActivity(re);
                                        StartActivity.isLogin = true;
                                        getActivity().finish();
                                    }
                                    else if(ec == -1){
                                        Toast.makeText(getActivity(),"Incorrect password", Toast.LENGTH_LONG).show();
                                    }
                                    else if(ec == -3){
                                        Toast.makeText(getActivity(),"OTP is incorrect", Toast.LENGTH_LONG).show();
                                    }
                                    System.out.println("Message: " + ec);
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
                        params.put("loginphone", mbnb);
                        params.put("loginpass", pwd);
                        params.put("action", "login");
                        System.out.println(params);
                        return params;
                    }
                };

                // add it to the RequestQueue
                StartActivity.get().getRequestQueue().add(postRequest);
            }
        });
    }



}