package com.nikola3in1.audiobooks.util;

import java.sql.Date;
import java.text.DateFormat;

public class StringFormater {

    // Util methods for String formating
    public static float setStars(Double rating) {
        Number n = rating;
        return n.floatValue();
    }

    public static String setRatingsNumber(Integer ratingsNumber) {
        return ratingsNumber + " Ratings";
    }

    public static String setDescription(String description) {
        //Maybe add few \n on a paragraph which has more than 200 words?
        return description;
    }

    public static String setNarator(String narator) {
        return "Read by: " + narator;
    }

    public static String setAuthor(String author) {
        return "Author: " + author;
    }

    public static String setReleasedDate(Date releasedDate) {
        return "Released: " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(releasedDate);
    }

    public static String setDuration(Integer seconds) {
        Integer minutes = seconds / 60;
        Integer hours = minutes / 60;
        minutes = minutes % 60;
        Integer trailingSec = seconds % 60;
        if (trailingSec > 30) {
            minutes++;
        }
        return hours + " hours " + minutes + " minutes ";
    }
}
