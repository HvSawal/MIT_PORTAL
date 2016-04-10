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
import com.example.harshvardhan.mit_portal.model.Semester;
import com.example.harshvardhan.mit_portal.model.Subject;

import java.util.ArrayList;

public class SemesterAdapter extends ArrayAdapter<Semester> {

    private static final int LAYOUT= R.layout.card_semester;
    private AppCompatActivity mActivity;
    private Semester mSemester;
    private ArrayList<Semester> mList;
    private TextView mTitle;
    private TextView mSession;
    private TextView mGpa;
    private TextView mCredits;

    public SemesterAdapter(AppCompatActivity activity, ArrayList<Semester> list) {
        super(activity, LAYOUT, list);
        mActivity = activity;
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(LAYOUT, parent, false);
        mSemester =mList.get(position);
        mTitle=(TextView)rowView.findViewById(R.id.semester_title);
        mSession=(TextView)rowView.findViewById(R.id.semester_session);
        mCredits=(TextView)rowView.findViewById(R.id.semester_credits_earned);
        mGpa=(TextView)rowView.findViewById(R.id.semester_gpa);
        recreateView();
        return rowView;
    }

    private void recreateView(){
        try {
            mTitle.setText(mSemester.getName());
            mSession.setText(mSemester.getSession());
            mCredits.setText(mSemester.getCredits());
            mGpa.setText(mSemester.getGpa());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
