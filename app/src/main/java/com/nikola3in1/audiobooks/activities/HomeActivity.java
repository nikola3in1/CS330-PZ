package com.nikola3in1.audiobooks.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
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
import com.nikola3in1.audiobooks.model.MyBookLibrary;
import com.nikola3in1.audiobooks.model.UserData;
import com.nikola3in1.audiobooks.model.UserPreferences;
import com.nikola3in1.audiobooks.service.PlayerService;
import com.nikola3in1.audiobooks.util.PlayerEventConstants;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.HashMap;

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

    //User data
    private Book currentBook;
    private Chapter currentChapter;
    private UserPreferences userPreferences;

    //Service
    boolean mBounded;
    public static PlayerService playerService;

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
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("Service Con:", "Service is connected");
            mBounded = true;
            PlayerService.LocalBinder mLocalBinder = (PlayerService.LocalBinder) service;
            playerService = mLocalBinder.getServerInstance();
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
                            updateProgressBar(progress);
                        }
                        break;
                    case PlayerEventConstants.MAX_PROGRESS:
                        Integer maxProgress = (Integer) data.get(PlayerEventConstants.MAX_PROGRESS);
                        if (maxProgress != null) {
                            setMaxProgressBar(maxProgress);
                        }
                        break;
                    case PlayerEventConstants.CHAPTER:
                        Chapter chapter = (Chapter) data.get(PlayerEventConstants.CHAPTER);
                        if (chapter != null) {
                            setCurrentChapter(chapter);
                        }
                    case PlayerEventConstants.PLAY_BOOK:
                        Book book = (Book) data.get(PlayerEventConstants.PLAY_BOOK);
                        if (book != null) {
                            System.out.println("PLAYING BOOK");
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

        // Init UserData
        initUserData();
//        testSavingBook();

        // Init PlayerService
        initPlayerService();

        // Init BroadcastReciever
        initReciever();

        // Init player controller
        setupPlayer();

        // Set first fragment
        fragmentManager = getSupportFragmentManager();
        displayFragment(new FeaturedFragment());

        // Init navigation
        initNavigation();
    }

    private void initReciever() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("data");
        registerReceiver(receiver, intentFilter);
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

    private void initUserData() {
        // Loads app state from internal storage
        UserData.load(this);
//        MyBookLibrary.myBooks = UserData.getMyBooks();
//        if (MyBookLibrary.myBooks == null) {
//            UserData.setMyBooks(this, new HashMap<>());
//        }
//        System.out.println("AFTER INIT");

        this.currentBook = UserData.getLastPlayedBook();
        this.userPreferences = UserData.getUserPreferences();
        this.currentChapter = currentBook.getLastPlayedChapter();

        System.out.println("LAST PLAYED BOOK:" + currentBook);
    }

    private void initPlayerService() {
        Bundle data = new Bundle();
        data.putSerializable(Book.BUNDLE, currentBook);
        if (userPreferences != null) {
            System.out.println("USER PREFS:" + userPreferences);
            data.putSerializable(UserPreferences.BUNDLE, userPreferences);
        }

//        if (MyBookLibrary.myBooks.containsKey(currentBook)) {
//            System.out.println("CHECKING THE LIBRARY");
//            Chapter lastPlayedChapter = MyBookLibrary.myBooks.get(currentBook);
//            if (lastPlayedChapter != null) {
//                // Setting last played chapter
//                System.out.println("LAST CHAPTER:"+lastPlayedChapter);
//                currentBook.setLastPlayedChapter(lastPlayedChapter);
//            }else{
//                System.out.println("FIRST CHAPTER");
//                // If there is on last played chapter, we set first chapter
//                Chapter firstChapter = currentBook.getChapters().get(0);
//                currentBook.setLastPlayedChapter(firstChapter);
//            }
//        }else{
//            Log.d("NOBOOK:", "NOOOOO BOOOOOOK");
//            System.out.println("NO BOOK");
//
//        }

        System.out.println("INIT SERVICE, LAST PLAYED CHAPTER: " + currentBook.getLastPlayedChapter());

        System.out.println("INIT PLAYER SERVICE:" + currentBook);
        Intent mIntent = new Intent(this, PlayerService.class);
        mIntent.putExtras(data);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
        startService(mIntent);
    }

    private void playBook(Book book) {
        currentBook = book;

//        if (MyBookLibrary.myBooks.containsKey(currentBook)) {
//            System.out.println("CHECKING THE LIBRARY");
//            Chapter lastPlayedChapter = MyBookLibrary.myBooks.get(currentBook);
//            if (lastPlayedChapter != null) {
//                // Setting last played chapter
//                System.out.println("LAST CHAPTER:" + lastPlayedChapter);
//                currentBook.setLastPlayedChapter(lastPlayedChapter);
//            } else {
//                System.out.println("FIRST CHAPTER");
//                // If there is on last played chapter, we set first chapter
//                Chapter firstChapter = currentBook.getChapters().get(0);
//                currentBook.setLastPlayedChapter(firstChapter);
//            }
//        } else {
//            Log.d("NOBOOK:", "NOOOOO BOOOOOOK");
//            System.out.println("NO BOOK");
//            Chapter first = currentBook.getChapters().get(0);
//            currentBook.setLastPlayedChapter(first);
//            MyBookLibrary.myBooks.put(currentBook, first);
//        }


        System.out.println("PLAY BOOK, LAST PLAYED CHAPTER: " + book.getLastPlayedChapter());
        setupPlayer();

        Bundle data = new Bundle();
        data.putSerializable(Book.BUNDLE, book);
        if (userPreferences != null) {
            System.out.println("USER PREFS:" + userPreferences);
            data.putSerializable(UserPreferences.BUNDLE, userPreferences);
        }
        Intent mIntent = new Intent(this, PlayerService.class);
        mIntent.putExtras(data);
//        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
        playerService.stopObserver();
        stopService(mIntent);
        startService(mIntent);
    }

    private void setupPlayer() {
        SlidingUpPanelLayout sliding = findViewById(R.id.sliding_layout);
        //Setting the footer height
        sliding.setPanelHeight(143);

        footerPlayer = findViewById(R.id.footer_player);

        if (currentBook == null) {
            footerPlayer.setVisibility(View.GONE);
            return;
        }

        // Setting footer data
        footerPlayer.setVisibility(View.VISIBLE);
        setFooterData(currentBook.getImageUrl(), currentBook.getTitle(), currentBook.getAuthor());

        // Setting button listeners
        ImageButton footerPlayBtn = findViewById(R.id.footer_player_play);
        ImageButton playBtn = findViewById(R.id.player_play);
        ImageButton fastForwardBtn = findViewById(R.id.player_fast_forward);
        ImageButton fastBackwardsBtn = findViewById(R.id.player_fast_backwards);

        footerPlayBtn.setOnClickListener(new OnPlayButtonClickListener());
        playBtn.setOnClickListener(new OnPlayButtonClickListener());
        fastForwardBtn.setOnClickListener(new OnFastForwardButtonClickListener());
        fastBackwardsBtn.setOnClickListener(new OnRewindButtonClickListener());

        // Title
        TextView title = findViewById(R.id.player_title);
        title.setText(currentBook.getTitle());

        // Image
        ImageView image = findViewById(R.id.player_img);
        Glide.with(this)
                .asBitmap().
                load(currentBook.getImageUrl()).
                into(image);

        // Number of ratings
        TextView nrRatings = findViewById(R.id.player_ratings_number);
        nrRatings.setText(setRatingsNumber(currentBook.getRatingsNumber()));

        // Number of stars
        RatingBar rating = findViewById(R.id.player_rating_stars);
        rating.setRating(setStars(currentBook.getRating()));

        // Seekbar init
        seekBar = findViewById(R.id.player_seekbar);
        seekBar.setMax(100);
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
        setCurrentChapter(currentBook.getLastPlayedChapter());

//        Bookmark
        ImageButton bookmarkBtn = findViewById(R.id.player_bookmark);
        bookmarkBtn.setOnClickListener((e) -> {
            Toast.makeText(this, "Bookmarks!? Coming up in next update!", Toast.LENGTH_SHORT).show();
        });

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

    public static void displayFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("tag");
        fragmentTransaction.commit();
    }

    /* BroadcastReciever events */
    private void setCurrentChapter(Chapter chapter) {
        System.out.println("SETTING CHAPTER NAME: " + chapter.getName());
        currentChapter = chapter;
        int chapterNr = currentBook.getChapters().indexOf(chapter) + 1;
        chapterTextView.setText("Chapter " + chapterNr + " - " + chapter.getName());

        // Save to memory
        currentBook.setLastPlayedChapter(currentChapter);

//        MyBookLibrary.myBooks.put(currentBook, currentBook.getLastPlayedChapter());
//        UserData.setMyBooks(ctx);
        UserData.setLastPlayedBook(ctx, currentBook);
    }

    private void setMaxProgressBar(Integer maxProgress) {
        System.out.println("SETTING MAX PROGRESS");
        seekBar.setMax(maxProgress);
    }

    private void updateProgressBar(Integer progress) {
        if (!seekBarLocked) {
            seekBar.setProgress(progress);
            currentChapter.setCheckpoint(progress);
//            currentBook.getLastPlayedChapter().setCheckpoint(progress);
        }
//        System.out.println("UPDATE PROGRESS: "+progress);
    }

    private void bookIsFinished() {
        System.out.println("BOOK IS FINISHED");
        updatePlayButton(true);
    }

    private void updatePlayButton(boolean isPaused) {
        System.out.println("UPDATING BUTTON " + isPaused);

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    private class OnPlayButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Book updatedBook;
            if (playerService.isPlaying()) {
                updatePlayButton(true);
                updatedBook = playerService.pause();
            } else {
                updatePlayButton(false);
                updatedBook = playerService.play();
            }
//            System.out.println("LAST PLAYED CHAPTER CHECKPOINT" + currentBook.getLastPlayedChapter().getCheckpoint());
            updatedBook.setLastPlayedChapter(currentChapter);
            UserData.setLastPlayedBook(ctx, updatedBook);
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

}