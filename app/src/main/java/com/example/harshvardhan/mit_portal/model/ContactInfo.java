package com.example.harshvardhan.mit_portal.model;


public class ContactInfo {

    private String homePhone;
    private String mobile;
    private String emailAddress;
    private String permanentAddress;

    public ContactInfo(){
        homePhone="";
        mobile="";
        emailAddress="";
        permanentAddress="";
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }
}
