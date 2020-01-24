package com.godlife.followup.models;

public class MembersModel {
    private String firstName, otherNames, surname, phone, marital, gender, state, country, occupation, address;
    private String email, DD, MM, photo, uid;
    private int likes;

    public MembersModel() {
    }

    public MembersModel(String firstName, String otherNames, String surname, String phone, String marital,
                        String gender, String state, String country, String occupation, String address, String email,
                        String DD, String MM, String photo, String uid, int likes) {
        this.firstName = firstName;
        this.otherNames = otherNames;
        this.surname = surname;
        this.phone = phone;
        this.marital = marital;
        this.gender = gender;
        this.state = state;
        this.country = country;
        this.occupation = occupation;
        this.address = address;
        this.email = email;
        this.DD = DD;
        this.MM = MM;
        this.photo = photo;
        this.uid = uid;
        this.likes=likes;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDD() {
        return DD;
    }

    public void setDD(String DD) {
        this.DD = DD;
    }

    public String getMM() {
        return MM;
    }

    public void setMM(String MM) {
        this.MM = MM;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
