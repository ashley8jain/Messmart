package com.ashleyjain.messmart.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashleyjain.messmart.MessListTabLayout;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;


public class ViewpagerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TranslateAnimation moveToptoBottom = new TranslateAnimation(0,0, -1000, 0);
        moveToptoBottom.setDuration(1000);
        moveToptoBottom.setFillAfter(true);

        TranslateAnimation moveBottomtoTop = new TranslateAnimation(0,0,1000,0);
        moveBottomtoTop.setDuration(1000);
        moveBottomtoTop.setFillAfter(true);

        TranslateAnimation moveLefttoRight = new TranslateAnimation(-900,0, 0, 0);
        moveLefttoRight.setDuration(2000);
        moveLefttoRight.setFillAfter(true);

        TextView welcome = (TextView) view.findViewById(R.id.welcome);
        TextView place = (TextView) view.findViewById(R.id.place);
        welcome.setAnimation(moveToptoBottom);
        place.setAnimation(moveBottomtoTop);

        Button bookmeal = (Button) view.findViewById(R.id.bookmeal);
        bookmeal.setAnimation(moveLefttoRight);
        bookmeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessListTabLayout fragment = new MessListTabLayout("", "", false);
                Bundle bundle3 = new Bundle();
                bundle3.putString("days", StartActivity.days.toString());
                bundle3.putString("days2", StartActivity.days2.toString());
                fragment.setArguments(bundle3);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_not, fragment, fragment.toString())
                        .addToBackStack(fragment.toString())
                        .commit();
            }

        });

        ImageView imgFacebook = (ImageView)view.findViewById(R.id.imageViewFacebook);
        imgFacebook.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.fb.com"));
                startActivity(intent);
            }
        });

        ImageView imgGoogle = (ImageView)view.findViewById(R.id.imageViewGoogle);
        imgGoogle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://plus.google.com/"));
                startActivity(intent);
            }
        });
    }

}
