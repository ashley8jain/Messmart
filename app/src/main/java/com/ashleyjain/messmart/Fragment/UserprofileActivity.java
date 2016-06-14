package com.ashleyjain.messmart.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.function.Payment;
import com.ashleyjain.messmart.function.RoundedImageView;
import com.ashleyjain.messmart.function.StringRequestCookies;
import com.ashleyjain.messmart.function.drawer;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class UserprofileActivity extends Fragment {

    RoundedImageView userlogo;
    static TextView inputUsername1,inputUserwallet1,inputUseraddress1;
    String name,address;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    Boolean upload = false;
    private String KEY_IMAGE = "profilepic";
    private String KEY_NAME = "name";
    private String UPLOAD_URL =StartActivity.host+"mohit.php";


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
    public void onResume() {
        super.onResume();
        if(!upload){
            setInfo();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Profile");
        return inflater.inflate(R.layout.activity_userprofile, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_userprofile);
        userlogo = (RoundedImageView)view.findViewById(R.id.profImg);
        inputUsername1 = (TextView)view.findViewById(R.id.userinputname1);
        inputUseraddress1 = (TextView)view.findViewById(R.id.userinputaddress1);
        inputUserwallet1 = (TextView)view.findViewById(R.id.userinputwallet1);
        TextView showDialogedit = (TextView) view.findViewById(R.id.showdialogedit);
        TextView tv = (TextView) view.findViewById(R.id.add);

        setInfo();

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
                                                        StartActivity.get().getSupportFragmentManager().popBackStack();
                                                        UserprofileActivity userprofileActivity = new UserprofileActivity();
                                                        StartActivity.get().getSupportFragmentManager().beginTransaction()
                                                                .replace(R.id.fragment_not, userprofileActivity, userprofileActivity.toString())
                                                                .addToBackStack(userprofileActivity.toString())
                                                                .commit();
                                                        new drawer().rebuild();
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
                                StartActivity.get().getRequestQueue().add(postRequest);
                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = (LayoutInflater.from(getActivity())).inflate(R.layout.add_wallet, null);
                final Button apply = (Button) view.findViewById(R.id.apply);
                Button pay = (Button) view.findViewById(R.id.pay);

                final EditText timeSlot = (EditText) view.findViewById(R.id.timeslot);
                apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ProgressDialog dialog2 = ProgressDialog.show(getActivity(), "", "Loading.....", true);

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
                                                apply.setText("applied");
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
                                },
                                new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                                        dialog2.dismiss();
                                    }
                                }

                        ) {
                            @Override
                            protected Map<String, String> getParams() {
                                Log.d("debug", "posting param");
                                Map<String, String> params = new HashMap<String, String>();

                                // the POST parameters:
                                params.put("preftime", timeSlot.getText().toString());
                                params.put("action", "cash_collection");
                                System.out.println(params);
                                return params;
                            }
                        };

                        // add it to the RequestQueue
                        StartActivity.get().getRequestQueue().add(postRequest);
                    }
                });

                final EditText amount = (EditText) view.findViewById(R.id.amount);
                pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ProgressDialog dialog2 = ProgressDialog.show(getActivity(), "", "Loading.....", true);

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
                                                Intent intent = new Intent(getActivity(), Payment.class);
                                                String data = jsonResponse.getString("data");
                                                intent.putExtra("url", data);
                                                startActivity(intent);
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
                                },
                                new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                                        dialog2.dismiss();
                                    }
                                }

                        ) {
                            @Override
                            protected Map<String, String> getParams() {
                                Log.d("debug", "posting param");
                                Map<String, String> params = new HashMap<String, String>();

                                // the POST parameters:
                                params.put("addamount", amount.getText().toString());
                                params.put("device", "mobile");
                                params.put("session_id", StartActivity.sessionID);
                                params.put("action", "getpayurl");
                                System.out.println(params);
                                return params;
                            }
                        };

                        // add it to the RequestQueue
                        StartActivity.get().getRequestQueue().add(postRequest);
                    }
                });

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                alertBuilder.setView(view);

                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        final TextView profpic = (TextView) view.findViewById(R.id.profpic);

        profpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload = true;
                showFileChooser();
            }
        });

        final TextView upload = (TextView) view.findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }


    private void setInfo(){
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
        StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, StartActivity.url,
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
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                userlogo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Uploading...","Please wait...",false,false);
        StringRequestCookies stringRequest = new StringRequestCookies(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(getActivity(), s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(getActivity(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String name = "pic";

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);

                //returning parameters
                return params;
            }
        };

        // add it to the RequestQueue
        StartActivity.get().getRequestQueue().add(stringRequest);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
