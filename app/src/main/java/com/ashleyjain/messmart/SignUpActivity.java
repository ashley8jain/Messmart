package com.ashleyjain.messmart;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    EditText newmobile;
    Button sendotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Typeface font = Typeface.createFromAsset(getAssets(),"YuppySC-Regular.ttf");

        newmobile = (EditText) findViewById(R.id.newmob);
        newmobile.setTypeface(font);
        newmobile.addTextChangedListener(new checkError(newmobile));

        sendotp = (Button) findViewById(R.id.sendotp);
        sendotp.setTypeface(font);

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
