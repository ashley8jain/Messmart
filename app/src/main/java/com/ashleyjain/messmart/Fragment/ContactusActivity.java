package com.ashleyjain.messmart.Fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashleyjain.messmart.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactusActivity extends android.support.v4.app.Fragment {

    TextView cn;
    String email,phone;

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("MesSmart");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String contactus = getArguments().getString("contactus");
        try {
            JSONObject contactusobject = new JSONObject(contactus);
            email = contactusobject.getString("email");
            phone = contactusobject.getString("phone");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_contactus, container, false);
        cn = (TextView) view.findViewById(R.id.textView2);
        //cn.setText("free");
        cn.setText("E-mail:"+email+"\n"+"Phone:"+phone);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Contact Us");
        return view;
    }
}
