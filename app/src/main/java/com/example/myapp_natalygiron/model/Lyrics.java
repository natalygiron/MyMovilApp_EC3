package com.example.myapp_natalygiron.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Lyrics {

    @SerializedName("quote")
    private String quote;

    @SerializedName("song")
    private String song;

    @SerializedName("album")
    private String album;

    public Lyrics(String quote, String song, String album) {
        this.quote = quote;
        this.song = song;
        this.album = album;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
