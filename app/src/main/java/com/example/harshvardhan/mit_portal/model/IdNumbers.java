package com.example.harshvardhan.mit_portal.model;


public class IdNumbers {

    private String registrationNumber;
    private String admissionNumber;
    private String rollNumber;

    public IdNumbers(){
        registrationNumber="";
        admissionNumber="";
        rollNumber="";
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }
}
