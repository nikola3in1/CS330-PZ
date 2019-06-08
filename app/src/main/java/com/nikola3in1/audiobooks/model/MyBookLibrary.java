package com.nikola3in1.audiobooks.model;

import java.util.HashMap;
import java.util.Map;

public class MyBookLibrary {
    public static Map<Book, Chapter> myBooks = new HashMap<>();

    public MyBookLibrary() {
    }

    public static Map<Book, Chapter> getMyBooks() {
        return myBooks;
    }

    public static void setMyBooks(Map<Book, Chapter> myBooks) {
        MyBookLibrary.myBooks = myBooks;
    }
}
