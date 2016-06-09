package com.ashleyjain.messmart.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AboutusActivity extends Fragment {

    TextView abus;
    ImageView prof,prof1,prof2;
    String aboutus;
    JSONObject aboutJSON;
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("About Us");

        View view = inflater.inflate(R.layout.activity_aboutus, container, false);
        try {
            aboutJSON = new JSONObject(aboutus);
            TextView que = (TextView) view.findViewById(R.id.que);
            TextView que1 = (TextView) view.findViewById(R.id.que1);
            TextView detail = (TextView) view.findViewById(R.id.detail);
            TextView detail1 = (TextView) view.findViewById(R.id.detail1);
            JSONArray about = aboutJSON.getJSONArray("about");
            JSONArray why = about.getJSONArray(0);
            que.setText(why.getString(0));
            detail.setText(why.getString(1));
            detail.setTypeface(StartActivity.font);
            JSONArray what = about.getJSONArray(1);
            que1.setText(what.getString(0));
            detail1.setText(what.getString(1));
            detail1.setTypeface(StartActivity.font);
            JSONObject team = aboutJSON.getJSONObject("team");
            TextView title = (TextView) view.findViewById(R.id.tit);
            title.setText(team.getString("title"));
            JSONArray bodyarray = team.getJSONArray("body");
            JSONArray body = bodyarray.getJSONArray(0);
            JSONArray body1 = bodyarray.getJSONArray(1);
            JSONArray body2 = bodyarray.getJSONArray(2);
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView des = (TextView) view.findViewById(R.id.desc);
            TextView name1 = (TextView) view.findViewById(R.id.name1);
            TextView des1 = (TextView) view.findViewById(R.id.desc1);
            TextView name2 = (TextView) view.findViewById(R.id.name2);
            TextView des2 = (TextView) view.findViewById(R.id.desc2);
            name.setText(body.getString(1));
            des.setText(body.getString(2));
            name1.setText(body1.getString(1));
            des1.setText(body1.getString(2));
            name2.setText(body2.getString(1));
            des2.setText(body2.getString(2));
            des.setTypeface(StartActivity.font);
            des1.setTypeface(StartActivity.font);
            des2.setTypeface(StartActivity.font);


            prof = (ImageView) view.findViewById(R.id.prof);
            prof1 = (ImageView) view.findViewById(R.id.prof1);
            prof2 = (ImageView) view.findViewById(R.id.prof2);
            Picasso.with(getActivity()).load(StartActivity.host + body.getString(0)).into(prof);
            Picasso.with(getActivity()).load(StartActivity.host + body1.getString(0)).into(prof1);
            Picasso.with(getActivity()).load(StartActivity.host + body2.getString(0)).into(prof2);

        } catch (JSONException e) {
            e.printStackTrace();
        }



        return view;

    }
}
