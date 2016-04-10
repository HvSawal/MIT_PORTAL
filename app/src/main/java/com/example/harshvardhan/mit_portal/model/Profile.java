package com.example.harshvardhan.mit_portal.model;

public class Profile {

    private String name;
    private String stream;
    private String semester;
    private String section;
    private String gender;
    private String birthDate;
    private String joiningYear;
    private String status;
    private String nationality;
    private String registrationNumber;

    public Profile(){
        name="";
        stream="";
        semester="";
        section="";
        gender="";
        birthDate="";
        joiningYear="";
        status="";
        nationality="";
        registrationNumber="";
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getJoiningYear() {
        return joiningYear;
    }

    public void setJoiningYear(String joiningYear) {
        this.joiningYear = joiningYear;
    }

}
