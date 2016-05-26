package com.ashleyjain.messmart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

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
    JSONArray days,days2;

    public static String host = "http://192.168.0.104/mess/";

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
        if(isLogin){
            Intent re = new Intent(StartActivity.this,StartActivity.class);
            startActivity(re);
            finish();
        }
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
                            aboutus = dataobject.getString("aboutus_content");
                            contactus = dataobject.getString("contact");
                            JSONObject days7 = dataobject.getJSONObject("7days");
                            days = days7.getJSONArray("textl");
                            days2 = days7.getJSONArray("timel");
                            //System.out.println()
                            if(isLogin){
                                loginname = dataobject.getJSONObject("drawer").getString("loginname");
                                System.out.println("loginname: "+loginname);
                            }
                            System.out.println("Message: " + message);
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
                .addToBackStack(fragment.toString())
                .commit();
        System.out.println("loginname2: "+loginname);

        //profile section in drawer layout
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        new ProfileDrawerItem().withName(isLogin?loginname:"Guest User").withEmail(isLogin?"Logged in":"Not Signed in"),
                        new ProfileSettingDrawerItem()
                                .withName(isLogin?"Logout":"Login")
                                .withIdentifier(1)
                )
                .withHeaderBackground(R.drawable.slider1)
                .withProfileImagesClickable(true)
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {

                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {

                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {

                        if (profile != null && profile instanceof IDrawerItem) {
                            switch ((int) profile.getIdentifier()) {
                                case 1:
                                    if(!isLogin) {
                                        LoginActivity loginfragment = new LoginActivity();
                                        getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment_not, loginfragment, loginfragment.toString())
                                                .addToBackStack(loginfragment.toString())
                                                .commit();
                                    }
                                    else{
                                        isLogin = false;
                                        Intent re = new Intent(StartActivity.this,StartActivity.class);
                                        startActivity(re);
                                        Toast.makeText(getApplicationContext(), "Log Out Successful", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                        return false;
                    }
                })
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
                        new PrimaryDrawerItem().withName("Cart").withIdentifier(4).withIcon(R.drawable.shopping_cart),
                        new PrimaryDrawerItem().withName("Mess").withIdentifier(4).withIcon(R.drawable.bell_service),
                        new PrimaryDrawerItem().withName("Pricing").withIdentifier(4).withIcon(R.drawable.coins),
                        new PrimaryDrawerItem().withName(isLogin ? "Logout" : "Login").withIdentifier(4).withIcon(isLogin ? R.drawable.logout : R.drawable.login),
                        new PrimaryDrawerItem().withName("About us").withIcon(R.drawable.info),
                        new PrimaryDrawerItem().withName("Contact us").withIcon(R.drawable.online_support)
                );
       // if(isLogin)
            builder.addDrawerItems(
              new PrimaryDrawerItem().withName("Orders")
            );

        builder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View v, int position, IDrawerItem drawerItem) {
                Intent login;
                switch (position) {

                    case 3:
                        MessListTabLayout fragment = new MessListTabLayout();
                        Bundle bundle3 = new Bundle();
                        fragment.setArguments(bundle3);
                        bundle3.putString("days",days.toString());
                        bundle3.putString("days2",days2.toString());
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_not, fragment, fragment.toString())
                                .addToBackStack(fragment.toString())
                                .commit();
                        break;
                    case 5:
                        if (!isLogin) {
                            LoginActivity loginfragment = new LoginActivity();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_not, loginfragment, loginfragment.toString())
                                    .addToBackStack(loginfragment.toString())
                                    .commit();
                            break;
                        } else {
                            isLogin = false;
                            Intent re = new Intent(StartActivity.this, StartActivity.class);
                            startActivity(re);
                            Toast.makeText(getApplicationContext(), "Log Out Successful", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    case 6:
                        AboutusActivity aboutfragment = new AboutusActivity();
                        Bundle bundle = new Bundle();
                        bundle.putString("aboutus", aboutus);
                        aboutfragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_not, aboutfragment, aboutfragment.toString())
                                .addToBackStack(aboutfragment.toString())
                                .commit();
                        break;
                    case 7:
                        ContactusActivity contactfragment = new ContactusActivity();
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("contactus", contactus);
                        contactfragment.setArguments(bundle2);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_not, contactfragment, contactfragment.toString())
                                .addToBackStack(contactfragment.toString())
                                .commit();
                        break;
                    case 8:
                        OrderFragment ofragment = new OrderFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_not, ofragment, ofragment.toString())
                                .addToBackStack(ofragment.toString())
                                .commit();
                        getSupportActionBar().setTitle("Orders");
                        break;
                }
                return false;
            }
        });

        drawer = builder.build();

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
