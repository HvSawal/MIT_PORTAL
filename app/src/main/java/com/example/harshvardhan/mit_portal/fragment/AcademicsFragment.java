package com.example.harshvardhan.mit_portal.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.example.harshvardhan.mit_portal.R;
import com.example.harshvardhan.mit_portal.activity.ArchivesActivity;
import com.example.harshvardhan.mit_portal.adapter.SubjectAdapter;
import com.example.harshvardhan.mit_portal.helper.Util;
import com.example.harshvardhan.mit_portal.model.Semester;
import com.example.harshvardhan.mit_portal.model.Subject;
import com.example.harshvardhan.mit_portal.model.WebSys;
import com.example.harshvardhan.mit_portal.model.WebSysAcademics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AcademicsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private AppCompatActivity mActivity;
    private String semName;
    private FloatingActionButton mViewArchives;
    private TextView mName;
    private TextView mSession;
    private TextView mCredits;
    private TextView mGpa;
    private CardView mCardCredit;
    private CardView mCardGpa;
    private WebSys webSys;
    private SwipeRefreshLayout mRefresh;
    private ListView mList;
    private ArrayList<Subject> mSubjects;
    private SubjectAdapter mSubjectAdapter;

    public AcademicsFragment() {
        // Required empty public constructor
    }

    public static AcademicsFragment getInstance(AppCompatActivity activity) {
        AcademicsFragment fragment = new AcademicsFragment();
        fragment.mActivity = activity;
        fragment.semName="";
        return fragment;
    }

    public static AcademicsFragment getInstance(AppCompatActivity activity,String semName) {
        AcademicsFragment fragment = new AcademicsFragment();
        fragment.mActivity = activity;
        fragment.semName=semName;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_academics, container, false);
        View listHeader = mActivity.getLayoutInflater().inflate(R.layout.header_academic_status_list, null);
        View listFooter = mActivity.getLayoutInflater().inflate(R.layout.footer_academic_status_list, null);
        webSys=WebSys.getInstance(mActivity);
        mSubjects=new ArrayList<>();
        mSubjectAdapter=new SubjectAdapter(mActivity,mSubjects);
        mList=(ListView)rootView.findViewById(R.id.list_latest_sem_subjects);
        mName=(TextView)listHeader.findViewById(R.id.academic_status_current_semester_name);
        mCredits=(TextView)listHeader.findViewById(R.id.academic_status_current_semester_credits);
        mGpa=(TextView)listHeader.findViewById(R.id.academic_status_current_semester_gpa);
        mSession=(TextView)listHeader.findViewById(R.id.academic_status_current_semester_session);
        mCardCredit=(CardView)listHeader.findViewById(R.id.academic_status_card_current_semester_credits);
        mCardGpa=(CardView)listHeader.findViewById(R.id.academic_status_card_current_semester_gpa);
        mViewArchives=(FloatingActionButton)rootView.findViewById(R.id.academic_archives);
        mRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_academic_status);
        mRefresh.setOnRefreshListener(this);
        mRefresh.setColorSchemeResources(
                R.color.materialize_css_green,
                R.color.materialize_css_red,
                R.color.materialize_css_blue,
                R.color.materialize_css_yellow
        );
        if(isLatestSem()) {
            mViewArchives.setVisibility(View.VISIBLE);
            mViewArchives.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    startActivity(new Intent(mActivity, ArchivesActivity.class));
                }
            });
        }else{
            mViewArchives.setVisibility(View.GONE);
        }
        mList.addHeaderView(listHeader);
        mList.addFooterView(listFooter);
        mList.setAdapter(mSubjectAdapter);
        webSysRead();
        mSubjectAdapter.notifyDataSetChanged();
        recreateView();
        return rootView;
    }

    private boolean isLatestSem(){
        return semName.equals("");
    }

    private void recreateView(){
        try{
            WebSysAcademics webSysAcademics=webSys.getWebSysAcademics();
            Semester semester=null;
            if(isLatestSem()){
                semester=webSysAcademics.getSemesters().get(webSysAcademics.getCurrentSemester());
            }else{
                semester=webSysAcademics.getSemesters().get(semName);
            }
            mName.setText(semester.getName());
            mSession.setText(semester.getSession());
            if(semester.isArchived()){
                mCardGpa.setVisibility(View.VISIBLE);
                mCardCredit.setVisibility(View.VISIBLE);
                mCredits.setText(semester.getCredits());
                mGpa.setText(semester.getGpa());
            }else{
                mCardGpa.setVisibility(View.GONE);
                mCardCredit.setVisibility(View.GONE);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        if(!isLatestSem()){
            mRefresh.setRefreshing(false);
            Util.snackbar(mViewArchives,mActivity.getResources().getString(R.string.text_no_download_for_archived_sem));
            return;
        }
        if (!Util.isConnectedToInternet(mActivity)) {
            mRefresh.setRefreshing(false);
            Util.snackbar(mViewArchives, mActivity.getResources().getString(R.string.error_no_internet));
        } else {
            mRefresh.setRefreshing(true);
            new FetchContents().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private void webSysRead(){
        try{
            webSys.read();
            mSubjects.clear();
            ArrayList<Subject> subs=isLatestSem()?webSys.getWebSysAcademics().getSemesters().get(webSys.getWebSysAcademics().getCurrentSemester()).getAllSubjects():webSys.getWebSysAcademics().getSemesters().get(semName).getAllSubjects();
            for(Subject s:subs){
                mSubjects.add(s);
            }
            for(int i=0;i<mSubjects.size();i++){
                for(int j=0;j<mSubjects.size()-1-i;j++){
                    if(mSubjects.get(j).getName().compareTo(mSubjects.get(j+1).getName())>0){
                        Subject temp=mSubjects.get(j);
                        mSubjects.set(j,mSubjects.get(j+1));
                        mSubjects.set(j+1,temp);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private class FetchContents extends AsyncTask<Void, Void, Void> {

        private boolean success;

        @Override
        protected Void doInBackground(Void... params) {
            webSys.fetchArchivedSemesters(false);
            success=webSys.downloadContent();
            webSysRead();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(success){
                recreateView();
                mSubjectAdapter.notifyDataSetChanged();
            }else {
                if (webSys.isInvalidCredentials()) {
                    Util.snackbar(mViewArchives, mActivity.getResources().getString(R.string.error_invalid_details));
                } else if (webSys.isWebsysDown()) {
                    Util.snackbar(mViewArchives, mActivity.getResources().getString(R.string.error_websys_down));
                } else {
                    Util.snackbar(mViewArchives, mActivity.getResources().getString(R.string.error_unknown));
                }
            }
            mRefresh.setRefreshing(false);
        }
    }

}
