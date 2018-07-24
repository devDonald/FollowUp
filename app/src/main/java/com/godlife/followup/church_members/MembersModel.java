package com.godlife.followup.church_members;

/**
 * Created by donald on 3/22/18.
 */

public class MembersModel {
    private String name,dob,gender,address, email, phone, marital, occupation,nationality;
    private String stateOfOrigin, location, image;

    public MembersModel() {
    }

    public MembersModel(String name, String dob, String gender, String address,
                        String email, String phone, String marital,
                        String occupation, String nationality,
                        String stateOfOrigin, String location) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.marital = marital;
        this.occupation = occupation;
        this.nationality = nationality;
        this.stateOfOrigin = stateOfOrigin;
        this.location = location;
    }
    public MembersModel(String name,String phone,String occupation,String location, String marital, String image){
        this.name = name;
        this.phone = phone;
        this.occupation = occupation;
        this.location = location;
        this.marital = marital;
        this.image=image;
}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getStateOfOrigin() {
        return stateOfOrigin;
    }

    public void setStateOfOrigin(String stateOfOrigin) {
        this.stateOfOrigin = stateOfOrigin;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
