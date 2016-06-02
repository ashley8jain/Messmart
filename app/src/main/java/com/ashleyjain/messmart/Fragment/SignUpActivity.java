package com.ashleyjain.messmart.Fragment;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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

public class SignUpActivity extends Fragment {

    EditText newmobile;
    Button sendotp;
    TextView alreadyhave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"YuppySC-Regular.ttf");

        newmobile = (EditText) view.findViewById(R.id.newmob);
        newmobile.setTypeface(font);
        newmobile.addTextChangedListener(new checkError(newmobile));

        sendotp = (Button) view.findViewById(R.id.sendotp);
        sendotp.setTypeface(font);

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Sending.....", true);
                String url = StartActivity.host+"index.php/ajaxactions";
                final String phone = newmobile.getText().toString();
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
                                        ConfirmSignUp signupfragment = new ConfirmSignUp();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("PHONE", phone);
                                        signupfragment.setArguments(bundle);
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment_not, signupfragment, signupfragment.toString())
                                                .addToBackStack(signupfragment.toString())
                                                .commit();

                                    }
                                    else{
                                        Toast.makeText(getActivity(),StartActivity.errorcode.getString(""+ec), Toast.LENGTH_LONG).show();
                                    }
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

        alreadyhave = (TextView) view.findViewById(R.id.already);
        alreadyhave.setText(Html.fromHtml("Already have an account? "+"<font color=#039be5>"+"Login"+"</font><br><br>"));
//        alreadyhave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().popBackStack();
//            }
//        });
        alreadyhave.setMovementMethod(LinkMovementMethod.getInstance());
        alreadyhave.setText(alreadyhave.getText(), TextView.BufferType.SPANNABLE);
        Spannable alreadyspannable = (Spannable)alreadyhave.getText();
        ClickableSpan myAlreadySpan = new ClickableSpan()
        {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        };
        alreadyspannable.setSpan(myAlreadySpan, 25, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }




}
