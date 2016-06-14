package com.ashleyjain.messmart.function;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.Fragment.AboutusActivity;
import com.ashleyjain.messmart.Fragment.ContactusActivity;
import com.ashleyjain.messmart.Fragment.LoginActivity;
import com.ashleyjain.messmart.Fragment.MessListTabLayout;
import com.ashleyjain.messmart.Fragment.OrderFragment;
import com.ashleyjain.messmart.Fragment.Setting;
import com.ashleyjain.messmart.Fragment.UserprofileActivity;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ashleyjain on 08/06/16.
 */
public class drawer {

    public static Drawer drawer = null;
    DrawerBuilder builder=null;
    AccountHeader headerResult = null;
    TextView Name,Email;
    ImageView im;

    public void rebuild(){




        //profile section in drawer layout
        headerResult = new AccountHeaderBuilder()
                .withProfileImagesClickable(false)
                .withActivity(StartActivity.get())
                .withSelectionListEnabled(false)
                .addProfiles(
                        new ProfileDrawerItem().withName("Guest User")
                )
                .withHeaderBackground(R.drawable.slider1)
                .build();

        builder = new DrawerBuilder()
                .withActivity(StartActivity.get())
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult);

        Name = (TextView) headerResult.getView().findViewById(R.id.material_drawer_account_header_name);
        Name.setTextSize(30);
        Email = (TextView) headerResult.getView().findViewById(R.id.material_drawer_account_header_email);
        im = (ImageView) headerResult.getView().findViewById(R.id.material_drawer_account_header_current);

        final ProgressDialog dialog = ProgressDialog.show(StartActivity.get(), "", "Loading...", true);

        StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, StartActivity.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        //response JSON from url
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject dataobject = jsonResponse.getJSONObject("data");
                            StartActivity.errorcode= dataobject.getJSONObject("ec");
                            StartActivity.aboutus = dataobject.getString("aboutus");
                            StartActivity.regions = dataobject.getJSONArray("regions");
                            System.out.println("about: "+StartActivity.aboutus);
                            StartActivity.contactus = dataobject.getString("contact");
                            JSONObject days7 = dataobject.getJSONObject("7days");
                            StartActivity.days = days7.getJSONArray("textl");
                            StartActivity.days2 = days7.getJSONArray("timel");
                            JSONObject drawerj = dataobject.getJSONObject("drawer");
                            StartActivity.loginname = drawerj.getString("loginname");
                            System.out.println("loginname: " + StartActivity.loginname);
                            StartActivity.loginid = drawerj.getString("loginid");
                            StartActivity.logintype = drawerj.getString("logintype");

                            Name.setText(StartActivity.loginname.equals("Profile") ? "Guest User" : StartActivity.loginname);

                            StartActivity.tabs = drawerj.getJSONArray("tabs");
                            StartActivity.tab_map = drawerj.getJSONObject("tab_map");

                            HashMap hm = new HashMap();
                            hm.put("Login",R.drawable.login);
                            hm.put("Logout", R.drawable.logout);
                            hm.put("Contact us", R.drawable.online_support);
                            hm.put("About us", R.drawable.aboutus);
                            hm.put("Menu", R.drawable.menu);
                            hm.put("Orders", R.drawable.order);
                            hm.put("Settings", R.drawable.setting);
                            hm.put(StartActivity.loginname, R.drawable.profile);


                            try {

                                for(int i = 0 ;i < StartActivity.tabs.length();i++){
                                    String cap = null;
                                    cap = (String) StartActivity.tab_map.getJSONArray(StartActivity.tabs.getString(i)).get(0);
                                    builder.addDrawerItems(new PrimaryDrawerItem().withName(cap).withIcon((int)hm.get(cap)));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if( !StartActivity.logintype.equals("null")&& !StartActivity.logintype.equals("u")){
                                AlertDialog.Builder alertbuilder = new AlertDialog.Builder(StartActivity.get());
                                alertbuilder.setTitle("You are not user!!");
                                alertbuilder.setMessage("Go to www.messmart.com website");
                                alertbuilder.setCancelable(true);
                                alertbuilder.setPositiveButton("Open Website", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.messmart.com"));
                                        StartActivity.get().startActivity(browserIntent);
                                        StartActivity.get().logoutapi();
                                    }
                                });
                                alertbuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        StartActivity.get().logoutapi();
                                    }
                                });
                                AlertDialog alertDialog = alertbuilder.create();
                                alertDialog.show();
                            }
                            dialog.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(StartActivity.get(), e.toString(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StartActivity.get(), error.toString(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Log.d("debug", "posting param");
                Map<String, String> params = new HashMap<String, String>();

                // the POST parameters:
                params.put("action", "getinit");
                System.out.println(params);
                return params;
            }
        };

        // add it to the RequestQueue
        StartActivity.get().getRequestQueue().add(postRequest);



        drawer = builder.build();


        builder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View v, int position, IDrawerItem drawerItem) {
                String name = ((Nameable) drawerItem).getName().toString();
                if (name.equals("Menu")) {
                    StartActivity.get().popStack();
                    MessListTabLayout fragment = new MessListTabLayout("", "", false);
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("days", StartActivity.days.toString());
                    bundle3.putString("days2", StartActivity.days2.toString());
                    fragment.setArguments(bundle3);
                    StartActivity.get().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_not, fragment, fragment.toString())
                            .addToBackStack(null)
                            .commit();
                } else if (name.equals("Login")) {
                    LoginActivity loginfragment = new LoginActivity();
                    StartActivity.get().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_not, loginfragment, loginfragment.toString())
                            .addToBackStack(null)
                            .commit();


                } else if (name.equals("Logout")) {
                    StartActivity.get().logoutapi();
                } else if (name.equals("About us")) {
                    StartActivity.get().popStack();
                    AboutusActivity aboutfragment = new AboutusActivity();
                    Bundle bundle = new Bundle();
                    bundle.putString("aboutus", StartActivity.aboutus);
                    aboutfragment.setArguments(bundle);
                    StartActivity.get().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_not, aboutfragment, aboutfragment.toString())
                            .addToBackStack(aboutfragment.toString())
                            .commit();

                } else if (name.equals(("Contact us"))) {
                    StartActivity.get().popStack();
                    ContactusActivity contactfragment = new ContactusActivity();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("contactus", StartActivity.contactus);
                    contactfragment.setArguments(bundle2);
                    StartActivity.get().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_not, contactfragment, contactfragment.toString())
                            .addToBackStack(contactfragment.toString())
                            .commit();
                } else if (name.equals("Orders")) {
                    StartActivity.get().popStack();
                    OrderFragment ofragment = new OrderFragment();
                    StartActivity.get().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_not, ofragment, ofragment.toString())
                            .addToBackStack(ofragment.toString())
                            .commit();
                } else if (name.equals(StartActivity.loginname)) {
                    StartActivity.get().popStack();
                    UserprofileActivity userprofileActivity = new UserprofileActivity();
                    StartActivity.get().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_not, userprofileActivity, userprofileActivity.toString())
                            .addToBackStack(userprofileActivity.toString())
                            .commit();

                } else if (name.equals("Settings")) {
                    StartActivity.get().popStack();
                    Setting settingfragment = new Setting();
                    StartActivity.get().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_not, settingfragment, settingfragment.toString())
                            .addToBackStack(settingfragment.toString())
                            .commit();
                }
                return false;
            }
        });

        StringRequestCookies postRequest2 = new StringRequestCookies(Request.Method.POST, StartActivity.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        //response JSON from url
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject data = jsonResponse.getJSONObject("data");
                            JSONObject uinfo = data.getJSONObject("uinfo");
                            //Email.setText("Wallet: "+uinfo.getString("wallet")+"/-");
                            String imageurl = uinfo.getString("profilepic");
                            System.out.println("profileurl: " + imageurl);
                            if(imageurl!=null)
                                Picasso.with(StartActivity.get()).load(StartActivity.host+imageurl).into(im);
                        } catch (JSONException e) {
                        }

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StartActivity.get(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Log.d("debug", "posting param");
                Map<String, String> params = new HashMap<String, String>();

                // the POST parameters:
                params.put("action", "profile");
                System.out.println(params);
                return params;
            }
        };

        // add it to the RequestQueue
        StartActivity.get().getRequestQueue().add(postRequest2);

    }


}
