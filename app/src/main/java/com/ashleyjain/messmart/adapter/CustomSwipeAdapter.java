package com.ashleyjain.messmart.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ashleyjain.messmart.R;

/**
 * Created by ashleyjain on 18/05/16.
 */
public class CustomSwipeAdapter extends PagerAdapter {

    private int[] image_resources = {R.drawable.slider1,R.drawable.layout,R.drawable.hww3_t,R.drawable.hww1_t};
    private Context context;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context context){
        this.context = context;
    }


    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);

        ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);
        imageView.setImageResource(image_resources[position]);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
