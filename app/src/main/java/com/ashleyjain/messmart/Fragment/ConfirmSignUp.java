package com.ashleyjain.messmart.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.function.KeyboardDown;
import com.ashleyjain.messmart.function.StringRequestCookies;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ConfirmSignUp extends Fragment {

    String phone,confirmpass;
    EditText ph,otp;
    Button resend,confirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phone = getArguments().getString("PHONE");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ph = (EditText) view.findViewById(R.id.mob);
        ph.setText(phone);
        RelativeLayout rlayout = (RelativeLayout) view.findViewById(R.id.fragment_confirm_sign_up);
        rlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                KeyboardDown.keyboardDown();
            }

        });

        resend = (Button) view.findViewById(R.id.resend);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = ph.getText().toString();
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Sending.....", true);

                StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, StartActivity.url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                                //response JSON from url
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    Integer ec = jsonResponse.getInt("ec");
                                    if(ec == 1){
                                        Toast.makeText(getActivity(), "Sent!", Toast.LENGTH_LONG).show();
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
                    protected Map<String, String> getParams() {
                        Log.d("debug", "posting param");
                        Map<String, String> params = new HashMap<String, String>();

                        // the POST parameters:
                        params.put("phone", phone);
                        params.put("action", "sendotp");
                        System.out.println(params);
                        return params;
                    }
                };

                // add it to the RequestQueue
                StartActivity.get().getRequestQueue().add(postRequest);
                KeyboardDown.keyboardDown();
            }
        });

        otp = (EditText) view.findViewById(R.id.confirmotppp);
        System.out.println("confirmotp: "+confirmpass);
        confirm = (Button) view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Verifying.....", true);
                confirmpass = otp.getText().toString();

                StringRequestCookies postRequest2 = new StringRequestCookies(Request.Method.POST, StartActivity.url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                                //response JSON from url
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    Integer ec = jsonResponse.getInt("ec");
                                    if(ec == 1){
                                        Toast.makeText(getActivity(), "Enter Account Details!", Toast.LENGTH_LONG).show();
                                        createAccount fragment = new createAccount();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("PHONE",phone);
                                        bundle.putString("OTP",confirmpass);
                                        fragment.setArguments(bundle);
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment_not, fragment, fragment.toString())
                                                .addToBackStack(fragment.toString())
                                                .commit();
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
                        params.put("phone",phone+"");
                        params.put("otp", confirmpass+"");
                        params.put("action", "confirmotp");
                        System.out.println(params);
                        return params;
                    }
                };

                // add it to the RequestQueue
                StartActivity.get().getRequestQueue().add(postRequest2);
                KeyboardDown.keyboardDown();
            }
        });

        TextView already = (TextView) view.findViewById(R.id.already);
        already.setText(Html.fromHtml("" + "<font color=#039be5>" + "Login" + "</font><br><br>"));
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
//        already.setMovementMethod(LinkMovementMethod.getInstance());
//        already.setText(already.getText(), TextView.BufferType.SPANNABLE);
//        Spannable alreadyspannable = (Spannable)already.getText();
//        ClickableSpan myAlreadySpan = new ClickableSpan()
//        {
//            @Override
//            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().popBackStack();
//                getActivity().getSupportFragmentManager().popBackStack();
//            }
//            @Override
//            public void updateDrawState(TextPaint ds) {// override updateDrawState
//                ds.setUnderlineText(false); // set to false to remove underline
//            }
//        };
//        alreadyspannable.setSpan(myAlreadySpan, 25, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        alreadyspannable.setSpan(new ForegroundColorSpan(Color.parseColor("#039be5")),25, 30,0);

    }
}
