package com.happy.radiostation.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class MainAdapter extends SmartFragmentStatePagerAdapter {

    private final static int NUM_ITEMS = 2;
    private final int RADIO_PAGE = 0;
    private final int CHANEL_PAGE = 1;

    public MainAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case RADIO_PAGE:
                return RadioFragment.newInstance();
            case CHANEL_PAGE:
                return ChanelListFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
