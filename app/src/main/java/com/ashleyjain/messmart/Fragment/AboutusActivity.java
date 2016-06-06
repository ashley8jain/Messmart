package com.ashleyjain.messmart.Fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashleyjain.messmart.R;

public class AboutusActivity extends Fragment {

    TextView abus;
    //ImageView iv;
    String aboutus;
    //WebView wv;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aboutus = getArguments().getString("aboutus");

    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("MesSmart");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_aboutus, container, false);
        abus = (TextView) view.findViewById(R.id.textView2);
        abus.setText(Html.fromHtml(aboutus));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("About Us");
        return view;

    }
}
