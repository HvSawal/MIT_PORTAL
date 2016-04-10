package com.example.harshvardhan.mit_portal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.harshvardhan.mit_portal.R;
import com.example.harshvardhan.mit_portal.fragment.AcademicsFragment;
import com.example.harshvardhan.mit_portal.helper.Util;


public class ArchivedActivity extends AppCompatActivity {

    private AppCompatActivity mAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived);
        mAct = this;
        Intent intent = getIntent();
        String name = intent.getStringExtra(mAct.getResources().getString(R.string.intent_sem_name));
        mAct.getSupportActionBar().setTitle(name);
        try {
            FragmentTransaction fragmentTransaction = mAct.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.semester_frame, AcademicsFragment.getInstance(mAct, name));
            fragmentTransaction.commit();
        }catch(Exception e){
            e.printStackTrace();
            Util.toast(mAct.getResources().getString(R.string.error_unknown_2), mAct);
            finish();
        }
    }

}
