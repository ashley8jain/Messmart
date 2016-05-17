package com.ashleyjain.messmart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

public class StartActivity extends AppCompatActivity {

    protected Drawer drawer = null;
    AccountHeader headerResult= null;
    public static Boolean login=false;

    @Override
    protected void onRestart() {
        super.onRestart();
        if(login){
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

        ViewpagerFragment fragment = new ViewpagerFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_not,fragment,fragment.toString())
                .addToBackStack(fragment.toString())
                .commit();

        //profile section in drawer layout
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        new ProfileDrawerItem().withName(login?"Jain Mess":"Guest User").withEmail(login?"Logged in":"Not Signed in"),
                        new ProfileSettingDrawerItem()
                                .withName(login?"Logout":"Login")
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
                                    if(!login) {
                                        Intent login = new Intent(StartActivity.this, LoginActivity.class);
                                        startActivity(login);
                                        break;
                                    }
                                    else{
                                        login = false;
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
                        new PrimaryDrawerItem().withName(login?"Logout":"Login").withIdentifier(4).withIcon(login?R.drawable.logout:R.drawable.login),
                        new SectionDrawerItem().withName("Help"),
                        new PrimaryDrawerItem().withName("About us").withIcon(R.drawable.info),
                        new PrimaryDrawerItem().withName("Contact us").withIcon(R.drawable.online_support)
                );

        builder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                                  @Override
                                                  public boolean onItemClick(View v, int position, IDrawerItem drawerItem) {
                                                      switch (position) {
                                                          case 5:
                                                              if(!login) {
                                                                  Intent login = new Intent(StartActivity.this, LoginActivity.class);
                                                                  startActivity(login);
                                                                  break;
                                                              }
                                                              else{
                                                                  login = false;
                                                                  Intent re = new Intent(StartActivity.this,StartActivity.class);
                                                                  startActivity(re);
                                                                  Toast.makeText(getApplicationContext(), "Log Out Successful", Toast.LENGTH_LONG).show();
                                                                  finish();
                                                              }
                                                      }
                                                      return false;
                                                  }
                                              });

        drawer = builder.build();

    }
}
