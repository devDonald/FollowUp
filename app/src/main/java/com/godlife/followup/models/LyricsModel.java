package com.godlife.followup.models;

public class LyricsModel {
    private String songTitle, fullLyrics, writtenBy, songID;

    public LyricsModel() {
    }

    public LyricsModel(String songTitle, String fullLyrics, String writtenBy, String songID) {
        this.songTitle = songTitle;
        this.fullLyrics = fullLyrics;
        this.writtenBy = writtenBy;
        this.songID = songID;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getFullLyrics() {
        return fullLyrics;
    }

    public void setFullLyrics(String fullLyrics) {
        this.fullLyrics = fullLyrics;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public String getSongID() {
        return songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }
}
