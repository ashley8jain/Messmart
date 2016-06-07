package com.ashleyjain.messmart.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.function.KeyboardDown;
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
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("MesSmart");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Login");
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"YuppySC-Regular.ttf");

        RelativeLayout rlayout = (RelativeLayout) view.findViewById(R.id.activity_login);
        rlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                KeyboardDown.keyboardDown();
            }

        });
        RelativeLayout rlayout2 = (RelativeLayout) view.findViewById(R.id.activity_login2);
        rlayout2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                KeyboardDown.keyboardDown();
            }

        });

        ScrollView scrollView = (ScrollView) view.findViewById(R.id.activity_login_sv);
        scrollView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                KeyboardDown.keyboardDown();
            }

        });
        mobileno = (EditText) view.findViewById(R.id.phone);
        mobileno.setTypeface(font);
        mobileno.addTextChangedListener(new checkError(mobileno));

        pass = (EditText) view.findViewById(R.id.confirmotp);
        pass.setTypeface(font);
        pass.addTextChangedListener(new checkError(pass));

        login = (Button) view.findViewById(R.id.loginbutton);

        signup = (TextView) view.findViewById(R.id.signup);
        signup.setText(Html.fromHtml("Don't have an account? <font color=#039be5>Sign Up</font><br><br>"));
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SignUpActivity signupfragment = new SignUpActivity();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_not, signupfragment, signupfragment.toString())
//                        .addToBackStack(signupfragment.toString())
//                        .commit();
//            }
//        });
        signup.setMovementMethod(LinkMovementMethod.getInstance());
        signup.setText(signup.getText(), TextView.BufferType.SPANNABLE);
        Spannable signupspannable = (Spannable)signup.getText();
        ClickableSpan mySignUpSpan = new ClickableSpan()
        {
            @Override
            public void onClick(View v) {
                SignUpActivity signupfragment = new SignUpActivity();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_not, signupfragment, signupfragment.toString())
                        .addToBackStack(signupfragment.toString())
                        .commit();

            }
            @Override
            public void updateDrawState(TextPaint ds) {// override updateDrawState
                ds.setUnderlineText(false); // set to false to remove underline
            }



        };
        signupspannable.setSpan(mySignUpSpan, 23, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        signupspannable.setSpan(new ForegroundColorSpan(Color.parseColor("#039be5")),23, 30,0);



        joinus = (TextView) view.findViewById(R.id.joinus);
        joinus.setText(Html.fromHtml("Are you a mess? " + "<font color=#039be5>" + "Join us" + "</font><br><br>"));
//        joinus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MessjoinActivity messFragment = new MessjoinActivity();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_not, messFragment, messFragment.toString())
//                        .addToBackStack(messFragment.toString())
//                        .commit();
//            }
//        });
        joinus.setMovementMethod(LinkMovementMethod.getInstance());
        joinus.setText(joinus.getText(), TextView.BufferType.SPANNABLE);
        Spannable joinusspannable = (Spannable)joinus.getText();
        ClickableSpan myJoinUsSpan = new ClickableSpan()
        {
            @Override
            public void onClick(View v) {
                MessjoinActivity messFragment = new MessjoinActivity();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_not, messFragment, messFragment.toString())
                        .addToBackStack(messFragment.toString())
                        .commit();
            }
            @Override
            public void updateDrawState(TextPaint ds) {// override updateDrawState
                ds.setUnderlineText(false); // set to false to remove underline
            }
        };
        joinusspannable.setSpan(myJoinUsSpan, 16, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        joinusspannable.setSpan(new ForegroundColorSpan(Color.parseColor("#039be5")),16, 23,0);
        //joinusspannable.setSpan(new MyClickableSpan());


        forgetpass = (TextView) view.findViewById(R.id.forgot);
        forgetpass.setText(Html.fromHtml("<font color=#039be5> Forgot your password ? </font><br><br>"));
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPasswordActivity forgotPasswordActivity = new ForgotPasswordActivity();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_not, forgotPasswordActivity, forgotPasswordActivity.toString())
                        .addToBackStack(forgotPasswordActivity.toString())
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
                                        getActivity().finish();
//                                        StartActivity.get().drawer();
//                                        StartActivity.get().getSupportFragmentManager().popBackStackImmediate();
                                    }
                                    else{
                                        Toast.makeText(getActivity(),StartActivity.errorcode.getString(""+ec), Toast.LENGTH_LONG).show();
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
                protected Map<String, String> getParams()   {
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
                KeyboardDown.keyboardDown();
            }
        });
    }



}