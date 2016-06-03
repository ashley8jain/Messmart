package com.ashleyjain.messmart.Fragment;

        import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
        import com.ashleyjain.messmart.R;
        import com.ashleyjain.messmart.StartActivity;
        import com.ashleyjain.messmart.function.StringRequestCookies;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserprofileActivity extends Fragment {
    ImageView userlogo;
    static TextView inputUsername1,inputUserwallet1,inputUseraddress1;
    String name,address;

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
        return inflater.inflate(R.layout.activity_userprofile, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_userprofile);
        userlogo = (ImageView)view.findViewById(R.id.profImg);
        inputUsername1 = (TextView)view.findViewById(R.id.userinputname1);
        inputUseraddress1 = (TextView)view.findViewById(R.id.userinputaddress1);
        inputUserwallet1 = (TextView)view.findViewById(R.id.userinputwallet1);

        setInfo();

        TextView showDialogedit = (TextView) view.findViewById(R.id.showdialogedit);
        showDialogedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View view = (LayoutInflater.from(getActivity())).inflate(R.layout.user_input, null);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                alertBuilder.setView(view);
                final EditText userInputname = (EditText) view.findViewById(R.id.userinputname);
                final EditText userInputaddress = (EditText) view.findViewById(R.id.userinputaddress);
                userInputname.setText(name);
                userInputaddress.setText(address);

                alertBuilder.setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog dialog2 = ProgressDialog.show(getActivity(), "", "Loading......", true);
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
                                                    if (ec == 1) {
                                                        StartActivity.get().getSupportFragmentManager().popBackStack();
                                                        UserprofileActivity userprofileActivity = new UserprofileActivity();
                                                        StartActivity.get().getSupportFragmentManager().beginTransaction()
                                                                .replace(R.id.fragment_not, userprofileActivity, userprofileActivity.toString())
                                                                .addToBackStack(userprofileActivity.toString())
                                                                .commit();
                                                    } else {
                                                        Toast.makeText(getActivity(), StartActivity.errorcode.getString("" + ec), Toast.LENGTH_LONG).show();
                                                    }
                                                    System.out.println("Message: " + ec);
                                                    dialog2.dismiss();
                                                } catch (JSONException e) {
                                                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                                                    dialog2.dismiss();
                                                }
                                            }
                                            //Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                                        },
                                        new Response.ErrorListener()

                                        {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                                                dialog2.dismiss();
                                            }
                                        }

                                )

                                {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Log.d("debug", "posting param");
                                        Map<String, String> params = new HashMap<String, String>();

                                        // the POST parameters:
                                        params.put("action", "saveprofile");
                                        params.put("name", userInputname.getText().toString());
                                        params.put("address", userInputaddress.getText().toString());
                                        System.out.println(params);
                                        return params;
                                    }
                                };

                                // add it to the RequestQueue
                                StartActivity.get().

                                        getRequestQueue()

                                        .

                                                add(postRequest);


                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });
    }


    private void setInfo(){
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
        String url = StartActivity.host+"index.php/ajaxactions";
        StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONObject uinfo = data.getJSONObject("uinfo");
                            name = uinfo.getString("name");
                            address = uinfo.getString("address");
                            inputUsername1.setText(name);
                            inputUseraddress1.setText(address);
                            inputUserwallet1.setText(uinfo.getString("wallet")+"/-");
                            Picasso.with(getActivity()).load(StartActivity.host + uinfo.getString("profilepic")).into(userlogo);
                            dialog.dismiss();
                        }
                        catch(JSONException je){
                            Toast.makeText(getActivity(),je.toString(),Toast.LENGTH_LONG).show();
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
                params.put("action", "profile");
                params.put("uid",StartActivity.loginid+"");
                System.out.println(params);
                return params;
            }
        };

        // add it to the RequestQueue
        StartActivity.get().getRequestQueue().add(postRequest);

    }
}
