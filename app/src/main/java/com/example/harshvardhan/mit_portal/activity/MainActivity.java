package com.example.harshvardhan.mit_portal.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.harshvardhan.mit_portal.R;
import com.example.harshvardhan.mit_portal.fragment.HomeFragment;
import com.example.harshvardhan.mit_portal.fragment.LoginFragment;
import com.example.harshvardhan.mit_portal.helper.Util;
import com.example.harshvardhan.mit_portal.model.User;


public class MainActivity extends AppCompatActivity {

    private User user;
    private AppCompatActivity mActivity;
    private boolean backPressed=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity=this;
        user=User.getInstance(mActivity);
        user.read();
        if(user.isLoggedIn()){
            setScreen(mActivity,HomeFragment.getInstance(mActivity));
        }else{
            setScreen(mActivity,LoginFragment.getInstance(mActivity));
        }
    }

    public static void setScreen(AppCompatActivity activity,Fragment fragment) {
        try {
            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragment);
            fragmentTransaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_about:
                Util.showAlertDialog(mActivity,mActivity.getResources().getString(R.string.title_about),mActivity.getResources().getString(R.string.text_about));
                return true;
            case R.id.action_disclaimer:
                Util.showAlertDialog(mActivity,mActivity.getResources().getString(R.string.title_disclaimer),mActivity.getResources().getString(R.string.text_disclaimer));
                return true;
            case R.id.action_exit:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (backPressed) {
            super.onBackPressed();
            return;
        }
        this.backPressed = true;
        Util.toast(mActivity.getResources().getString(R.string.text_back_pressed),mActivity);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                backPressed = false;
            }
        }, mActivity.getResources().getInteger(R.integer.back_press_time));
    }
}
