package com.example.harshvardhan.mit_portal.model;


public class WebSysHome {

    private ContactInfo contactInfo;
    private Profile profile;
    private IdNumbers idNumbers;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public IdNumbers getIdNumbers() {
        return idNumbers;
    }

    public void setIdNumbers(IdNumbers idNumbers) {
        this.idNumbers = idNumbers;
    }
}
