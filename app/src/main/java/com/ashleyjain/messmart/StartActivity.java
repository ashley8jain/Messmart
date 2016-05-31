package com.ashleyjain.messmart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ashleyjain.messmart.Fragment.AboutusActivity;
import com.ashleyjain.messmart.Fragment.ContactusActivity;
import com.ashleyjain.messmart.Fragment.LoginActivity;
import com.ashleyjain.messmart.Fragment.OrderFragment;
import com.ashleyjain.messmart.Fragment.ViewpagerFragment;
import com.ashleyjain.messmart.function.StringRequestCookies;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    protected Drawer drawer = null;
    AccountHeader headerResult = null;
    public static Boolean isLogin = false;
    String loginname;
    private Context context;
    String aboutus,contactus;
    public static JSONArray days,days2;
    public static JSONObject errorcode;
    public static String loginid;
    TextView Name;
    ImageView im;

    public static String host = "http://192.168.0.106/mess/";

    private static final String SET_COOKIE_KEY = "set-cookie";
    private static final String COOKIE_KEY = "cookie";
    private static final String SESSION_COOKIE = "PHPSESSID";
    private static StartActivity _instance;
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;

    public static StartActivity get() {
        return _instance;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//            Intent re = new Intent(StartActivity.this,StartActivity.class);
//            startActivity(re);
//            finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        context = this;
        _instance = this;
        _preferences = PreferenceManager.getDefaultSharedPreferences(this);  //for saving cookies
        _requestQueue = Volley.newRequestQueue(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MesSmart");

        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer();
            }
        });

        if (!isNetworkConnected(context)) {
            //when wifi is not connected
            AlertDialog.Builder alertbuilder = new AlertDialog.Builder(context);
            alertbuilder.setTitle("No Network Connection");
            alertbuilder.setCancelable(true);
            alertbuilder.setPositiveButton("Go to wifi settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            AlertDialog alertDialog = alertbuilder.create();
            alertDialog.show();
        } else {
            final ProgressDialog dialog = ProgressDialog.show(context, "", "Loading...", true);
            String url = host+"index.php/ajaxactions";

            StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response);
                            //response JSON from url
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String message = jsonResponse.getString("data");
                                JSONObject dataobject = jsonResponse.getJSONObject("data");
                                errorcode= dataobject.getJSONObject("ec");
                                aboutus = dataobject.getString("aboutus_content");
                                contactus = dataobject.getString("contact");
                                JSONObject days7 = dataobject.getJSONObject("7days");
                                days = days7.getJSONArray("textl");
                                days2 = days7.getJSONArray("timel");
                                //System.out.println()
                                if(isLogin){
                                    loginname = dataobject.getJSONObject("drawer").getString("loginname");
                                    loginid = dataobject.getJSONObject("drawer").getString("loginid");
                                    Name.setText(loginname);
                                    System.out.println("loginname: " + loginname);
                                }
                                dialog.dismiss();
                            } catch (JSONException e) {
                                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }

                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
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
            getRequestQueue().add(postRequest);

            ViewpagerFragment fragment = new ViewpagerFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_not,fragment,fragment.toString())
                    .commit();

            System.out.println("loginname2: "+loginname);

            //profile section in drawer layout
            headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .addProfiles(
                            new ProfileDrawerItem().withName(isLogin?loginname:"Guest User").withEmail(isLogin?"Logged in":"Not Signed in")
                    )
                    .withHeaderBackground(R.drawable.slider1)
                    .build();

            //buttons handling in drawer
            final DrawerBuilder builder = new DrawerBuilder()
                    .withActivity(this)
                    .withDisplayBelowStatusBar(true)
                    .withActionBarDrawerToggle(true)
                    .withAccountHeader(headerResult)
                    .withHasStableIds(true)
                    .addDrawerItems(
                            new SectionDrawerItem().withName("Menu"),
                            new PrimaryDrawerItem().withName("Mess").withIcon(R.drawable.bell_service),
                            new PrimaryDrawerItem().withName(isLogin ? "Logout" : "Login").withIcon(isLogin ? R.drawable.logout : R.drawable.login),
                            new PrimaryDrawerItem().withName("About us").withIcon(R.drawable.info),
                            new PrimaryDrawerItem().withName("Contact us").withIcon(R.drawable.online_support)
                    );
            if(isLogin)
                builder.addDrawerItems(
                        new PrimaryDrawerItem().withName("Orders"),
                        new PrimaryDrawerItem().withName("Profile"),
                        new PrimaryDrawerItem().withName("Setting")
                );

            builder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View v, int position, IDrawerItem drawerItem) {
                    Intent login;
                    switch (position) {
                        default:
                            if (position == 2) {
                                MessListTabLayout fragment = new MessListTabLayout("", "", false);
                                Bundle bundle3 = new Bundle();
                                bundle3.putString("days", days.toString());
                                bundle3.putString("days2", days2.toString());
                                fragment.setArguments(bundle3);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_not, fragment, fragment.toString())
                                        .addToBackStack(fragment.toString())
                                        .commit();
                            } else if (position == 3) {
                                if (!isLogin) {
                                    LoginActivity loginfragment = new LoginActivity();
                                    getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.fragment_not, loginfragment, loginfragment.toString())
                                            .addToBackStack(loginfragment.toString())
                                            .commit();

                                } else {
                                    isLogin = false;
                                    Intent re = new Intent(StartActivity.this, StartActivity.class);
                                    startActivity(re);
                                    Toast.makeText(getApplicationContext(), "Log Out Successful", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            } else if (position == 4) {
                                AboutusActivity aboutfragment = new AboutusActivity();
                                Bundle bundle = new Bundle();
                                bundle.putString("aboutus", aboutus);
                                aboutfragment.setArguments(bundle);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_not, aboutfragment, aboutfragment.toString())
                                        .addToBackStack(aboutfragment.toString())
                                        .commit();

                            } else if (position == 5) {
                                ContactusActivity contactfragment = new ContactusActivity();
                                Bundle bundle2 = new Bundle();
                                bundle2.putString("contactus", contactus);
                                contactfragment.setArguments(bundle2);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_not, contactfragment, contactfragment.toString())
                                        .addToBackStack(contactfragment.toString())
                                        .commit();
                            } else if (position == 6) {
                                OrderFragment ofragment = new OrderFragment();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_not, ofragment, ofragment.toString())
                                        .addToBackStack(ofragment.toString())
                                        .commit();
                            }
                            else if(position==7){
                                UserprofileActivity userprofileActivity = new UserprofileActivity();
                                                            getSupportFragmentManager().beginTransaction()
                                                                          .replace(R.id.fragment_not,userprofileActivity,userprofileActivity.toString())
                                                                           .addToBackStack(userprofileActivity.toString())
                                                                           .commit();

                            }
                            else if(position==8){
                                Setting settingfragment = new Setting();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_not,settingfragment,settingfragment.toString())
                                        .addToBackStack(settingfragment.toString())
                                        .commit();
                            }
                            break;

                    }
                    return false;
                }
            });

            drawer = builder.build();
            Name = (TextView) headerResult.getView().findViewById(R.id.material_drawer_account_header_name);
            im = (ImageView) headerResult.getView().findViewById(R.id.material_drawer_account_header_current);

            final ProgressDialog dialog2 = ProgressDialog.show(context, "", "Loading...", true);

            StringRequestCookies postRequest2 = new StringRequestCookies(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response);
                            //response JSON from url
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                JSONObject uinfo = jsonResponse.getJSONObject("data").getJSONObject("uinfo");
                                String imageurl = uinfo.getString("profilepic");
                                System.out.println("profileurl: " + imageurl);
                                if(imageurl!=null)
                                    Picasso.with(context).load(StartActivity.host+imageurl).into(im);
                                dialog2.dismiss();
                            } catch (JSONException e) {
                                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                                dialog2.dismiss();
                            }

                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
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
                    System.out.println(params);
                    return params;
                }
            };

            // add it to the RequestQueue
            getRequestQueue().add(postRequest2);
        }

    }

    //Check if there is network connection or not
    public static boolean isNetworkConnected(Context con) {
        ConnectivityManager connMgr = (ConnectivityManager) con.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    //checking session cookie
    public final void checkSessionCookie(Map<String, String> headers) {
        if (headers.containsKey(SET_COOKIE_KEY)
                && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                SharedPreferences.Editor prefEditor = _preferences.edit();
                prefEditor.putString(SESSION_COOKIE, cookie);
                prefEditor.commit();
            }
        }
    }

    /**
     * Adds session cookie to headers if exists.
     * @param headers
     */
    public final void addSessionCookie(Map<String, String> headers) {
        String sessionId = _preferences.getString(SESSION_COOKIE, "");
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }
    }

    //Volley request queue
    public RequestQueue getRequestQueue() {
        return _requestQueue;
    }

}
