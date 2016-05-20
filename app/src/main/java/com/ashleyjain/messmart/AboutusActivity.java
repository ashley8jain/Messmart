package com.ashleyjain.messmart;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutusActivity extends android.support.v4.app.Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Picasso.with(getActivity()).load(StartActivity.host+"photo/chef2.jpg").into(iv);
        View view = inflater.inflate(R.layout.activity_aboutus, container, false);
        abus = (TextView) view.findViewById(R.id.textView2);
        //wv = (WebView) view.findViewById(R.id.webView);
        String html = "<h1 style='color:red;' onclick='document.getElementById(\"timepass\").style.display=\"none\";' >Mohit Saini</h1> <img id='timepass' style='width:300px;' src='http://192.168.0.102/mess/photo/chef2.jpg' />";
        String mime = "text/html";
        String encoding = "utf-8";
        //wv.getSettings().setJavaScriptEnabled(true);
        //wv.loadDataWithBaseURL(null, html, mime, encoding, null);
        abus.setText(Html.fromHtml(aboutus));

        return view;
    }
}
