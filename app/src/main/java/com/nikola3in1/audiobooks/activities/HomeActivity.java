package com.nikola3in1.audiobooks.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.fragments.menu.BrowseFragment;
import com.nikola3in1.audiobooks.fragments.menu.FeaturedFragment;
import com.nikola3in1.audiobooks.fragments.menu.MyBooksFragment;
import com.nikola3in1.audiobooks.model.Book;
import com.nikola3in1.audiobooks.model.DummyData;
import com.nikola3in1.audiobooks.model.UserData;
import com.nikola3in1.audiobooks.service.PlayerService;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static FragmentManager fragmentManager;
    private View footerPlayer;
    private Context ctx;

    //User data
    private Book lastPlayedBook;

    //Service
    boolean mBounded;
    public static PlayerService playerService;

    private void testInternalStorage() {
        Book book = UserData.getLastPlayedBook(this);
        System.out.println("Last played book: " + book);
    }

    private void testSavingBook() {
        Book book = DummyData.getBooks().get(0);
        UserData.setLastPlayedBook(this, book);
        lastPlayedBook = UserData.getLastPlayedBook(this);
        System.out.println("Last played book: " + book);
    }

    ServiceConnection mConnection = new ServiceConnection() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ctx = this;
        setContentView(R.layout.activity_home);

        // Init UserData
        initUserData();
        testSavingBook();

        // Init PlayerService
        initPlayerService();

        // Init player controller
        setupPlayer();

        // Set first fragment
        fragmentManager = getSupportFragmentManager();
        displayFragment(new FeaturedFragment());

        // Init navigation
        initNavigation();
    }

    private void initNavigation(){
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
        this.lastPlayedBook = UserData.getLastPlayedBook(this);
        System.out.println("LAST PLAYED BOOK:"+lastPlayedBook);
    }

    private void initPlayerService() {
        Bundle data = new Bundle();
        data.putSerializable("book", lastPlayedBook);
        System.out.println("INIT PLAYER SERVICE:" + lastPlayedBook);
        Intent mIntent = new Intent(this, PlayerService.class);
        mIntent.putExtras(data);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
        startService(mIntent);
    }

    private void setupPlayer() {
        SlidingUpPanelLayout sliding = findViewById(R.id.sliding_layout);
        //Setting the footer height
        sliding.setPanelHeight(143);

        footerPlayer = findViewById(R.id.footer_player);

        if (lastPlayedBook != null) {
            // Setting footer data
            setFooterData(lastPlayedBook.getImageUrl(), lastPlayedBook.getTitle(),
                    lastPlayedBook.getAuthor());
            footerPlayer.setVisibility(View.VISIBLE);
        } else {
            footerPlayer.setVisibility(View.GONE);
        }

        // Setting play btn listeners
        ImageButton footerPlayBtn = findViewById(R.id.footer_player_play);
        Button playBtn = findViewById(R.id.player_play_btn);
        footerPlayBtn.setOnClickListener(new OnPlayButtonClickListener());
        playBtn.setOnClickListener(new OnPlayButtonClickListener());

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_featured) {
            // Handle the camera action
            displayFragment(new FeaturedFragment());
            System.out.println("nav_featured");
        } else if (id == R.id.nav_browse) {
            displayFragment(new BrowseFragment());
            System.out.println("nav_browse");
        } else if (id == R.id.nav_my_books) {
            displayFragment(new MyBooksFragment());
            System.out.println("nav_my_books");

        } else if (id == R.id.nav_settings) {
            try {
                throw new Exception("NOT IMPLEMENTED YET");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void displayFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("tag");
        fragmentTransaction.commit();
    }

    //Defaults
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
            // If playing set 'android.R.drawable.ic_media_pause',
            // else set 'android.R.drawable.ic_media_play'
            if (playerService.isPlaying()) {
                playerService.pause();
            }else{
                Book playedBook = playerService.play();
//                UserData.setLastPlayedBook(ctx, playedBook); TESTING
            }

        }
    }

}