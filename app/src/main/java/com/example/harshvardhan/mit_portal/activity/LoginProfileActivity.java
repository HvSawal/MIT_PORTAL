package com.example.harshvardhan.mit_portal.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.harshvardhan.mit_portal.R;
import com.example.harshvardhan.mit_portal.model.User;

public class LoginProfileActivity extends AppCompatActivity {

    private TextView mRegNo;
    private TextView mDob;
    private Button mLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        String regNo=intent.getStringExtra(getResources().getString(R.string.intent_reg_no));
        String dob=intent.getStringExtra(getResources().getString(R.string.intent_dob));
        mRegNo=(TextView)findViewById(R.id.login_profile_reg_no);
        mDob=(TextView)findViewById(R.id.login_profile_dob);
        mLogout=(Button)findViewById(R.id.login_profile_logout);
        mRegNo.setText(regNo);
        mDob.setText(dob);
        final AppCompatActivity activity=this;
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.getInstance(activity).logOut();
                //activity.finish();
                //System.exit(0);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}
