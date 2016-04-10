package com.example.harshvardhan.mit_portal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import com.example.harshvardhan.mit_portal.model.Subject;


public class Semester {

    private HashMap<String,Subject> subjects;
    private boolean archived;
    private String name;
    private String gpa;
    private String credits;
    private String session;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getCredits() {
        return credits;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Semester(){
        subjects=null;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public HashMap<String, Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(HashMap<String, Subject> subjects) {
        this.subjects = subjects;
    }

    public ArrayList<Subject> getAllSubjects(){
        Set<String> set=subjects.keySet();
        ArrayList<Subject> subs=new ArrayList<>();
        for(String s:set){
            subs.add(subjects.get(s));
        }
        return subs;
    }

}
