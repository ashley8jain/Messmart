package com.ashleyjain.messmart.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ashleyjain.messmart.Fragment.List.MessList;

/**
 * Created by ashleyjain on 21/05/16.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    int NumOfTabs;
    String pd;
    public PagerAdapter(FragmentManager fm, int NumOfTabs,String particularday) {
        super(fm);
        this.NumOfTabs = NumOfTabs;
        pd = particularday;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            MessList frag = new MessList("l",pd);
            return frag;
        }
        if(position == 1){
            MessList frag1 = new MessList("d",pd);
            return frag1;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NumOfTabs;
    }

    @Override
    public int getItemPosition(Object object) {
        //don't return POSITION_NONE, avoid fragment recreation.
        return POSITION_NONE;
    }
}
