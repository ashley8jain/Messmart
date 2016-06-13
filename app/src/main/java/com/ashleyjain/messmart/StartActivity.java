package com.ashleyjain.messmart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ashleyjain.messmart.Fragment.ViewpagerFragment;
import com.ashleyjain.messmart.function.KeyboardDown;
import com.ashleyjain.messmart.function.StringRequestCookies;
import com.ashleyjain.messmart.function.drawer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    public static String loginname,logintype;
    private Context context;
    public static String aboutus,contactus;
    public static JSONArray days,days2;
    public static JSONObject errorcode;
    public static String loginid;

    public static JSONArray tabs;
    public static JSONObject tab_map;

    public static String host = "http://192.168.0.111/mess/";
    public static String url = StartActivity.host+"index.php/ajaxactions";

    private static final String SET_COOKIE_KEY = "set-cookie";
    private static final String COOKIE_KEY = "cookie";
    private static final String SESSION_COOKIE = "PHPSESSID";
    public static Typeface font;
    private static StartActivity _instance;
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;

    public static StartActivity get() {
        return _instance;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //KeyboardDown.keyboardDown();
        switch(item.getItemId()){
            case R.id.home:
                for(int i=0;i<getSupportFragmentManager().getBackStackEntryCount();i++)
                    getSupportFragmentManager().popBackStack();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
//        try{
//            KeyboardDown.keyboardDown();
//        }
//        catch (Exception e){
//            System.out.println("hi");
//        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.drawer.isDrawerOpen()) {
            drawer.drawer.closeDrawer();
        } else {
            super.onBackPressed();
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
        font = Typeface.createFromAsset(this.getAssets(),"YuppySC-Regular.ttf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MesSmart");

        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.drawer.openDrawer();
                //KeyboardDown.keyboardDown();
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
            new drawer().rebuild();


            ViewpagerFragment fragment = new ViewpagerFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_not, fragment, fragment.toString())
                    .commit();


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

    public void popStack(){
        for(int i=0;i<getSupportFragmentManager().getBackStackEntryCount();i++)
            getSupportFragmentManager().popBackStack();
    }

    public void logoutapi(){
        final ProgressDialog dialog = ProgressDialog.show(context, "", "Logging out....", true);

        StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        Toast.makeText(getApplicationContext(), "Log Out Successful", Toast.LENGTH_LONG).show();
                        new drawer().rebuild();
                        dialog.dismiss();
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
                params.put("action", "logout");
                System.out.println(params);
                return params;
            }
        };

        // add it to the RequestQueue
        getRequestQueue().add(postRequest);
    }

}
