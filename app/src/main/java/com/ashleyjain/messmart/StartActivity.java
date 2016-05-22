package com.ashleyjain.messmart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    protected Drawer drawer = null;
    AccountHeader headerResult= null;
    public static Boolean isLogin=false;
    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    Timer swipeTimer;
    int currentPage = 0;
    private Context context;
    String aboutus,contactus;

    public static String host = "http://www.messmart.com/";


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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //toolbar button handling
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
//            case R.id.material_drawer_switch:
//                drawer.openDrawer();
//                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        context = this;
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

        final ProgressDialog dialog = ProgressDialog.show(context, "", "Authenticating...", true);
        String url = host+"index.php/ajaxactions";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
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
        Volley.newRequestQueue(context).add(postRequest);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);

        final Handler handler = new Handler();

        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 4) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 3000);

        ViewpagerFragment fragment = new ViewpagerFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_not,fragment,fragment.toString())
                .addToBackStack(fragment.toString())
                .commit();

        //profile section in drawer layout
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        new ProfileDrawerItem().withName(isLogin?"Jain Mess":"Guest User").withEmail(isLogin?"Logged in":"Not Signed in"),
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
                                        Intent login = new Intent(StartActivity.this, LoginActivity.class);
                                        startActivity(login);
                                        break;
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
                        new PrimaryDrawerItem().withName(isLogin?"Logout":"Login").withIdentifier(4).withIcon(isLogin?R.drawable.logout:R.drawable.login),
                        new PrimaryDrawerItem().withName("About us").withIcon(R.drawable.info),
                        new PrimaryDrawerItem().withName("Contact us").withIcon(R.drawable.online_support)
                );

        builder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                                  @Override
                                                  public boolean onItemClick(View v, int position, IDrawerItem drawerItem) {
                                                      Intent login;
                                                      switch (position) {
                                                          case 3:
                                                              Intent intent = new Intent(StartActivity.this,MessListingPage.class);
                                                              startActivity(intent);
                                                              break;
                                                          case 5:
                                                              if(!isLogin) {
                                                                  Intent intent1 = new Intent(StartActivity.this, LoginActivity.class);
                                                                  startActivity(intent1);
                                                                  break;
                                                              }
                                                              else{
                                                                  isLogin = false;
                                                                  Intent re = new Intent(StartActivity.this,StartActivity.class);
                                                                  startActivity(re);
                                                                  Toast.makeText(getApplicationContext(), "Log Out Successful", Toast.LENGTH_LONG).show();
                                                                  finish();
                                                              }
                                                          case 6:
                                                              AboutusActivity aboutfragment = new AboutusActivity();
                                                              Bundle bundle = new Bundle();
                                                              bundle.putString("aboutus",aboutus);
                                                              aboutfragment.setArguments(bundle);
                                                              getSupportFragmentManager().beginTransaction()
                                                                      .replace(R.id.fragment_not,aboutfragment,aboutfragment.toString())
                                                                      .addToBackStack(aboutfragment.toString())
                                                                      .commit();
                                                              break;
                                                          case 7:
                                                              ContactusActivity contactfragment = new ContactusActivity();
                                                              Bundle bundle2 = new Bundle();
                                                              bundle2.putString("contactus",contactus);
                                                              contactfragment.setArguments(bundle2);
                                                              getSupportFragmentManager().beginTransaction()
                                                                      .replace(R.id.fragment_not,contactfragment,contactfragment.toString())
                                                                      .addToBackStack(contactfragment.toString())
                                                                      .commit();
                                                              break;
                                                      }
                                                      return false;
                                                  }
                                              });

        drawer = builder.build();


    }
}
