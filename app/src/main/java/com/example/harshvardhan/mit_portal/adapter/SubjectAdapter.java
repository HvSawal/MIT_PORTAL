package com.example.harshvardhan.mit_portal.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.harshvardhan.mit_portal.R;
import com.example.harshvardhan.mit_portal.model.Subject;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SubjectAdapter extends ArrayAdapter<Subject> {

    private static final int LAYOUT=R.layout.card_subject;
    private AppCompatActivity mActivity;
    private ArrayList<Subject> mList;
    private Subject mSubject;
    private TextView mName;
    private TextView mCode;
    private TextView mCredits;
    private TextView mSection;
    private TextView mStatus;
    private TextView mOnlyExam;
    private TextView mClassesTaken;
    private TextView mClassesAttended;
    private TextView mClassesAbsent;
    private TextView mAttendancePercentage;
    private TextView mAttendanceUpdated;
    private TextView mGrade;
    private TextView mSessionCleared;
    private TextView mIa1;
    private TextView mIa2;
    private TextView mIa3;
    private TextView mOption;
    private TableRow mCodeRow;
    private TableRow mCreditsRow;
    private TableRow mSectionRow;
    private TableRow mStatusRow;
    private TableRow mOnlyExamRow;
    private TableRow mClassesTakenRow;
    private TableRow mClassesAttendedRow;
    private TableRow mClassesAbsentRow;
    private TableRow mAttendancePercentageRow;
    private TableRow mAttendanceUpdatedRow;
    private TableRow mGradeRow;
    private TableRow mSessionClearedRow;
    private TableRow mIa1Row;
    private TableRow mIa2Row;
    private TableRow mIa3Row;
    private TableRow mOptionRow;

    public SubjectAdapter(AppCompatActivity activity, ArrayList<Subject> list) {
        super(activity, LAYOUT, list);
        mActivity = activity;
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(LAYOUT, parent, false);
        mSubject=mList.get(position);
        mName=(TextView)rowView.findViewById(R.id.subject_title);
        mCode=(TextView)rowView.findViewById(R.id.subject_code);
        mCredits=(TextView)rowView.findViewById(R.id.subject_credits);
        mSection=(TextView)rowView.findViewById(R.id.subject_section);
        mStatus=(TextView)rowView.findViewById(R.id.subject_status);
        mOnlyExam=(TextView)rowView.findViewById(R.id.subject_only_exam);
        mClassesTaken=(TextView)rowView.findViewById(R.id.subject_classes_taken);
        mClassesAttended=(TextView)rowView.findViewById(R.id.subject_classes_attended);
        mClassesAbsent=(TextView)rowView.findViewById(R.id.subject_classes_absent);
        mAttendancePercentage=(TextView)rowView.findViewById(R.id.subject_attendance_percentage);
        mAttendanceUpdated=(TextView)rowView.findViewById(R.id.subject_attendance_updated);
        mGrade=(TextView)rowView.findViewById(R.id.subject_grade);
        mSessionCleared=(TextView)rowView.findViewById(R.id.subject_session);
        mIa1=(TextView)rowView.findViewById(R.id.subject_ia1);
        mIa2=(TextView)rowView.findViewById(R.id.subject_ia2);
        mIa3=(TextView)rowView.findViewById(R.id.subject_ia3);
        mOption=(TextView)rowView.findViewById(R.id.subject_option);
        mCodeRow=(TableRow)rowView.findViewById(R.id.subject_code_row);
        mCreditsRow=(TableRow)rowView.findViewById(R.id.subject_credits_row);
        mSectionRow=(TableRow)rowView.findViewById(R.id.subject_section_row);
        mStatusRow=(TableRow)rowView.findViewById(R.id.subject_status_row);
        mOnlyExamRow=(TableRow)rowView.findViewById(R.id.subject_only_exam_row);
        mClassesTakenRow=(TableRow)rowView.findViewById(R.id.subject_classes_taken_row);
        mClassesAttendedRow=(TableRow)rowView.findViewById(R.id.subject_classes_attended_row);
        mClassesAbsentRow=(TableRow)rowView.findViewById(R.id.subject_classes_absent_row);
        mAttendancePercentageRow=(TableRow)rowView.findViewById(R.id.subject_attendance_percentage_row);
        mAttendanceUpdatedRow=(TableRow)rowView.findViewById(R.id.subject_attendance_updated_row);
        mGradeRow=(TableRow)rowView.findViewById(R.id.subject_grade_row);
        mSessionClearedRow=(TableRow)rowView.findViewById(R.id.subject_session_row);
        mIa1Row=(TableRow)rowView.findViewById(R.id.subject_ia1_row);
        mIa2Row=(TableRow)rowView.findViewById(R.id.subject_ia2_row);
        mIa3Row=(TableRow)rowView.findViewById(R.id.subject_ia3_row);
        mOptionRow=(TableRow)rowView.findViewById(R.id.subject_option_row);
        recreateView();
        return rowView;
    }

    private void recreateView(){
        try {
            mName.setText(mSubject.getName());
            TextView[] view={mCode,mCredits,mSection,mStatus,mOnlyExam,mClassesTaken,mClassesAttended,mClassesAbsent,mAttendancePercentage,mAttendanceUpdated,mIa1,mIa2,mIa3,mGrade,mSessionCleared,mOption};
            View[] container={mCodeRow,mCreditsRow,mSectionRow,mStatusRow,mOnlyExamRow,mClassesTakenRow,mClassesAttendedRow,mClassesAbsentRow,mAttendancePercentageRow,mAttendanceUpdatedRow,mIa1Row,mIa2Row,mIa3Row,mGradeRow,mSessionClearedRow,mOptionRow};
            String[] val={mSubject.getCode(),mSubject.getCredits(),mSubject.getSection(),mSubject.getStatus(),mSubject.getOnlyExam(),mSubject.getClasses(),mSubject.getAttended(),mSubject.getAbsent(),mSubject.getAttendacePercentage(),mSubject.getAttendaceUpdated(),mSubject.getInternalAssessment1(),mSubject.getInternalAssessment2(),mSubject.getInternalAssessment3(),mSubject.getGrade(),mSubject.getSession(),mSubject.getOption()};
            for(int i=0;i<view.length;i++){
                setValue(view[i],container[i],val[i]);
            }
            if(mAttendancePercentageRow.getVisibility()!=View.GONE){
                try{
                    if(Integer.parseInt(mSubject.getAttendacePercentage())<mActivity.getResources().getInteger(R.integer.attendance_threshhold)){
                        mAttendancePercentage.setTextColor(mActivity.getResources().getColor(R.color.attendance_shortage));
                    }else{
                        mAttendancePercentage.setTextColor(mActivity.getResources().getColor(R.color.text_primary));
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            if(mGradeRow.getVisibility()!=View.GONE){
                try{
                    if(mSubject.getGrade().equals(mActivity.getResources().getString(R.string.grade_fail)) || mSubject.getGrade().equals(mActivity.getResources().getString(R.string.grade_incomplete))){
                        mGrade.setTextColor(mActivity.getResources().getColor(R.color.grade_fail_or_incomplete));
                    }else{
                        mGrade.setTextColor(mActivity.getResources().getColor(R.color.text_primary));
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setValue(TextView view,View container,String val){
        if(val!=null){
            val=val.trim();
        }
        if(val==null || val.equals("") || val.length()==0 ){
            container.setVisibility(View.GONE);
        }else{
            container.setVisibility(View.VISIBLE);
            view.setText(val);
            //Util.log("Setting -> " + val);
        }
    }
}
