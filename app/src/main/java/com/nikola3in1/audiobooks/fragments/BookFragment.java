package com.nikola3in1.audiobooks.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.model.Book;

import org.w3c.dom.Text;

import java.sql.Date;
import java.text.DateFormat;

import static com.android.volley.VolleyLog.TAG;

public class BookFragment extends Fragment {

    private Book book;
    private Context ctx;
    private String TITLE;
    RequestBuilder<Bitmap> preloadedImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        ctx = getActivity().getApplicationContext();
        if (args != null && args.get("book") != null) {
            book = (Book) args.get("book");

            preloadedImage = Glide.with(ctx).asBitmap().load(book.getImageUrl());
            TITLE = book.getTitle();
            getActivity().setTitle(TITLE);
        }


        String duration = setDuration(60 * 60 * 3 + 43);
        System.out.println("Duration: " + duration);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_book, container, false);

        initBook(contentView);
        return contentView;
    }


    private void playBook() {
    }

    private void initBook(View contentView) {
        ImageView bookImage = contentView.findViewById(R.id.book_details_img);
        preloadedImage.into(bookImage);

        ImageButton playBtn = contentView.findViewById(R.id.book_details_play);
        playBtn.setOnClickListener((e) -> {
            Log.d(TAG, "Clicked on play button: " + book.getTitle());
            playBook();
        });
        // Title
        TextView titleTxt = contentView.findViewById(R.id.book_details_title);
        titleTxt.setText(TITLE);
        // Author
        TextView authorTxt = contentView.findViewById(R.id.book_details_author);
        authorTxt.setText(setAuthor(book.getAuthor()));
        // Narator
        TextView readByTxt = contentView.findViewById(R.id.book_details_readby);
        readByTxt.setText(setNarator(book.getNarator()));
        // Duration
        TextView durationTxt = contentView.findViewById(R.id.book_details_duration);
        durationTxt.setText(setDuration(book.getDuration()));
        // Released date
        TextView releasedDateTxt = contentView.findViewById(R.id.book_details_realesed);
        releasedDateTxt.setText(setReleasedDate(book.getReleasedDate()));
        // Description
        TextView descriptionTxt = contentView.findViewById(R.id.book_details_description);
        descriptionTxt.setText(setDescription(book.getDescription()));
        // Number of ratings
        TextView nrRatings = contentView.findViewById(R.id.book_details_ratings_number);
        nrRatings.setText(setRatingsNumber(book.getRatingsNumber()));
        // Number of stars
        RatingBar rating = contentView.findViewById(R.id.book_details_rating_stars);
        rating.setRating(setStars(book.getRating()));

    }

    // Util methods for String formating
    private float setStars(Double rating) {
        Number n = rating;
        return n.floatValue();
    }
    private String setRatingsNumber(Integer ratingsNumber) {
        return ratingsNumber + " Ratings";
    }
    private String setDescription(String description) {
        //Maybe add few \n on a paragraph which has more than 200 words?
        return description;
    }
    private String setNarator(String narator) {
        return "Read by: " + narator;
    }
    private String setAuthor(String author) {
        return "Author: " + author;
    }
    private String setReleasedDate(Date releasedDate) {
        return "Released: " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(releasedDate);
    }
    private String setDuration(Integer seconds) {
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
