package com.nikola3in1.audiobooks.model;

import java.io.Serializable;

public class Category implements Serializable {
    private String title;
    private String imageUrl;

    public Category(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Category{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}



