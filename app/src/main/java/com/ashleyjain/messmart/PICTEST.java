package com.ashleyjain.messmart;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PICTEST extends AppCompatActivity {

    ImageView im;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictest);

        im = (ImageView) findViewById(R.id.imageView5);
        Picasso.with(this).load(StartActivity.host+"photo/chef2.jpg").into(im);

    }

}
