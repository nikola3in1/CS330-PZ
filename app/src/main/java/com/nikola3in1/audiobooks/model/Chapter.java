package com.nikola3in1.audiobooks.model;

import java.io.Serializable;
import java.util.List;

public class Chapter implements Serializable {
    private String name;
    private String about;
    private List<Chunk> chunkURLs;

    public Chapter() {
    }

    public Chapter(String name, List<Chunk> chunkURLs) {
        this.name = name;
        this.chunkURLs = chunkURLs;
    }

    public Chapter(String name, String about, List<Chunk> chunkURLs) {
        this.name = name;
        this.about = about;
        this.chunkURLs = chunkURLs;
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

    public List<Chunk> getChunkURLs() {
        return chunkURLs;
    }

    public void setChunkURLs(List<Chunk> chunkURLs) {
        this.chunkURLs = chunkURLs;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "name='" + name + '\'' +
                ", about='" + about + '\'' +
                ", chunkURLs=" + chunkURLs +
                '}';
    }
}
