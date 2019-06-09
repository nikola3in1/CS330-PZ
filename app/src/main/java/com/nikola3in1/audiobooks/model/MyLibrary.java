package com.nikola3in1.audiobooks.model;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyLibrary implements Serializable {
    /* Singleton */
    private static Set<Book> books = new HashSet<>();

    public MyLibrary() {
    }

    public void addBook(Context ctx, Book book) {
        if (book != null) {
            books.add(book);
        }
        // Saving data
        UserData.setMyLibrary(ctx, this);
    }


    /* Getters and Setters */


    public static ArrayList<Book> getBooks() {
        return new ArrayList<>(books);
    }
}

