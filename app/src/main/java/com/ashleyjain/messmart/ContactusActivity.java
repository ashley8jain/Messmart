package com.ashleyjain.messmart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactusActivity extends android.support.v4.app.Fragment {

    TextView cn;
    String email,phone;

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
        return view;
    }
}
