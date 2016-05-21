package com.ashleyjain.messmart;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ashleyjain on 21/05/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    int NumOfTabs;
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.NumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            MessList frag = new MessList();
            return frag;
        }
        if(position == 1){
            MessList frag1 = new MessList();
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
