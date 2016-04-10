package com.example.harshvardhan.mit_portal.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.example.harshvardhan.mit_portal.R;
import com.example.harshvardhan.mit_portal.adapter.SemesterAdapter;
import com.example.harshvardhan.mit_portal.helper.Util;
import com.example.harshvardhan.mit_portal.model.Semester;
import com.example.harshvardhan.mit_portal.model.Subject;
import com.example.harshvardhan.mit_portal.model.WebSys;

import java.util.ArrayList;

public class ArchivesActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private AppCompatActivity mActivity;
    private SwipeRefreshLayout mRefresh;
    private View mView;
    private WebSys webSys;
    private FloatingActionButton mCgpa;
    private ListView mList;
    private ArrayList<Semester> mSemesters;
    private SemesterAdapter mAdapter;
    private LinearLayout mNoArchivedSemesters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archives);
        mActivity = this;
        webSys = WebSys.getInstance(mActivity);
        mSemesters = new ArrayList<>();
        mView = (View)findViewById(R.id.archived_sems_frame_layout);
        mRefresh = (SwipeRefreshLayout)findViewById(R.id.refresh_acrchived_semesters);
        mList = (ListView)findViewById(R.id.list_archived_semesters);
        mList.addFooterView(mActivity.getLayoutInflater().inflate(R.layout.footer_academic_status_list, null));
        mNoArchivedSemesters=(LinearLayout)findViewById(R.id.no_archived_semesters);
        mCgpa = (FloatingActionButton)findViewById(R.id.calculate_cgpa);
        mRefresh.setOnRefreshListener(this);
        mRefresh.setColorSchemeResources(
                R.color.materialize_css_green,
                R.color.materialize_css_red,
                R.color.materialize_css_blue,
                R.color.materialize_css_yellow
        );
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sem = ((TextView) view.findViewById(R.id.semester_title)).getText().toString().trim();
                Intent intent = new Intent(mActivity, ArchivedActivity.class);
                intent.putExtra(mActivity.getResources().getString(R.string.intent_sem_name), sem);
                mActivity.startActivity(intent);
            }
        });
        mCgpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ArrayList<Semester> archivedSemesters = webSys.getWebSysAcademics().getAllSemesters();
                    double cgpa = 0.0, credits = 0.0;
                    for(Semester sem:archivedSemesters){
                        if(sem.isArchived()){
                            double gpa = Double.parseDouble(sem.getGpa());
                            double credit = 0;
                            for(Subject sub:sem.getAllSubjects()){
                                credit += Double.parseDouble(sub.getCredits());
                            }
                            cgpa += (gpa*credit);
                            credits += credit;
                        }
                    }
                    String CGPA="";
                    if(credits!=0) {
                        cgpa = cgpa / credits;
                        CGPA = String.format("%.2f", cgpa);
                        Util.log("CGPA -> "+CGPA);
                        Util.showAlertDialog(mActivity, mActivity.getResources().getString(R.string.title_cgpa), CGPA);
                    }else{
                        Util.snackbar(mCgpa, mActivity.getResources().getString(R.string.error_invalid_cgpa));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Util.snackbar(mCgpa, mActivity.getResources().getString(R.string.error_invalid_cgpa));
                }
            }
        });
        mAdapter = new SemesterAdapter(mActivity,mSemesters);
        mList.setAdapter(mAdapter);
        webSysRead();
        mAdapter.notifyDataSetChanged();
        checkForEmptyList();
    }

    private void checkForEmptyList(){
        if(mSemesters.size()==0){
            mNoArchivedSemesters.setVisibility(View.VISIBLE);
        }else{
            mNoArchivedSemesters.setVisibility(View.GONE);
        }
    }

    private void webSysRead(){
        try{
            webSys.read();
            mSemesters.clear();
            ArrayList<Semester> sems = webSys.getWebSysAcademics().getAllSemesters();
            for(Semester s:sems){
                if(s.isArchived()){
                    mSemesters.add(s);
                }
            }
            for(int i=0;i<mSemesters.size();i++){
                for(int j=0;j<mSemesters.size()-1-i;j++){
                    if(mSemesters.get(j).getName().compareTo(mSemesters.get(j+1).getName())<0){
                        Semester temp = mSemesters.get(j);
                        mSemesters.set(j,mSemesters.get(j+1));
                        mSemesters.set(j+1,temp);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        if (!Util.isConnectedToInternet(mActivity)) {
            mRefresh.setRefreshing(false);
            Util.snackbar(mCgpa, mActivity.getResources().getString(R.string.error_no_internet));
        } else {
            mRefresh.setRefreshing(true);
            new FetchContents().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private class FetchContents extends AsyncTask<Void, Void, Void> {

        private boolean success;

        @Override
        protected Void doInBackground(Void... params) {
            webSys.fetchArchivedSemesters(true);
            success = webSys.downloadContent();
            webSysRead();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            webSys.fetchArchivedSemesters(false);
            checkForEmptyList();
            if(success){
                mAdapter.notifyDataSetChanged();
            }else {
                if (webSys.isInvalidCredentials()) {
                    Util.snackbar(mCgpa, mActivity.getResources().getString(R.string.error_invalid_details));
                } else if (webSys.isWebsysDown()) {
                    Util.snackbar(mCgpa, mActivity.getResources().getString(R.string.error_websys_down));
                } else {
                    Util.snackbar(mCgpa, mActivity.getResources().getString(R.string.error_unknown));
                }
            }
            mRefresh.setRefreshing(false);
        }
    }
}
