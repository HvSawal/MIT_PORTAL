package com.example.harshvardhan.mit_portal.model;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.example.harshvardhan.mit_portal.R;
import com.example.harshvardhan.mit_portal.helper.ContentDownloader;
import com.example.harshvardhan.mit_portal.helper.LocalDataStore;
import com.example.harshvardhan.mit_portal.helper.Util;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;


public class WebSys {

    private static final String[] POST_NAME = {"idValue","birthDate"};

    private boolean invalidCredentials;
    private boolean websysDown;
    private boolean fetchArchives;
    private static WebSys webSys = null;
    private HttpClient httpClient;
    private String home;
    private String academics;
    private ArrayList<String> archive;
    private AppCompatActivity activity;
    private User user;
    private WebSysHome webSysHome = null;
    private WebSysAcademics webSysAcademics = null;

    public WebSysAcademics getWebSysAcademics() {
        return webSysAcademics;
    }

    private WebSys(){
        home = "";
        archive = new ArrayList<>();
        academics = "";
        webSysHome = new WebSysHome();
        webSysAcademics = new WebSysAcademics();
        fetchArchives = false;
    }

    public void fetchArchivedSemesters(boolean b){
        fetchArchives = b;
    }

    public static WebSys getInstance(AppCompatActivity activity){
        if(webSys == null){
            webSys = new WebSys();
        }
        webSys.activity = activity;
        webSys.user = User.getInstance(webSys.activity);
        return webSys;
    }

    public boolean isWebsysDown() {
        return websysDown;
    }

    public boolean isInvalidCredentials() {
        return invalidCredentials;
    }

    public boolean downloadContent(){
        boolean success = true;
        try{
            websysDown = invalidCredentials = false;
            home = academics = "";
            httpClient = new DefaultHttpClient();
            ArrayList<String> mPostName = new ArrayList<>();
            ArrayList<String> mPostParam = new ArrayList<>();
            String[] postParam = {user.getRegNo(),user.getDob()};
            for(int i=0;i<postParam.length;i++){
                mPostName.add(POST_NAME[i]);
                mPostParam.add(postParam[i]);
            }
            home = ContentDownloader.downloadContent(activity.getResources().getString(R.string.url_home),mPostName,mPostParam,httpClient).trim();
            if(home.equals(""))return false;
            Document document= Jsoup.parse(home);
            if(document.getElementById(activity.getResources().getString(R.string.id_login_box))==null && document.getElementById(activity.getResources().getString(R.string.id_name))==null){
                websysDown = true;
                return false;
            }
            if(document.getElementById(activity.getResources().getString(R.string.id_name))==null){
                invalidCredentials = true;
                //return false;
            }
            academics = ContentDownloader.downloadContent(activity.getResources().getString(R.string.url_academics),null,null,httpClient).trim();
            try {
                Document doc = Jsoup.parse(academics);
                Element ele = doc.getElementById(activity.getResources().getString(R.string.id_latest_enrollment));
                Elements ele2 = ele.select("a");
                String latestUrl = ele2.get(0).attr("href");
                String temp = ContentDownloader.downloadContent(activity.getResources().getString(R.string.url_base)+latestUrl, null, null,httpClient).trim();
                if(!temp.equals("")){
                    academics = temp;
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
            if(home.equals("") || academics.equals(""))return false;
            if(webSysHome == null){
                webSysHome = new WebSysHome();
            }
            if(webSysAcademics == null){
                webSysAcademics = new WebSysAcademics();
            }
            webSysHome.setProfile(parseProfile());
            webSysHome.setContactInfo(parseContactInfo());
            webSysHome.setIdNumbers(parseIdNumbers());
            parseAcademics();
            if(fetchArchives){
                parseArchivedSemesters();
            }
            persist();
        }catch(Exception e){
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    private void parseArchivedSemesters(){
        Document document = Jsoup.parse(academics);
        Element table = document.getElementById(activity.getResources().getString(R.string.id_archived_sem_link));
        Elements rows = table.child(1).children();
        for(int i=1;i<rows.size();i++){
            String link = rows.get(i).child(0).child(0).attr("href").trim();
            Util.log(link);
            link=activity.getResources().getString(R.string.url_base)+link;
            Util.log(link);
            String content=ContentDownloader.downloadContent(link, null, null,httpClient).trim();
            Document doc = Jsoup.parse(content);
            parseArchivedSemester(doc);
        }
    }

    public WebSysHome getWebSysHome() {
        return webSysHome;
    }

    public String getAcademics() {
        return academics;
    }

    public ArrayList<String> getArchive() {
        return archive;
    }

    public String getHome() {
        return home;
    }

    private Profile parseProfile(){
        Profile profile = new Profile();
        Document document = Jsoup.parse(home);
        profile.setName(document.getElementById(activity.getResources().getString(R.string.id_name)).text().trim());
        Util.log(profile.getName());
        profile.setStatus(document.getElementById(activity.getResources().getString(R.string.id_status)).text().trim());
        Util.log(profile.getStatus());
        Elements elements = document.getElementById(activity.getResources().getString(R.string.id_semester)).parent().parent().children();
        profile.setSemester(elements.get(1).text().trim());
        Util.log(profile.getSemester());
        profile.setSection(document.getElementById(activity.getResources().getString(R.string.id_section)).text().trim());
        Util.log(profile.getSection());
        profile.setRegistrationNumber(document.getElementById(activity.getResources().getString(R.string.id_reg_no)).text().trim());
        Util.log(profile.getRegistrationNumber());
        elements = document.getElementById(activity.getResources().getString(R.string.id_stream)).parent().parent().children();
        profile.setStream(elements.get(1).text().trim());
        Util.log(profile.getStream());
        elements = document.getElementById(activity.getResources().getString(R.string.id_gender)).parent().parent().children();
        profile.setGender(elements.get(1).text().trim());
        Util.log(profile.getGender());
        elements = document.getElementById(activity.getResources().getString(R.string.id_birth_date)).parent().parent().children();
        profile.setBirthDate(elements.get(1).text().trim());
        Util.log(profile.getBirthDate());
        elements = document.getElementById(activity.getResources().getString(R.string.id_joining_year)).parent().parent().children();
        profile.setJoiningYear(elements.get(1).text().trim());
        Util.log(profile.getJoiningYear());
        elements = document.getElementById(activity.getResources().getString(R.string.id_nationality)).parent().parent().children();
        profile.setNationality(elements.get(1).text().trim());
        Util.log(profile.getNationality());
        return profile;
    }

    private ContactInfo parseContactInfo(){
        ContactInfo contactInfo=new ContactInfo();
        Document document = Jsoup.parse(home);
        Element content = document.getElementById(activity.getResources().getString(R.string.id_profile_summary)).child(0).child(1).child(0);
        final String[] conParams={"Permanent Address","Home Phone","Mobile Phone","Email Address","Local Address"};
        for(int i=0;i<12;i++){
            try {
                String p = content.child(i).child(0).child(0).child(0).ownText().trim();
                String v = content.child(i).child(1).child(0).ownText().trim();
                //Util.log("p-> " + p);
                //Util.log("v-> " + v);
                for (int j = 0; j < conParams.length; j++) {
                    if (p.toLowerCase().contains(conParams[j].toLowerCase())) {
                        switch (j) {
                            case 0:
                                contactInfo.setPermanentAddress(v);
                                break;
                            case 1:
                                contactInfo.setHomePhone(v);
                                break;
                            case 2:
                                contactInfo.setMobile(v);
                                break;
                            case 3:
                                contactInfo.setEmailAddress(v);
                                break;
                            case 4:
                                contactInfo.setPermanentAddress(v);
                                break;

                        }
                        Util.log(v);
                        break;
                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return contactInfo;
    }

    private IdNumbers parseIdNumbers(){
        IdNumbers idNumbers=new IdNumbers();
        Document document= Jsoup.parse(home);
        Element content=document.getElementById(activity.getResources().getString(R.string.id_id_numbers)).child(2);
        final String[] params={"Registration Number","Admission Number","Roll Number"};
        for(int i=0;i<12;i++){
            try {
                String p = content.child(i).child(0).text().trim();
                String v = content.child(i).child(1).text().trim();
                //Util.log("p-> " + p);
                //Util.log("v-> " + v);
                for (int j = 0; j < params.length; j++) {
                    if (p.toLowerCase().contains(params[j].toLowerCase())) {
                        switch (j) {
                            case 0:
                                idNumbers.setRegistrationNumber(v);
                                break;
                            case 1:
                                idNumbers.setAdmissionNumber(v);
                                break;
                            case 2:
                                idNumbers.setRollNumber(v);
                                break;
                        }
                        Util.log(v);
                        break;
                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return idNumbers;
    }

    private boolean isArchivedSemester(Document document){
        if(document.getElementById(activity.getResources().getString(R.string.id_archived_sem_metadata))==null){
            return false;
        }
        return true;
    }

    private String parseArchivedSemester(Document document){
        Semester semester = new Semester();
        semester.setArchived(true);
        HashMap<String,Semester> semesters=webSysAcademics.getSemesters();
        if(semesters == null){
            semesters = new HashMap<>();
            webSysAcademics.setSemesters(semesters);
        }
        Element metadata = document.getElementById(activity.getResources().getString(R.string.id_archived_sem_metadata));
        Element result = document.getElementById(activity.getResources().getString(R.string.id_archived_sem_info));
        String session,credits,gpa,name;
        if(metadata == null){
            Util.log("metadata is null");
        }
        session = metadata.child(4).child(0).child(0).child(1).text().trim();
        Util.log(session);
        semester.setSession(session);
        credits = metadata.child(4).child(0).child(1).child(1).text().trim();
        Util.log(credits);
        semester.setCredits(credits);
        gpa = metadata.child(4).child(0).child(2).child(1).text().trim();
        Util.log(gpa);
        semester.setGpa(gpa);
        name = metadata.parent().child(0).text().trim();
        semester.setName(name);
        Util.log(name);
        semesters.put(semester.getName(),semester);
        if(semester.getSubjects()==null){
            semester.setSubjects(new HashMap<String,Subject>());
        }
        for(Element row:result.child(2).children()){
            Subject subject = new Subject();
            String code = row.child(0).child(0).text().trim();
            Util.log(code);
            subject.setCode(code);
            String course = row.child(1).child(0).text().trim();
            Util.log(course);
            subject.setName(course);
            String credit = row.child(2).child(0).text().trim();
            Util.log(credit);
            subject.setCredits(credit);
            String grade = row.child(3).child(0).text().trim();
            Util.log(grade);
            subject.setGrade(grade);
            String sessionCleared = row.child(4).child(0).text().trim();
            Util.log(sessionCleared);
            subject.setSession(sessionCleared);
            semester.getSubjects().put(subject.getCode(),subject);
        }
        return semester.getName();
    }

    private void parseAcademics(){
        Document document= Jsoup.parse(academics);
        if(isArchivedSemester(document)){
            webSysAcademics.setCurrentSemester(parseArchivedSemester(document));
            return;
        }
        Semester semester = new Semester();
        semester.setArchived(false);
        HashMap<String,Semester> semesters=webSysAcademics.getSemesters();
        if(semesters == null){
            semesters = new HashMap<>();
            webSysAcademics.setSemesters(semesters);
        }
        Element content = document.getElementById(activity.getResources().getString(R.string.id_subject_info)).child(2);
        semester.setName(document.getElementsByClass(activity.getResources().getString(R.string.class_title_bar)).get(0).child(0).child(0).text().trim());
        webSysAcademics.setCurrentSemester(semester.getName());
        Util.log(semester.getName());
        semester.setSession(document.getElementsByClass(activity.getResources().getString(R.string.class_title_bar)).get(0).parent().parent().child(0).text().trim());
        Util.log(semester.getSession());
        semesters.put(semester.getName(),semester);
        HashMap<String,Subject> subjects=semester.getSubjects();
        if(subjects == null){
            subjects = new HashMap<>();
            semester.setSubjects(subjects);
        }
        for(Element ele:content.children()){
            Subject subject = new Subject();
            subject.setCode(ele.child(0).text().trim());
            Util.log(subject.getCode());
            subject.setOption(ele.child(1).text().trim());
            Util.log(subject.getOption());
            subjects.put(subject.getCode(), subject);
            subject.setName(ele.child(2).child(0).child(0).text().trim());
            Util.log(subject.getName());
            subject.setCredits(ele.child(3).text().trim());
            Util.log(subject.getCredits());
            subject.setSection(ele.child(4).child(0).text().trim());
            Util.log(subject.getSection());
            subject.setStatus(ele.child(5).text().trim());
            Util.log(subject.getStatus());
            subject.setOnlyExam(ele.child(6).text().trim());
            Util.log(subject.getOnlyExam());
        }
        content = document.getElementById(activity.getResources().getString(R.string.id_attendance_info)).child(2);
        for(String key:subjects.keySet()){
            Subject sub = subjects.get(key);
            String name = sub.getName();
            String code = sub.getCode();
            for(Element ele:content.children()){
                if(ele.child(0).child(0).text().trim().equalsIgnoreCase(code)){
                    Util.log("Inserting values for subject "+sub.getName());
                    sub.setClasses(ele.child(2).child(0).text().trim());
                    Util.log(sub.getClasses());
                    sub.setAttended(ele.child(3).child(0).text().trim());
                    Util.log(sub.getAttended());
                    sub.setAbsent(ele.child(4).child(0).text().trim());
                    Util.log(sub.getAbsent());
                    sub.setAttendacePercentage(ele.child(5).child(0).text().trim());
                    Util.log(sub.getAttendacePercentage());
                    sub.setAttendaceUpdated(ele.child(6).child(0).text().trim());
                    Util.log(sub.getAttendaceUpdated());
                    break;
                }
            }
            Elements internalAssesments = document.getElementsByAttributeValueContaining("id",activity.getResources().getString(R.string.id_internal_assesment));
            for(Element ai:internalAssesments){
                int aiNumber = 0;
                String title = ai.parent().parent().child(0).child(0).text().trim();
                String[] arr = {"1","2","3"};
                for(int i=0;i<arr.length;i++){
                    if(title.contains(arr[i])){
                        aiNumber = i+1;
                        break;
                    }
                }
                if(aiNumber==0){
                    continue;
                }
                Util.log("Internal assessment "+String.valueOf(aiNumber));
                for(Element ele:ai.child(2).children()){
                    if(code.equalsIgnoreCase(ele.child(0).child(0).text().trim())){
                        String marks=ele.child(2).child(0).text().trim();
                        switch(aiNumber){
                            case 1:
                                sub.setInternalAssessment1(marks);
                                break;
                            case 2:
                                sub.setInternalAssessment2(marks);
                                break;
                            case 3:
                                sub.setInternalAssessment3(marks);
                                break;
                        }
                        Util.log("Marks for AI "+aiNumber+", subject "+name+" -> "+marks);
                        break;
                    }
                }
            }
        }
    }

    public void persist(){
        try{
            Gson gson=new Gson();
            String profile=gson.toJson(webSysHome.getProfile());
            String contactInfo=gson.toJson(webSysHome.getContactInfo());
            String idNumbers=gson.toJson(webSysHome.getIdNumbers());
            String academicStatus=gson.toJson(webSysAcademics);
            Util.log("persisting data");
            Util.log(profile);
            Util.log(contactInfo);
            Util.log(idNumbers);
            Util.log(academicStatus);
            LocalDataStore.store(activity, activity.getResources().getString(R.string.preference_id_numbers), idNumbers);
            LocalDataStore.store(activity, activity.getResources().getString(R.string.preference_contact_info), contactInfo);
            LocalDataStore.store(activity, activity.getResources().getString(R.string.preference_profile_summary), profile);
            LocalDataStore.store(activity,activity.getResources().getString(R.string.preference_academic_status),academicStatus);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void read(){
        try{
            Gson gson=new Gson();
            String profile=LocalDataStore.retrieve(activity, activity.getResources().getString(R.string.preference_profile_summary));
            String contactInfo=LocalDataStore.retrieve(activity, activity.getResources().getString(R.string.preference_contact_info));
            String idNumbers=LocalDataStore.retrieve(activity, activity.getResources().getString(R.string.preference_id_numbers));
            String academicStatus=LocalDataStore.retrieve(activity,activity.getResources().getString(R.string.preference_academic_status));
            Util.log("retrieving data");
            Util.log(profile);
            Util.log(contactInfo);
            Util.log(idNumbers);
            Util.log(academicStatus);
            webSysHome=new WebSysHome();
            webSysHome.setProfile(gson.fromJson(profile,Profile.class));
            webSysHome.setContactInfo(gson.fromJson(contactInfo, ContactInfo.class));
            webSysHome.setIdNumbers(gson.fromJson(idNumbers, IdNumbers.class));
            webSysAcademics=gson.fromJson(academicStatus,WebSysAcademics.class);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void logout(){
        webSysHome=null;
        webSysAcademics=null;
    }

}
