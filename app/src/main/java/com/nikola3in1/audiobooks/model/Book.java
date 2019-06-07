package com.nikola3in1.audiobooks.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class Book implements Serializable {
    private String title;
    private String author;
    private String imageUrl;
    private Integer ratingsNumber;
    private Double rating;
    private String narator;
    private Date releasedDate;
    private String description;
    private Integer duration;

    // UserData
    ArrayList<Chapter> chapters;

    private Chunk lastPlayedChunk;

    public Chunk getLastPlayedChunk() {
        return lastPlayedChunk;
    }

    public void setLastPlayedChunk(Chunk lastPlayedChunk) {
        this.lastPlayedChunk = lastPlayedChunk;
    }

    public Book(String title, String author, String imageUrl) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    public Book(String title, String author, String imageUrl, Integer ratingsNumber, Double rating,
                String narator, Date releasedDate, String description, Integer duration) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.ratingsNumber = ratingsNumber;
        this.rating = rating;
        this.narator = narator;
        this.releasedDate = releasedDate;
        this.description = description;
        this.duration = duration;
    }

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Integer getRatingsNumber() {
        return ratingsNumber;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setRatingsNumber(Integer ratingsNumber) {
        this.ratingsNumber = ratingsNumber;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getNarator() {
        return narator;
    }

    public void setNarator(String narator) {
        this.narator = narator;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", ratingsNumber=" + ratingsNumber +
                ", rating=" + rating +
                ", narator='" + narator + '\'' +
                ", releasedDate=" + releasedDate +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", chapters=" + chapters +
                '}';
    }
}

