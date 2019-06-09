package com.nikola3in1.audiobooks.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.nikola3in1.audiobooks.model.Book;
import com.nikola3in1.audiobooks.model.Chapter;
import com.nikola3in1.audiobooks.model.UserPreferences;
import com.nikola3in1.audiobooks.util.PlayerEventConstants;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerService extends Service {
    // Book that is beign played
    private Book book;
    private AtomicInteger currentChapterPosition = new AtomicInteger(0);
    private MediaPlayer player;
    private int currentProgress = 0;
    private boolean hasPlayed = false;
    private IBinder mBinder = new LocalBinder();
    private UserPreferences userPreferences;

    // Fast forward & rewind value
    private Integer mediaSkipValue = 1000 * 20;

    public int onStartCommand(Intent intent, int flags, int startId) {
        int val = super.onStartCommand(intent, flags, startId);
        Bundle args = intent.getExtras();

        if (args == null || args.get(Book.BUNDLE) == null) {
            return val;
        }
        if (args.get(UserPreferences.BUNDLE) != null) {
            userPreferences = (UserPreferences) args.get(UserPreferences.BUNDLE);
            mediaSkipValue = userPreferences.getMediaSkipValue();
        }
        book = (Book) args.get(Book.BUNDLE);
        return val;
    }

    public void playBook(Book book) {
        if (book != null) {
            this.book = book;
            hasPlayed = false;
            currentProgress = 0;
            currentChapterPosition.set(0);
            if (player == null) {
                System.out.println("CREATING MEDIA PLAYER !!!");
                player = new MediaPlayer();
            } else {
                player.stop();
                player.reset();
            }
            initPlayer();
            events();
        }

    }

    private void events() {
        // Events
        player.setOnCompletionListener((e) -> {
            if (book.getChapters().size() == currentChapterPosition.get()) {
                finishBook();
            } else {
                sendNextChapter();
                prepareNextChapter();
                player.start();
            }
            sendMaxProgress();
            sendProgressUpdate(player.getCurrentPosition());
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initPlayer() {
        // Starting from begining
        System.out.println("initPlayer: lastPlayedCHapter " + book.getLastPlayedChapter());
        if (book.getLastPlayedChapter() != null) {
            // Start from last played chapter
            System.out.println("START FROM LAST");
            continueFromChapter();
        } else if (book.getBookmark() == null) {
            // Start from begining
            System.out.println("START FROM BEGINING");
            prepareNextChapter();
        } else {
            // Start from bookmark
        }
        sendNextChapter();
    }

    private void continueFromChapter() {
        Chapter lastPlayedChapter = book.getLastPlayedChapter();
        Integer lastChapterPosition = book.getChapters().indexOf(lastPlayedChapter);
        if (lastChapterPosition >= 0) {
//            currentChapterPosition = lastChapterPosition;
            currentChapterPosition.set(lastChapterPosition);
            System.out.println("CHECKPOINT: " + lastPlayedChapter.getCheckpoint());

            prepareNextChapter();
            if (lastPlayedChapter.getCheckpoint() != null) {
                currentProgress = lastPlayedChapter.getCheckpoint();
                sendMaxProgress();
                sendProgressUpdate(currentProgress);
            }
        }
    }

    private void prepareChapter(Chapter chapter) {
        if (player != null) {
            player.reset();
        }
        try {
            String url = chapter.getAudioURL();
            if (url != null) {
                player.setDataSource(url);
                player.prepare();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareNextChapter() {
        Chapter nextChapter = getNextChapter();
        System.out.println("INDEX OF NEXT CHAPTER: " + currentChapterPosition);
        prepareChapter(nextChapter);
        book.setLastPlayedChapter(nextChapter);
    }

    private void finishBook() {
        // Emit event to activity
        System.out.println("BOOK IS FINISHED");
        sendBookIsFinished();
//        currentChapterPosition = 0;
        currentChapterPosition.set(0);

        sendNextChapter();
        prepareNextChapter();
    }


    /* Media Controls */
    public Book play() {
        if (!hasPlayed) {
            sendMaxProgress();
            player.start();
            hasPlayed = true;
            if (currentProgress > 0) {
                System.out.println("SEEKING TO :" + currentProgress);
                player.seekTo(currentProgress);
            }
        } else {
            player.seekTo(currentProgress);
            player.start();
        }
        return book;
    }

    public Book pause() {
        currentProgress = player.getCurrentPosition();
        player.pause();
        return book;
    }

    public boolean isPlaying() {
        return player.isPlaying();
    }

    public void fastForward() {
        Integer currPosition = player.getCurrentPosition();
        System.out.println("FAST FORWARDING: from: " + currPosition + ", to: " + (currPosition + mediaSkipValue));
        player.seekTo(currPosition + mediaSkipValue);
        setProgress(player.getCurrentPosition());

    }

    public void fastBackwards() {
        Integer currPosition = player.getCurrentPosition();
        System.out.println("REWINDING: from: " + currPosition + ", to: " + (currPosition - mediaSkipValue));
        player.seekTo(currPosition - mediaSkipValue);
        setProgress(player.getCurrentPosition());
    }

    /* Events */

    private void sendNextChapter() {
        sendMessage(PlayerEventConstants.CHAPTER, currentChapterPosition.get());
    }

    private void sendMaxProgress() {
        sendMessage(PlayerEventConstants.MAX_PROGRESS, player.getDuration());
    }

    private void sendBookIsFinished() {
        sendMessage(PlayerEventConstants.FINISHED, true);
    }

    private void sendProgressUpdate(Integer progress) {
        sendMessage(PlayerEventConstants.PROGRESS, progress);
    }

    private void sendMessage(String key, Serializable value) {
        Intent intent = new Intent("data");
        // Adding some data
        intent.putExtra("event", key);
        intent.putExtra(key, value);
        sendBroadcast(intent);
    }

    /* Binder */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public class LocalBinder extends Binder {
        public PlayerService getServerInstance() {
            return PlayerService.this;
        }
    }

    public boolean isReady() {
        return player != null;
    }

    /* Getters and Setters */

    public void setProgress(Integer progress) {
        currentProgress = progress;
        player.seekTo(progress);
    }

    public int getCurrentPosition() {
        if (player != null) {
            return player.getCurrentPosition();
        }
        return 0;
    }


    public synchronized Integer getCurrentChapterPosition() {
        if (player != null) {
            return currentChapterPosition.get();
        }
        return 0;
    }

    public void setCurrentChapterPosition(Integer currentChapterPosition) {
        this.currentChapterPosition.set(currentChapterPosition);
//        this.currentChapterPosition = currentChapterPosition;
    }

    public Integer getMaxProgress() {
        return player.getDuration();
    }

    private Chapter getNextChapter() {
        return book.getChapters().get(currentChapterPosition.getAndIncrement());
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

