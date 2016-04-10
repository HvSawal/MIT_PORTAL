package com.example.harshvardhan.mit_portal.model;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.harshvardhan.mit_portal.R;
import com.example.harshvardhan.mit_portal.helper.LocalDataStore;
import com.example.harshvardhan.mit_portal.helper.Util;


public class User {

    private static User user = null;
    private boolean loggedIn;
    private String regNo;
    private String dob;
    private AppCompatActivity activity;

    private User(){
        loggedIn=false;
        regNo="";
        dob="";
    }

    public static User getInstance(AppCompatActivity activity){
        if(user==null){
            user=new User();
        }
        user.activity=activity;
        return user;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void persist(){
        LocalDataStore.store(activity,activity.getResources().getString(R.string.preference_reg_no),regNo);
        LocalDataStore.store(activity,activity.getResources().getString(R.string.preference_dob),dob);
    }

    public void read(){
        regNo = LocalDataStore.retrieve(activity,activity.getResources().getString(R.string.preference_reg_no));
        dob = LocalDataStore.retrieve(activity,activity.getResources().getString(R.string.preference_dob));
        if(!regNo.equals("") && !dob.equals("")){
            loggedIn=true;
        }
    }

    public void logOut(){
        loggedIn=false;
        regNo="";
        dob="";
        LocalDataStore.store(activity,activity.getResources().getString(R.string.preference_academic_status),"");
        LocalDataStore.store(activity,activity.getResources().getString(R.string.preference_profile_summary),"");
        LocalDataStore.store(activity,activity.getResources().getString(R.string.preference_contact_info),"");
        LocalDataStore.store(activity,activity.getResources().getString(R.string.preference_id_numbers),"");
        WebSys.getInstance(activity).logout();
        persist();
        /*
        Util.log(String.format("Logged out -> %s\n%s\n%s\n%s\n",
                LocalDataStore.retrieve(activity, activity.getResources().getString(R.string.preference_academic_status)),
                LocalDataStore.retrieve(activity, activity.getResources().getString(R.string.preference_profile_summary)),
                LocalDataStore.retrieve(activity,activity.getResources().getString(R.string.preference_contact_info)),
                LocalDataStore.retrieve(activity,activity.getResources().getString(R.string.preference_id_numbers))
        ));
        */
    }

}
