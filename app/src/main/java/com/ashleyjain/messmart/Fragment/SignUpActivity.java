package com.ashleyjain.messmart.Fragment;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("MesSmart");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                for(int i=0;i<getActivity().getSupportFragmentManager().getBackStackEntryCount();i++)
                    getActivity().getSupportFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Sign Up");
        return inflater.inflate(R.layout.activity_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"YuppySC-Regular.ttf");

        RelativeLayout rlayout = (RelativeLayout) view.findViewById(R.id.activity_sign_up);
        rlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                KeyboardDown.keyboardDown();
            }

        });

        newmobile = (EditText) view.findViewById(R.id.newmob);
        newmobile.setTypeface(font);
        newmobile.addTextChangedListener(new checkError(newmobile));

        /*newmobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager inputManager = (InputMethodManager)StartActivity.get().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    Toast.makeText(getActivity(),"unfocused",Toast.LENGTH_LONG).show();
                }

            }
        });*/

        sendotp = (Button) view.findViewById(R.id.sendotp);
        sendotp.setTypeface(font);

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = newmobile.getText().toString();
                if (phone.length() == 10) {
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
                                        if (ec == 1) {
                                            Toast.makeText(getActivity(), "Sent!", Toast.LENGTH_LONG).show();
                                            ConfirmSignUp signupfragment = new ConfirmSignUp();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("PHONE", phone);
                                            signupfragment.setArguments(bundle);
                                            getActivity().getSupportFragmentManager().beginTransaction()
                                                    .replace(R.id.fragment_not, signupfragment, signupfragment.toString())
                                                    .addToBackStack(signupfragment.toString())
                                                    .commit();

                                        } else {
                                            Toast.makeText(getActivity(), StartActivity.errorcode.getString("" + ec), Toast.LENGTH_LONG).show();
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
                    KeyboardDown.keyboardDown();
                }
                else {
                    Toast.makeText(getActivity(), "Please Enter Correct Phone Number!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alreadyhave = (TextView) view.findViewById(R.id.already);
        alreadyhave.setText(Html.fromHtml(""+"<font color=#039be5>"+"Login"+"</font><br><br>"));
        alreadyhave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
//        alreadyhave.setMovementMethod(LinkMovementMethod.getInstance());
//        alreadyhave.setText(alreadyhave.getText(), TextView.BufferType.SPANNABLE);
//        Spannable alreadyhavespannable = (Spannable)alreadyhave.getText();
//        ClickableSpan myAlreadyHaveSpan = new ClickableSpan()
//        {
//            @Override
//            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().popBackStack();
//            }
//            @Override
//            public void updateDrawState(TextPaint ds) {// override updateDrawState
//                ds.setUnderlineText(false); // set to false to remove underline
//            }
//        };
//        alreadyhavespannable.setSpan(myAlreadyHaveSpan, 25, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        alreadyhavespannable.setSpan(new ForegroundColorSpan(Color.parseColor("#039be5")),25, 30,0);

    }




}
