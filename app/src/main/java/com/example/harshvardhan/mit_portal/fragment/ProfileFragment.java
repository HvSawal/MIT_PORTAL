package com.example.harshvardhan.mit_portal.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.example.harshvardhan.mit_portal.R;
import com.example.harshvardhan.mit_portal.activity.LoginProfileActivity;
import com.example.harshvardhan.mit_portal.helper.Util;
import com.example.harshvardhan.mit_portal.model.ContactInfo;
import com.example.harshvardhan.mit_portal.model.IdNumbers;
import com.example.harshvardhan.mit_portal.model.Profile;
import com.example.harshvardhan.mit_portal.model.User;
import com.example.harshvardhan.mit_portal.model.WebSys;
import com.example.harshvardhan.mit_portal.model.WebSysHome;

public class ProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private AppCompatActivity mActivity;
    private FloatingActionButton mViewLoginProfile;
    private User user;
    private TextView mName;
    private TextView mStatus;
    private TextView mPsRegNo;//PS -> Profile Summary
    private TextView mSemester;
    private TextView mStream;
    private TextView mSection;
    private TextView mGender;
    private TextView mBirthDate;
    private TextView mJoiningYear;
    private TextView mNationality;
    private TextView mHomePhone;
    private TextView mMobilePhone;
    private TextView mEmail;
    private TextView mAddress;
    private TextView mRegNo;
    private TextView mAdmissionNo;
    private TextView mRollNo;
    private SwipeRefreshLayout mRefresh;
    private WebSys webSys;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment getInstance(AppCompatActivity activity) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.mActivity = activity;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_profile, container, false);
        user=User.getInstance(mActivity);
        webSys=WebSys.getInstance(mActivity);
        mViewLoginProfile=(FloatingActionButton)rootView.findViewById(R.id.view_login_profile);
        mStatus=(TextView)rootView.findViewById(R.id.profile_ps_status);
        mName=(TextView)rootView.findViewById(R.id.profile_ps_name);
        mPsRegNo=(TextView)rootView.findViewById(R.id.profile_ps_reg_no);
        mSemester=(TextView)rootView.findViewById(R.id.profile_ps_semester);
        mStream=(TextView)rootView.findViewById(R.id.profile_ps_stream);
        mSection=(TextView)rootView.findViewById(R.id.profile_ps_section);
        mGender=(TextView)rootView.findViewById(R.id.profile_ps_gender);
        mNationality=(TextView)rootView.findViewById(R.id.profile_ps_nationality);
        mBirthDate=(TextView)rootView.findViewById(R.id.profile_ps_dob);
        mJoiningYear=(TextView)rootView.findViewById(R.id.profile_ps_joining_year);
        mHomePhone=(TextView)rootView.findViewById(R.id.profile_ci_home_phone);
        mMobilePhone=(TextView)rootView.findViewById(R.id.profile_ci_mobile_phone);
        mEmail=(TextView)rootView.findViewById(R.id.profile_ci_email);
        mAddress=(TextView)rootView.findViewById(R.id.profile_ci_address);
        mRegNo=(TextView)rootView.findViewById(R.id.profile_id_reg);
        mAdmissionNo=(TextView)rootView.findViewById(R.id.profile_id_admission);
        mRollNo=(TextView)rootView.findViewById(R.id.profile_id_roll);
        mRefresh=(SwipeRefreshLayout)rootView.findViewById(R.id.refresh_login_profile);
        mRefresh.setOnRefreshListener(this);
        mRefresh.setColorSchemeResources(
                R.color.materialize_css_green,
                R.color.materialize_css_red,
                R.color.materialize_css_blue,
                R.color.materialize_css_yellow
        );
        mViewLoginProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user.logOut();
                //MainActivity.setScreen(mActivity,LoginFragment.getInstance(mActivity));
                Intent intent = new Intent(mActivity, LoginProfileActivity.class);
                intent.putExtra(mActivity.getResources().getString(R.string.intent_reg_no), user.getRegNo());
                intent.putExtra(mActivity.getResources().getString(R.string.intent_dob), user.getDob());
                mActivity.startActivity(intent);
            }
        });
        webSys.read();
        recreateView();
        return rootView;
    }

    private void recreateView(){
        try {
            WebSysHome webSysHome = webSys.getWebSysHome();
            Profile profile = webSysHome.getProfile();
            ContactInfo contactInfo = webSysHome.getContactInfo();
            IdNumbers idNumbers = webSysHome.getIdNumbers();
            mStatus.setText(profile.getStatus());
            mName.setText(profile.getName());
            mPsRegNo.setText(profile.getRegistrationNumber());
            mSemester.setText(profile.getSemester());
            mStream.setText(profile.getStream());
            mSection.setText(profile.getSection());
            mGender.setText(profile.getGender());
            mNationality.setText(profile.getNationality());
            mBirthDate.setText(profile.getBirthDate());
            mJoiningYear.setText(profile.getJoiningYear());
            mRollNo.setText(idNumbers.getRollNumber());
            mRegNo.setText(idNumbers.getRegistrationNumber());
            mAdmissionNo.setText(idNumbers.getAdmissionNumber());
            mHomePhone.setText(contactInfo.getHomePhone());
            mMobilePhone.setText(contactInfo.getMobile());
            mEmail.setText(contactInfo.getEmailAddress());
            mAddress.setText(contactInfo.getPermanentAddress());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        if (!Util.isConnectedToInternet(mActivity)) {
            mRefresh.setRefreshing(false);
            Util.snackbar(mViewLoginProfile, mActivity.getResources().getString(R.string.error_no_internet));
        } else {
            mRefresh.setRefreshing(true);
            new FetchContents().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private class FetchContents extends AsyncTask<Void, Void, Void> {

        private boolean success;

        @Override
        protected Void doInBackground(Void... params) {
            webSys.fetchArchivedSemesters(false);
            success=webSys.downloadContent();
            try{
                webSys.read();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(success){
                recreateView();
            }else {
                if (webSys.isInvalidCredentials()) {
                    Util.snackbar(mViewLoginProfile, mActivity.getResources().getString(R.string.error_invalid_details));
                } else if (webSys.isWebsysDown()) {
                    Util.snackbar(mViewLoginProfile, mActivity.getResources().getString(R.string.error_websys_down));
                } else {
                    Util.snackbar(mViewLoginProfile, mActivity.getResources().getString(R.string.error_unknown));
                }
            }
            mRefresh.setRefreshing(false);
        }
    }
}
