package com.nikola3in1.audiobooks.util;

public class PlayerConstants {
    public static class Commands{
        // Player Controlls
        public final static String PLAY = "play";
        public final static String PAUSE = "pause";
        public final static String FAST_FORWARD= "fastForward";
        public final static String FAST_BACKWARDS = "fastBackwards";

        // Queries
        public final static String IS_PLAYING = "is_playing";

        // Getters and Setters
        public final static String SET_BOOK = "set_book";
        public final static String GET_BOOK = "get_book";

    }

    public static class Data{
        public final static String BOOK = "book";
    }

    public static class ResponseCodes{
        public final static String BOOK = "book";

    }



}

