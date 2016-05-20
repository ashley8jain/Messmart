package com.ashleyjain.messmart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MessListingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_listing_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mess listing");

        MessList fragment = new MessList();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_messli,fragment,fragment.toString())
                .addToBackStack(fragment.toString())
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_messlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
