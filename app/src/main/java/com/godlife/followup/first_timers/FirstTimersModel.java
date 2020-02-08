package com.godlife.followup.first_timers;

public class FirstTimersModel {
    private String name, dob,gender, address, email, phone, occupation, marital;
    private String bornAgain, filled,supervisor,dateOfVisit, prayer;

    public FirstTimersModel() {
    }

    public FirstTimersModel(String name, String dob, String gender, String address, String email,
                            String phone, String occupation, String marital, String bornAgain,
                            String filled, String supervisor,String dateOfVisit) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.occupation = occupation;
        this.marital = marital;
        this.bornAgain = bornAgain;
        this.filled = filled;
        this.supervisor = supervisor;
        this.dateOfVisit = dateOfVisit;
    }

    public FirstTimersModel(String name, String dob, String gender, String address, String email, String phone, String occupation, String marital, String bornAgain, String filled, String supervisor, String dateOfVisit, String prayer) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.occupation = occupation;
        this.marital = marital;
        this.bornAgain = bornAgain;
        this.filled = filled;
        this.supervisor = supervisor;
        this.dateOfVisit = dateOfVisit;
        this.prayer = prayer;
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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getBornAgain() {
        return bornAgain;
    }

    public void setBornAgain(String bornAgain) {
        this.bornAgain = bornAgain;
    }

    public String getFilled() {
        return filled;
    }

    public void setFilled(String filled) {
        this.filled = filled;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(String dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }
}
