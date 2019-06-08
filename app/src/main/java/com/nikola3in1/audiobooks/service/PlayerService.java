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

public class PlayerService extends Service {
    // Book that is beign played
    private Book book;
    private Integer currentChapterPosition = 0;
    private MediaPlayer player;
    private int currentProgress = 0;
    private boolean hasPlayed = false;
    private IBinder mBinder = new LocalBinder();
    private UserPreferences userPreferences;

    // Fast forward & fast backwards value
    private Integer mediaSkipValue = 1000 * 20;

    private PlayerObserver playerObserver = null;

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

        initPlayer();

        // Events
        player.setOnCompletionListener((e) -> {
            stopObserver();

            if (book.getChapters().size() == currentChapterPosition) {
                finishBook();
            } else {
                sendNextChapter();
                prepareNextChapter();
                startObserver();
                player.start();
            }
            sendMaxProgress();
            sendProgressUpdate(player.getCurrentPosition());
        });

        return val;
    }

    @Override
    public void onDestroy() {
        System.out.println("DESTORY SERVICE");
        super.onDestroy();
    }

    private void initPlayer() {
        player = new MediaPlayer();
        playerObserver = new PlayerObserver();
        // Starting from begining

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
            System.out.println("START FROM THE BOOKMARK??!?!?");
        }
    }

    private void continueFromChapter() {
        Chapter lastPlayedChapter = book.getLastPlayedChapter();
        Integer lastChapterPosition = book.getChapters().indexOf(lastPlayedChapter);
        if (lastChapterPosition >= 0) {
            currentChapterPosition = lastChapterPosition;
            System.out.println("CHECKPOINT: " + lastPlayedChapter.getCheckpoint());

            prepareNextChapter();
            if (lastPlayedChapter.getCheckpoint() != null) {
                System.out.println("CHECK CHEKC");
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
        currentChapterPosition = 0;
        sendNextChapter();
        prepareNextChapter();
        startObserver();
    }

    private void sendBookIsFinished() {
        sendMessage(PlayerEventConstants.FINISHED, true);
    }

    /* Controlls */
    public Book play() {
//        sendMessage("asd","PLAY");

        if (!hasPlayed) {
            sendMaxProgress();
            player.start();
            hasPlayed = true;
            startObserver();
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
        sendMessage(PlayerEventConstants.CHAPTER, book.getChapters().get(currentChapterPosition));
    }

    private void sendMaxProgress() {
        sendMessage(PlayerEventConstants.MAX_PROGRESS, player.getDuration());
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

    public void setProgress(Integer progress) {
        currentProgress = progress;
        player.seekTo(progress);
    }

    public class LocalBinder extends Binder {
        public PlayerService getServerInstance() {
            return PlayerService.this;
        }
    }

    /* Getters and Setters */

    public Integer getMaxProgress(){
        return player.getDuration();
    }

    private Chapter getNextChapter() {
        return book.getChapters().get(currentChapterPosition++);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    /* Player Observer */
    private class PlayerObserver implements Runnable {
        AtomicBoolean stop = new AtomicBoolean(false);

        @Override
        public void run() {
            while (!stop.get()) {
                int progress = player.getCurrentPosition();
                sendProgressUpdate(progress);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        public void stop() {
            stop.set(true);
        }
    }

    private void startObserver() {
        playerObserver = new PlayerObserver();
        new Thread(playerObserver).start();
    }

    public void stopObserver() {
        if (playerObserver != null) {
            playerObserver.stop();
        }
    }
}

