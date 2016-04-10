package com.example.harshvardhan.mit_portal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.example.harshvardhan.mit_portal.fragment.AcademicsFragment;
import com.example.harshvardhan.mit_portal.fragment.ProfileFragment;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    private static final int TAB_COUNT = 2;
    private AppCompatActivity mActivity;

    public TabsPagerAdapter(FragmentManager fragmentManager,AppCompatActivity activity) {
        super(fragmentManager);
        mActivity = activity;
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return ProfileFragment.getInstance(mActivity);
            case 1:
                return AcademicsFragment.getInstance(mActivity);
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

}
