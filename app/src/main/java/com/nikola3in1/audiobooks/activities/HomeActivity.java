package com.nikola3in1.audiobooks.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.fragments.menu.BrowseFragment;
import com.nikola3in1.audiobooks.fragments.menu.FeaturedFragment;
import com.nikola3in1.audiobooks.fragments.menu.MyBooksFragment;
import com.nikola3in1.audiobooks.fragments.menu.SettingsFragment;
import com.nikola3in1.audiobooks.model.Book;
import com.nikola3in1.audiobooks.model.Chapter;
import com.nikola3in1.audiobooks.model.DummyData;
import com.nikola3in1.audiobooks.model.MyLibrary;
import com.nikola3in1.audiobooks.model.UserData;
import com.nikola3in1.audiobooks.model.UserPreferences;
import com.nikola3in1.audiobooks.service.PlayerService;
import com.nikola3in1.audiobooks.util.PlayerEventConstants;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.app.PendingIntent.getActivity;
import static com.nikola3in1.audiobooks.util.StringFormater.setRatingsNumber;
import static com.nikola3in1.audiobooks.util.StringFormater.setStars;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context ctx;
    public static FragmentManager fragmentManager;

    // PlayerView
    private View footerPlayer;
    private SeekBar seekBar;
    private boolean seekBarLocked = false;
    private TextView chapterTextView;
    private SlidingUpPanelLayout sliding;

    //User data
    private Book currentBook;
    private Chapter currentChapter;
    private MyLibrary myLibrary;
    private UserPreferences userPreferences;

    //Service
    boolean mBounded;
    private PlayerObserver playerObserver;
    public static PlayerService playerService;
    private Thread playerObserverThread;

    private IntentFilter intentFilter;

    private void testInternalStorage() {
        Book book = UserData.getLastPlayedBook();
        System.out.println("Last played book: " + book);
    }

    private void testSavingBook() {
        Book book = DummyData.getBooks().get(0);
        UserData.setLastPlayedBook(this, book);
        currentBook = UserData.getLastPlayedBook();
        System.out.println("Last played book: " + book);

    }

    // Service binder
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("Service Con:", "Service is disconnected");
            mBounded = false;
            playerService = null;
            stopObserver();
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("Service Con:", "Service is connected");
            mBounded = true;
            PlayerService.LocalBinder mLocalBinder = (PlayerService.LocalBinder) service;
            playerService = mLocalBinder.getServerInstance();

            if (currentBook != null) {
                playerService.playBook(currentBook);
                startObserver();
            }
            //Check for last progress
        }
    };

    // BroadcastReciever
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle data = intent.getExtras();
            if (data != null && data.get("event") != null) {
                String event = (String) data.get("event");
                switch (event) {
                    case PlayerEventConstants.FINISHED:
                        bookIsFinished();
                        break;
                    case PlayerEventConstants.PROGRESS:
                        Integer progress = (Integer) data.get(PlayerEventConstants.PROGRESS);
                        if (progress != null) {
                            System.out.println("RECIEVED PROGRESS " + progress);
                            updateProgressBar(progress);
                        }
                        break;
                    case PlayerEventConstants.MAX_PROGRESS:
                        Integer maxProgress = (Integer) data.get(PlayerEventConstants.MAX_PROGRESS);
                        System.out.println("RECIEVED MAX PROGRESS :" + maxProgress);
                        if (maxProgress != null) {
                            setMaxProgressBar(maxProgress);
                        }
                        break;
                    case PlayerEventConstants.CHAPTER:
                        Integer chapterPosition = (Integer) data.get(PlayerEventConstants.CHAPTER);
                        System.out.println("RECIEVED CHAPTER POS " + chapterPosition);
                        if (chapterPosition != null) {
                            setCurrentChapter(chapterPosition);
                        }
                    case PlayerEventConstants.PLAY_BOOK:
                        Book book = (Book) data.get(PlayerEventConstants.PLAY_BOOK);
                        if (book != null) {
                            System.out.println("PLAYING BOOK <------------");
                            playBook(book);
                        }
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ctx = this;
        setContentView(R.layout.activity_home);

        sliding = findViewById(R.id.sliding_layout);
        sliding.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

        // Init UserData
        initUserData();
//        testSavingBook();

        // Init PlayerService
        initPlayerService();

        // Init BroadcastReciever
        initReciever();

        // Init player controller
//        setupPlayer(currentBook);

        // Set first fragment
        fragmentManager = getSupportFragmentManager();
        displayFragment(new FeaturedFragment());

        // Init navigation
        initNavigation();
    }


    /* Init methods*/
    private void initReciever() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("data");
        registerReceiver(receiver, intentFilter);
    }

    private void initUserData() {
        // Loads app state from internal storage
        UserData.load(this);

        this.currentBook = UserData.getLastPlayedBook();

        if (currentBook.getLastPlayedChapter() == null) {
            System.out.println("LAST PALYED IS NULL");
            currentBook.setLastPlayedChapter(currentBook.getChapters().get(0));
        }

        System.out.println("LAST PLAYED BOOK" + currentBook);
//        this.currentBook = null; // TESTING

        this.userPreferences = UserData.getUserPreferences();
        System.out.println("USER PREFS" + userPreferences);
        this.myLibrary = UserData.getMyLibrary();

        if (myLibrary == null) {
            myLibrary = new MyLibrary();
            UserData.setMyLibrary(this, myLibrary);
            System.out.println("STIL NULL");
        }
//        this.currentChapter = currentBook.getLastPlayedChapter();

        System.out.println("LAST PLAYED BOOK:" + currentBook);
    }

    private void initPlayerService() {
        Bundle data = new Bundle();
        data.putSerializable(Book.BUNDLE, currentBook);
        if (userPreferences != null) {
            System.out.println("USER PREFS:" + userPreferences);
            data.putSerializable(UserPreferences.BUNDLE, userPreferences);
        }
        //check my books library
        System.out.println("INIT PLAYER SERVICE:" + currentBook);
        Intent mIntent = new Intent(this, PlayerService.class);
        mIntent.putExtras(data);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
        startService(mIntent);

        if (currentBook != null) {
            System.out.println("PLAY CURRENT BOOK");
            playBook(currentBook);
            sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
    }

    private void initNavigation() {
        // Initiates Navigation and Actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Remove navigation view shadow
        drawer.setScrimColor(Color.TRANSPARENT);
    }

    /* Layout init */
    private void setupPlayer(Book playedBook) {

        //Setting the footer height
        System.out.println("SETTING UP PLAYER");
        footerPlayer = findViewById(R.id.footer_player);
        sliding.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

        if (playedBook == null) {
            footerPlayer.setVisibility(View.GONE);
//            sliding.setVisibility(View.GONE);
            System.out.println("EXIT FUCKIN THING");
            return;
        }
        System.out.println("PLAYED BOOK " + playedBook);

        sliding.setPanelHeight(143);

        // Setting footer data
        setFooterData(playedBook.getImageUrl(), playedBook.getTitle(), playedBook.getAuthor());

        // Setting button listeners
        ImageButton footerPlayBtn = findViewById(R.id.footer_player_play);
        ImageButton playBtn = findViewById(R.id.player_play);
        ImageButton fastForwardBtn = findViewById(R.id.player_fast_forward);
        ImageButton fastBackwardsBtn = findViewById(R.id.player_fast_backwards);

        footerPlayBtn.setOnClickListener(new OnPlayButtonClickListener());
        playBtn.setOnClickListener(new OnPlayButtonClickListener());
        fastForwardBtn.setOnClickListener(new OnFastForwardButtonClickListener());
        fastBackwardsBtn.setOnClickListener(new OnRewindButtonClickListener());

        fastForwardBtn.setOnLongClickListener((e) -> {
            System.out.println("REWIND");
            playerService.playNextChapter();
            return true;
        });

        fastBackwardsBtn.setOnLongClickListener((e) -> {
            System.out.println("REWIND");
            playerService.playPreviousChapter();
            return true;
        });


        // Title
        TextView title = findViewById(R.id.player_title);
        title.setText(playedBook.getTitle());

        // Image
        ImageView image = findViewById(R.id.player_img);
        Glide.with(this)
                .asBitmap().
                load(playedBook.getImageUrl()).
                into(image);

        // Number of ratings
        TextView nrRatings = findViewById(R.id.player_ratings_number);
        nrRatings.setText(setRatingsNumber(playedBook.getRatingsNumber()));

        // Number of stars
        RatingBar rating = findViewById(R.id.player_rating_stars);
        rating.setRating(setStars(playedBook.getRating()));

        // Seekbar init
        seekBar = findViewById(R.id.player_seekbar);
//        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar tempSeekBar) {
                seekBarLocked = true;
                seekBar.setProgress(tempSeekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarLocked = false;
                Integer progress = seekBar.getProgress();
                playerService.setProgress(progress);
            }
        });

        // Chapter
        chapterTextView = findViewById(R.id.player_chapter);

        //TESTING
//        setCurrentChapter(playedBook);

//        Bookmark
        ImageButton bookmarkBtn = findViewById(R.id.player_bookmark);
        bookmarkBtn.setOnClickListener((e) -> {
            Toast.makeText(this, "Bookmarks!? Coming up in next update!", Toast.LENGTH_SHORT).show();
        });

        System.out.println("BOOK IS SET " + playedBook.getLastPlayedChapter());


    }

    private void setFooterData(String imageUrl, String title, String author) {
        // Setting book image
        ImageView footerImage = findViewById(R.id.footer_player_image);
        Glide.with(this)
                .asBitmap().
                load(imageUrl).
                into(footerImage);

        // Setting book title
        TextView footerTitle = findViewById(R.id.footer_player_title);
        footerTitle.setText(title);

        // Setting book author
        TextView footerAuthor = findViewById(R.id.footer_player_author);
        footerAuthor.setText(author);
    }

    /* Fragment Manager */
    public static void displayFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("tag");
        fragmentTransaction.commit();
    }

    /* BroadcastReciever events */
    private void setCurrentChapter(Book book) {
        // Only called on init - setupPlayer

        Chapter chapter;
        if (book.getLastPlayedChapter() != null) {
            chapter = book.getLastPlayedChapter();
        } else {
            chapter = book.getChapters().get(0);
        }
        System.out.println("SETTING CHAPTER NAME: " + chapter.getName());
        int chapterNr = book.getChapters().indexOf(chapter) + 1;
        chapterTextView.setText("Chapter " + chapterNr + " - " + chapter.getName());
        // Save to memory
        book.setLastPlayedChapter(chapter);
        UserData.setLastPlayedBook(ctx, book);
    }

    private void bookIsFinished() {
        System.out.println("BOOK IS FINISHED ACTIVITY");
        updatePlayButton(true);
    }

    private synchronized void updateProgressBar(Integer progress) {
        if (!seekBarLocked) {
            seekBar.setProgress(progress);

            // Saving progress
            Chapter lastPlayedChapter = currentBook.getLastPlayedChapter();
            if (lastPlayedChapter != null) {
                lastPlayedChapter.setCheckpoint(progress);
                currentBook.setLastPlayedChapter(lastPlayedChapter);
                UserData.setLastPlayedBook(this, currentBook);
            }
        }
    }

    private synchronized void setCurrentChapter(int chapterPosition) {
        // Called from the update thread
        if (chapterPosition >= 0) {
            if (chapterPosition == currentBook.getChapters().size()) {
                chapterPosition--;
            }
            Chapter chapter = currentBook.getChapters().get(chapterPosition);

            if (chapter != null) {
                int chapterNr = chapterPosition + 1;
                chapterTextView.setText("Chapter " + chapterNr + " - " + chapter.getName());
                // Save to memory
                System.out.println("setCurrentChapter: lastChapter" + currentBook.getLastPlayedChapter());
                currentBook.setLastPlayedChapter(chapter);
                UserData.setLastPlayedBook(ctx, currentBook);
                System.out.println("setCurrentChapter: lastChapter 2" + currentBook.getLastPlayedChapter());

            }
        }

    }

    private void setMaxProgressBar(Integer maxProgress) {
        System.out.println("SETTING MAX PROGRESS");
        seekBar.setMax(maxProgress);
    }

    private void playBook(Book book) {
        System.out.println("SETUP PLAYER");
        // Populate player layout

        myLibrary.addBook(this, book);

        System.out.println(Arrays.toString(myLibrary.getBooks().toArray()));

        currentBook = book;

        setupPlayer(book);

        if (playerObserver != null && !playerObserver.stop.get()) {
            System.out.println("STOPING OBSERVER");
            stopObserver();
        }
        if (playerService != null) {
            System.out.println("SETTING NEW BOOK");
            getPlayerService().playBook(book);
            startObserver();
        }

        System.out.println("PLAYBOOK :" + book);

    }

    public synchronized static PlayerService getPlayerService() {
        return playerService;
    }

    /* Observer thread */
    private void updatePlayButton(boolean isPaused) {
        // Called from update thread

        // When player is not rendered
        ImageButton footerPlayButton = findViewById(R.id.footer_player_play);
        ImageButton playButton = findViewById(R.id.player_play);

        if (!isPaused) {
            footerPlayButton.setImageResource(android.R.drawable.ic_media_pause);
            playButton.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            footerPlayButton.setImageResource(android.R.drawable.ic_media_play);
            playButton.setImageResource(android.R.drawable.ic_media_play);

        }
    }

    /* Life Cycle & Activity Events */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);

        System.out.println("DESTROYED");
        unbindService(mConnection);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_featured) {
            displayFragment(new FeaturedFragment());
        } else if (id == R.id.nav_browse) {
            displayFragment(new BrowseFragment());
        } else if (id == R.id.nav_my_books) {
            displayFragment(new MyBooksFragment());

        } else if (id == R.id.nav_settings) {
            displayFragment(new SettingsFragment());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private boolean isBackPressed = false;
    private long maxDelayOnPress = 3000; // 3 sec
    private long backPressedTime;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long timeElapsed = System.currentTimeMillis() - backPressedTime;
            System.out.println("Elapsed: " + timeElapsed);
            if (isBackPressed && timeElapsed < maxDelayOnPress) {
                finish();
                super.onBackPressed();
            } else {

                isBackPressed = false;
            }

            Fragment f = fragmentManager.findFragmentById(R.id.frame);
            if (f instanceof FeaturedFragment) {
                Toast.makeText(ctx, "Press back again to exit", Toast.LENGTH_SHORT).show();
                isBackPressed = true;
                backPressedTime = System.currentTimeMillis();

            } else {
                isBackPressed = false;
                super.onBackPressed();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* OnButtonClickListeners */
    private class OnPlayButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (playerService.isPlaying()) {
                playerService.pause();
            } else {
                playerService.play();
            }
        }
    }

    private class OnFastForwardButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            playerService.fastForward();
        }
    }

    private class OnRewindButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            playerService.fastBackwards();
        }
    }

    /* Player Observer */
    private class PlayerObserver implements Runnable {
        AtomicBoolean stop = new AtomicBoolean(false);

        @Override
        public void run() {
            while (!stop.get()) {
                int progress = getPlayerService().getCurrentPosition();
                if (progress > 0) {
                    updateProgressBar(progress);
                }

                if (getPlayerService().isPrepared()) {
                    if (getPlayerService().isPlaying()) {
                        updatePlayButton(false);
                    } else {
                        updatePlayButton(true);
                    }
                } else {
                    System.out.println("Not prepared yet");
//                    Toast.makeText(ctx,"Chapter is loading, please wait.",Toast.LENGTH_SHORT).show();
                }

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
        playerObserverThread = new Thread(playerObserver);
        playerObserverThread.start();
    }

    public void stopObserver() {
        if (playerObserver != null) {
            playerObserver.stop();
        }
    }

}