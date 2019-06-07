package com.nikola3in1.audiobooks.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.nikola3in1.audiobooks.model.Book;
import com.nikola3in1.audiobooks.model.Chapter;
import com.nikola3in1.audiobooks.model.Chunk;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PlayerService extends Service {
    // Book that is beign played
    private Book book;
    HashMap<String, List<Chunk>> chaptersMap = new HashMap<>();
    private Chapter currentChapter;
    private Integer currentChunkPosition = 0;
    private List<Chunk> chapterChunks = new ArrayList<>();

    private MediaPlayer player;
    private int pausedAt;

    private boolean isInit = true;

    IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public PlayerService getServerInstance() {
            return PlayerService.this;
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        int val = super.onStartCommand(intent, flags, startId);
        Bundle args = intent.getExtras();

        if (args == null || args.get("book") == null) {
            return val;
        }

        book = (Book) args.get("book");
        System.out.println("BOOK :"+book);

        initPlayer();
        return val;

    }

    private void initPlayer() {
        // Starting from begining

        if (book.getLastPlayedChunk() == null) {
            initChunkArray();
            prepareNextChunk();
        }else{
            // Bookmark
            // find chapter, chunk pos, etc...
        }

        player.setOnCompletionListener((e)->{
            prepareNextChunk();
            player.start();

        });
    }

    private Chunk getNextChunk() {
        return chapterChunks.get(currentChunkPosition++);
    }

    void prepareNextChunk(){
        Chunk chunk = getNextChunk();
        if (player == null) {
            player = new MediaPlayer();
        }else{
            player.reset();
        }


        try {
            player.setDataSource(chunk.getUrl());
            player.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initChunkArray() {
        chapterChunks = book.getChapters().get(0).getChunkURLs();
    }

    public Chapter getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(Chapter currentChapter) {
        this.currentChapter = currentChapter;
    }


    /*Controlls*/

    public Book play() {
        if (isInit) {
            player.start();
            isInit = false;
        } else {
            player.seekTo(pausedAt);
            player.start();
        }
        return book;
    }

    public boolean isPlaying() {
        return player.isPlaying();
    }

    public void pause() {
        pausedAt = player.getCurrentPosition();
        player.pause();
    }

    /* Util */


    public void fastForward() {
    }

    public void fastBackwards() {
    }


    /*Service lifecycle methods*/
    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//        player = MediaPlayer.create(this, R.raw.song);
//        player.start();
//
//        player.setOnCompletionListener((event) -> {
//            player.stop();
//        });
    }

    public void onDestroy() {
//        super.onDestroy();
//        player.stop();
    }

    /*Get and Set methods*/
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    /*Events*/
    void fetchNextChunk() {
    }

    void fetchPreviousChunk() {

    }

    void finishBook() {
    }

}

