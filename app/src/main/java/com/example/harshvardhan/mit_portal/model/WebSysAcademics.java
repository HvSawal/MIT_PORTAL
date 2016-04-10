package com.example.harshvardhan.mit_portal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class WebSysAcademics {

    private HashMap<String, Semester> semesters;
    private String currentSemester;

    public String getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(String currentSemester) {
        this.currentSemester = currentSemester;
    }

    public WebSysAcademics(){
        semesters=null;
    }

    public HashMap<String, Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(HashMap<String, Semester> semesters) {
        this.semesters = semesters;
    }

    public ArrayList<Semester> getAllSemesters() {
        Set<String> set = semesters.keySet();
        ArrayList<Semester> sems = new ArrayList<>();
        for (String s : set) {
            sems.add(semesters.get(s));
        }
        return sems;
    }

}
