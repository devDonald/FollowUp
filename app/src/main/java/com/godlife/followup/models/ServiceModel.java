package com.godlife.followup.models;

import com.google.firebase.firestore.FieldValue;

public class ServiceModel {
    private String service_tag, service_nugget, service_minister, service_date, service_time, time_stamp, id;

    public ServiceModel(String service_tag, String service_nugget, String service_minister, String service_date, String service_time, String id) {

        this.service_tag = service_tag;
        this.service_nugget = service_nugget;
        this.service_minister = service_minister;
        this.service_date = service_date;
        this.service_time = service_time;
        this.id = id;
    }

    public ServiceModel(String service_tag, String service_nugget, String service_minister, String service_date,
                        String service_time,String time_stamp, String id) {
        this.service_tag = service_tag;
        this.service_nugget = service_nugget;
        this.service_minister = service_minister;
        this.service_date = service_date;
        this.service_time = service_time;
        this.time_stamp = time_stamp;
        this.id = id;
    }

    public String getService_tag() {
        return service_tag;
    }

    public void setService_tag(String service_tag) {
        this.service_tag = service_tag;
    }

    public String getService_nugget() {
        return service_nugget;
    }

    public void setService_nugget(String service_nugget) {
        this.service_nugget = service_nugget;
    }

    public String getService_minister() {
        return service_minister;
    }

    public void setService_minister(String service_minister) {
        this.service_minister = service_minister;
    }

    public String getService_date() {
        return service_date;
    }

    public void setService_date(String service_date) {
        this.service_date = service_date;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }
}
