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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
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

        resend = (Button) view.findViewById(R.id.resend);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = ph.getText().toString();
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Sending.....", true);
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
            }
        });

        otp = (EditText) view.findViewById(R.id.confirmotppp);
        System.out.println("confirmotp: "+confirmpass);
        confirm = (Button) view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Verifying.....", true);
                String url = StartActivity.host+"index.php/ajaxactions";
                confirmpass = otp.getText().toString();

                StringRequestCookies postRequest2 = new StringRequestCookies(Request.Method.POST, url,
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
                        params.put("phone",phone);
                        params.put("otp", confirmpass);
                        params.put("action", "confirmotp");
                        System.out.println(params);
                        return params;
                    }
                };

                // add it to the RequestQueue
                StartActivity.get().getRequestQueue().add(postRequest2);
            }
        });

        TextView already = (TextView) view.findViewById(R.id.already);
        already.setText(Html.fromHtml("Already have an account? " + "<font color=#039be5>" + "Login" + "</font><br><br>"));
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }
}
