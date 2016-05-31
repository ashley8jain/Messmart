package com.ashleyjain.messmart;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
        usernewpw = (EditText)view.findViewById(R.id.usernewpw);
        usernewpwre = (EditText)view.findViewById(R.id.usernewpwre);
        useroldpw = (EditText)view.findViewById(R.id.useroldpw);
        changepw = (Button)view.findViewById(R.id.changepw);

        changepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usernewpw.getText().toString().equals(usernewpwre.getText().toString())){
                    Toast.makeText(getActivity(),"Passwords do not match!",Toast.LENGTH_LONG).show();
                }
                else {
                    String url = "http://192.168.0.106/mess/index.php/ajaxactions";
                    StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override

                                public void onResponse(String response) {
                                    Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

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
                            params.put("action", "cpassword");
                            params.put("oldpass", useroldpw.getText().toString());
                            params.put("newpass", usernewpw.getText().toString());
                            System.out.println(params);
                            return params;
                        }
                    };

                    // add it to the RequestQueue
                    StartActivity.get().getRequestQueue().add(postRequest);
                    //getActivity().finish();
                    //StartActivity.get().onBackPressed();
                    //getActivity().getSupportFragmentManager().beginTransaction().remove(Setting.this).commit();


                }
            }

        });

    }

}
