package com.nikola3in1.audiobooks.activities;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.fragments.PlayerFragment;
import com.nikola3in1.audiobooks.fragments.menu.BrowseFragment;
import com.nikola3in1.audiobooks.fragments.menu.FeaturedFragment;
import com.nikola3in1.audiobooks.fragments.menu.MyBooksFragment;
import com.nikola3in1.audiobooks.model.Book;
import com.nikola3in1.audiobooks.model.DummyData;
import com.nikola3in1.audiobooks.model.UserData;
import com.nikola3in1.audiobooks.util.SimpleGestureListener;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static FragmentManager fragmentManager;
    private View footerPlayer;
    private GestureDetector gestureDetector;

    private void testInternalStorage() {
        Book book = UserData.getLastPlayedBook(this);
        System.out.println("Last played book: " + book);
    }
    private void testSavingBook() {
        Book book = DummyData.getBooks().get(0);
        UserData.setLastPlayedBook(this, book);
        Book lastPlayedBook = UserData.getLastPlayedBook(this);
        System.out.println("Last played book: " + book);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Loads app state from internal storage
        UserData.load(this);

        fragmentManager = getSupportFragmentManager();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Init footer player controller
        setupFooter();

        // Set first fragment
        displayFragment(new FeaturedFragment());

        // Remove navigation view shadow
        drawer.setScrimColor(Color.TRANSPARENT);
    }

    private void setupFooter() {
        footerPlayer = findViewById(R.id.footer_player);
        Book lastPlayedBook = UserData.getLastPlayedBook(this);

        // On swipe UP gesture listener
        SimpleGestureListener simpleGestureListener = new SimpleGestureListener();
        simpleGestureListener.setListener(dy -> {

            //HERE <-------------------------------------------------------------------------
            Animation translateAnim = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.footer_to_top);
            footerPlayer.startAnimation(translateAnim);
            PlayerFragment playerFragment = new PlayerFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("book", lastPlayedBook);
            playerFragment.setArguments(bundle);
            displayFragment(playerFragment);
        });

        gestureDetector = new GestureDetector(this, simpleGestureListener);

        // Setting gesture listener to footer
        footerPlayer.setOnTouchListener((v, event) -> {
            v.performClick();
            return gestureDetector.onTouchEvent(event);
        });

        // Setting play btn listener
        ImageButton footerPlayBtn = findViewById(R.id.footer_player_play);
        footerPlayBtn.setOnClickListener((e) -> {
            // If playing set 'android.R.drawable.ic_media_pause',
            // else set 'android.R.drawable.ic_media_play'
            System.out.println("PLAY BUTTON IS CLICKED");
        });

        if (lastPlayedBook != null) {
            // Setting footer data
            setFooterData(lastPlayedBook.getImageUrl(), lastPlayedBook.getTitle(),
                    lastPlayedBook.getAuthor());
            footerPlayer.setVisibility(View.VISIBLE);
        } else {
            footerPlayer.setVisibility(View.GONE);
        }

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Proxying event to SimpleGestureListener
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


}

