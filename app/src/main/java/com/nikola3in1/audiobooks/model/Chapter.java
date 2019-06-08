package com.nikola3in1.audiobooks.model;

import java.io.Serializable;

public class Chapter implements Serializable {
    private String name;
    private String about;
    private String audioURL;

    private Integer checkpoint=0;

    public Chapter() {
    }

    public Chapter(String name, String about, String audioURL) {
        this.name = name;
        this.about = about;
        this.audioURL = audioURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chapter chapter = (Chapter) o;
        return getName().equals(chapter.getName()) &&
                getAbout().equals(chapter.getAbout()) &&
                getAudioURL().equals(chapter.getAudioURL());
    }

    @Override
    public int hashCode() {
        String hash = getName() + getAbout() + getAudioURL();
        return hash.hashCode();
    }

    public Integer getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(Integer checkpoint) {
        this.checkpoint = checkpoint;
    }

    public String getAudioURL() {
        return audioURL;
    }

    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "name='" + name + '\'' +
                ", checkpoint=" + checkpoint +
                '}';
    }
}
