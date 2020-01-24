package com.godlife.followup.models;

import java.util.HashMap;
import java.util.Map;

public class LoveModel {
    private String id, title, content, author, loveDate, loveTime;

    public LoveModel() {
    }

    public LoveModel(String id, String title, String content, String author, String loveDate, String loveTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.loveDate = loveDate;
        this.loveTime = loveTime;
    }

    public LoveModel(String title, String content, String author, String loveDate, String loveTime) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.loveDate = loveDate;
        this.loveTime = loveTime;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLoveDate() {
        return loveDate;
    }

    public void setLoveDate(String loveDate) {
        this.loveDate = loveDate;
    }

    public String getLoveTime() {
        return loveTime;
    }

    public void setLoveTime(String loveTime) {
        this.loveTime = loveTime;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("title", this.title);
        result.put("content", this.content);
        result.put("author",this.author);
        result.put("loveDate",this.loveDate);
        result.put("loveTime",this.loveTime);

        return result;
    }
}
