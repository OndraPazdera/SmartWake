package com.example.smartwake;

import java.io.Serializable;
public class Alarm implements Serializable{
    private String name;
    private String time;
    private String musicType;
    private String location;
    private String musicFilePath;

    public Alarm(String name, String time, String musicType, String location, String musicFilePath) {
        this.name = name;
        this.time = time;
        this.musicType = musicType;
        this.location = location;
        this.musicFilePath = musicFilePath;
    }

    // Gettery a Settery pro každou proměnnou
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMusicType() {
        return musicType;
    }

    public void setMusicType(String musicType) {
        this.musicType = musicType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMusicFilePath() {
        return musicFilePath;
    }

    public void setMusicFilePath(String musicFilePath) {
        this.musicFilePath = musicFilePath;
    }
}

