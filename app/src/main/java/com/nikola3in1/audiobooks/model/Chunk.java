package com.nikola3in1.audiobooks.model;

import java.io.Serializable;

public class Chunk implements Serializable {
    private String url;

    public Chunk(String chunkUrl) {
        this.url = chunkUrl;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chunk chunk = (Chunk) o;
        return getUrl().equals(chunk.getUrl());
    }

    @Override
    public int hashCode() {
        return getUrl().hashCode();
    }

    @Override
    public String toString() {
        return "Chunk{" +
                "url='" + url + '\'' +
                '}';
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
