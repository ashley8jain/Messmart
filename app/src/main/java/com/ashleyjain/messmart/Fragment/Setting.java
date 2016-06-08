package com.ashleyjain.messmart.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Setting extends Fragment {

    EditText useroldpw,usernewpw,usernewpwre;
    Button changepw;

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("MesSmart");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Settings");
        return inflater.inflate(R.layout.fragment_setting, container, false);
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
        usernewpw = (EditText)view.findViewById(R.id.usernewpw);
        usernewpwre = (EditText)view.findViewById(R.id.usernewpwre);
        useroldpw = (EditText)view.findViewById(R.id.useroldpw);
        changepw = (Button)view.findViewById(R.id.changepw);
        RelativeLayout rlayout = (RelativeLayout) view.findViewById(R.id.fragment_setting);
        rlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                KeyboardDown.keyboardDown();
            }

        });
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.fragment_setting_sv);
        scrollView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                KeyboardDown.keyboardDown();
            }

        });

        CheckBox showPassword = (CheckBox) view.findViewById(R.id.showHidePassword);
        showPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    //Show password
                    useroldpw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    usernewpw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    usernewpwre.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    //Hide password
                    useroldpw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    usernewpw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    usernewpwre.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        changepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usernewpw.getText().toString().equals(usernewpwre.getText().toString())){
                    Toast.makeText(getActivity(),"Passwords do not match!",Toast.LENGTH_LONG).show();
                }
                else {
                    final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Changing......", true);
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
                                            Toast.makeText(getActivity(),"Changed", Toast.LENGTH_LONG).show();
                                            getActivity().getSupportFragmentManager().popBackStack();
                                        }
                                        else{
                                            Toast.makeText(getActivity(), StartActivity.errorcode.getString(""+ec), Toast.LENGTH_LONG).show();
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
                            params.put("action", "cpassword");
                            params.put("oldpass", useroldpw.getText().toString());
                            params.put("newpass", usernewpw.getText().toString());
                            System.out.println(params);
                            return params;
                        }
                    };

                    // add it to the RequestQueue
                    StartActivity.get().getRequestQueue().add(postRequest);
                    KeyboardDown.keyboardDown();

                }
            }

        });

    }

}
