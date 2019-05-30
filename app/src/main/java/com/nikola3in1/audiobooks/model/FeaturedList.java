package com.nikola3in1.audiobooks.model;

import java.util.ArrayList;

public class FeaturedList {
    private ArrayList<Book> books = new ArrayList<>();
    private String name;

    public FeaturedList(String name, ArrayList<Book> books) {

        this.books = books;
        this.name = name;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
