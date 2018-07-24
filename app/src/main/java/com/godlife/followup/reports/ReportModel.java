package com.godlife.followup.reports;

public class ReportModel {
   private String  id, title, content, reporter, date;

    public ReportModel() {
    }

    public ReportModel(String id, String title, String content, String reporter, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.reporter = reporter;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
