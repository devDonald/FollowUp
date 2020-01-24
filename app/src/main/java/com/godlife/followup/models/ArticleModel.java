package com.godlife.followup.models;

public class ArticleModel {
    private String title, content, author, date,time, articleID;

    public ArticleModel() {
    }

    public ArticleModel(String title, String content, String author, String date, String time, String articleID) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
        this.time=time;
        this.articleID =articleID;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
