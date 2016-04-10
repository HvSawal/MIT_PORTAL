package com.example.harshvardhan.mit_portal.fragment;

import android.app.DatePickerDialog;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.example.harshvardhan.mit_portal.R;
import com.example.harshvardhan.mit_portal.activity.MainActivity;
import com.example.harshvardhan.mit_portal.helper.Util;
import com.example.harshvardhan.mit_portal.model.User;
import com.example.harshvardhan.mit_portal.model.WebSys;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LoginFragment extends Fragment {

    private AppCompatActivity mActivity;
    private EditText mDob;
    private EditText mRegNo;
    private ProgressWheel mProgressWheel;
    private ImageView mDate;
    private TextInputLayout mDobLayout;
    private TextInputLayout mRegNoLayout;
    private Button mLogin;
    private DateFormat dateFormat= DateFormat.getDateInstance();
    Calendar calendar = Calendar.getInstance();
    private User user;
    private WebSys webSys;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_login, container, false);
        user=User.getInstance(mActivity);
        webSys=WebSys.getInstance(mActivity);
        mActivity.getSupportActionBar().setTitle(mActivity.getResources().getString(R.string.title_login));
        mDob=(EditText)rowView.findViewById(R.id.login_dob);
        mRegNo=(EditText)rowView.findViewById(R.id.login_reg_no);
        mProgressWheel=(ProgressWheel)rowView.findViewById(R.id.login_progress_wheel);
        mDate=(ImageView)rowView.findViewById(R.id.login_select_date);
        mLogin=(Button)rowView.findViewById(R.id.login_submit);
        mDobLayout=(TextInputLayout)rowView.findViewById(R.id.login_dob_textinputlayout);
        mRegNoLayout=(TextInputLayout)rowView.findViewById(R.id.login_reg_no_textinputlayout);
        mDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(mActivity, d, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(validateForm()){
                    user.setRegNo(mRegNo.getText().toString().trim());
                    user.setDob(mDob.getText().toString().trim());
                    if(Util.isConnectedToInternet(mActivity)) {
                        mProgressWheel.setVisibility(View.VISIBLE);
                        new Login().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }else{
                        Util.snackbar(mActivity.findViewById(R.id.main_frame),mActivity.getResources().getString(R.string.error_no_internet));
                    }
                }
            }
        });
        return rowView;
    }

    private class Login extends AsyncTask<Void, Void, Void> {

        private boolean success;

        @Override
        protected Void doInBackground(Void... params) {
            success=webSys.downloadContent();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(success){
                user.persist();
                user.setLoggedIn(true);
                MainActivity.setScreen(mActivity,HomeFragment.getInstance(mActivity));
            }else{
                if(webSys.isInvalidCredentials()) {
                    Util.snackbar(mActivity.findViewById(R.id.main_frame), mActivity.getResources().getString(R.string.error_invalid_details));
                }else if(webSys.isWebsysDown()){
                    Util.snackbar(mActivity.findViewById(R.id.main_frame), mActivity.getResources().getString(R.string.error_websys_down));
                }else{
                    Util.snackbar(mActivity.findViewById(R.id.main_frame), mActivity.getResources().getString(R.string.error_unknown));
                }
            }
            mProgressWheel.setVisibility(View.GONE);
        }
    }

    private boolean validateForm(){
        boolean valid=true;
        String regNo=mRegNo.getText().toString().trim();
        String dob=mDob.getText().toString().trim();
        mRegNoLayout.setErrorEnabled(false);
        mDobLayout.setErrorEnabled(false);
        if(regNo.equals("")){
            mRegNoLayout.setError(mActivity.getResources().getString(R.string.error_no_reg_no));
            valid=false;
        }
        if(dob.equals("")){
            mDobLayout.setError(mActivity.getResources().getString(R.string.error_no_dob));
            valid=false;
        }
        return valid;
    }

    public static LoginFragment getInstance(AppCompatActivity activity) {
        LoginFragment fragment = new LoginFragment();
        fragment.mActivity = activity;
        return fragment;
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat,Locale.UK);
        mDob.setText(sdf.format(calendar.getTime()));
    }

}
