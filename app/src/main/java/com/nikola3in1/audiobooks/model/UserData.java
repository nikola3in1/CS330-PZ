package com.nikola3in1.audiobooks.model;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/* Internal storage management class */
public class UserData {
    private final static String DATA_FILE_NAME = "appState.data";
    private static Map<String, Object> data;

    public static Book getLastPlayedBook(Context ctx) {
        return (Book) data.get(UserDataConstants.LAST_PLAYED_BOOK);
    }

    public static void setLastPlayedBook(Context ctx, Book book) {
        data.put(UserDataConstants.LAST_PLAYED_BOOK, book);
        save(ctx);
    }

    private static void remove(Context ctx, String key) {
        data.remove(key);
    }

    private static void put(Context ctx, String key, Object value) {
        data.put(key, value);
        save(ctx);
    }

    public static Map<String, Object> load(Context ctx) {
        File directory = ctx.getFilesDir();
        File file = new File(directory, DATA_FILE_NAME);
        Map<String, Object> userData = null;
        try {
            ObjectInput in = new ObjectInputStream(new FileInputStream(file));
            userData = (Map<String, Object>) in.readObject();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            // If the file is not created yet,
            // create a file and save it
            userData = new HashMap<>();
            data = userData;
            save(ctx);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        data = userData;
        return data;
    }

    private static boolean save(Context ctx) {
        File file = new File(ctx.getFilesDir(), DATA_FILE_NAME);
        try {
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(data);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* Defining constants that will be used as keys in userData map */
    private static class UserDataConstants{
        private static final String LAST_PLAYED_BOOK = "lastPlayedBook";

    }

}
