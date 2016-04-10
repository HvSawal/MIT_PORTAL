package com.example.harshvardhan.mit_portal.fragment;



import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.harshvardhan.mit_portal.R;
import com.example.harshvardhan.mit_portal.adapter.TabsPagerAdapter;

public class HomeFragment extends Fragment {

    private AppCompatActivity mActivity;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(AppCompatActivity activity) {
        HomeFragment fragment = new HomeFragment();
        fragment.mActivity = activity;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_home, container, false);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        final String[] title={mActivity.getResources().getString(R.string.title_profile), mActivity.getResources().getString(R.string.title_academics)};
        tabLayout.addTab(tabLayout.newTab().setText(title[0]));
        tabLayout.addTab(tabLayout.newTab().setText(title[1]));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        final TabsPagerAdapter adapter = new TabsPagerAdapter(mActivity.getSupportFragmentManager(), mActivity);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                viewPager.setCurrentItem(tabPosition);
                if (tabPosition < title.length) {
                    mActivity.getSupportActionBar().setTitle(title[tabPosition]);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabPosition = 0;
                viewPager.setCurrentItem(tabPosition);
                mActivity.getSupportActionBar().setTitle(title[tabPosition]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setCurrentItem(0);
        mActivity.getSupportActionBar().setTitle(title[0]);
        return rootView;
    }

}
