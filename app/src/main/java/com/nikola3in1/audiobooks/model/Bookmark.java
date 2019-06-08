package com.nikola3in1.audiobooks.model;

import java.io.Serializable;

public class Bookmark implements Serializable {
    private Chapter chapter;
    private Integer position;

    public Bookmark(Chapter chapter, Integer position) {
        this.chapter = chapter;
        this.position = position;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "chapter=" + chapter +
                ", position=" + position +
                '}';
    }
}
