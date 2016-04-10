package com.example.harshvardhan.mit_portal.model;

public class Subject {

    private String code;
    private String name;
    private String credits;
    private String section;
    private String status;
    private String onlyExam;
    private String classes;
    private String attended;
    private String absent;
    private String option;
    private String attendacePercentage;
    private String attendaceUpdated;
    private String internalAssessment1;
    private String internalAssessment2;
    private String internalAssessment3;
    private String grade;
    private boolean incomplete;
    private String session;

    public String getSession() {
        return session;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public boolean isIncomplete() {
        return incomplete;
    }

    public void setIncomplete(boolean incomplete) {
        this.incomplete = incomplete;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOnlyExam() {
        return onlyExam;
    }

    public void setOnlyExam(String onlyExam) {
        this.onlyExam = onlyExam;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getAttended() {
        return attended;
    }

    public void setAttended(String attended) {
        this.attended = attended;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getAttendaceUpdated() {
        return attendaceUpdated;
    }

    public void setAttendaceUpdated(String attendaceUpdated) {
        this.attendaceUpdated = attendaceUpdated;
    }

    public String getAttendacePercentage() {
        return attendacePercentage;
    }

    public void setAttendacePercentage(String attendacePercentage) {
        this.attendacePercentage = attendacePercentage;
    }

    public String getInternalAssessment1() {
        return internalAssessment1;
    }

    public void setInternalAssessment1(String internalAssessment1) {
        this.internalAssessment1 = internalAssessment1;
    }

    public String getInternalAssessment2() {
        return internalAssessment2;
    }

    public void setInternalAssessment2(String internalAssessment2) {
        this.internalAssessment2 = internalAssessment2;
    }

    public String getInternalAssessment3() {
        return internalAssessment3;
    }

    public void setInternalAssessment3(String internalAssessment3) {
        this.internalAssessment3 = internalAssessment3;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
