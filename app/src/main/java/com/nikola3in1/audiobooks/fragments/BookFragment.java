package com.nikola3in1.audiobooks.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.model.Book;

public class BookFragment extends Fragment {

    private Book book;
    private Context ctx;
    RequestBuilder<Bitmap> preloadedImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        ctx = getActivity().getApplicationContext();
        if (args != null && args.get("book") != null) {
            book = (Book) args.get("book");

            preloadedImage = Glide.with(ctx).asBitmap().load(book.getImageUrl());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_book, container, false);

        initBook(contentView);
        return contentView;
    }

    private void initBook(View contentView) {
        ImageView imageView = contentView.findViewById(R.id.book_details_img);
        preloadedImage.into(imageView);
        LinearLayout playBtn = contentView.findViewById(R.id.book_details_play);

        playBtn.setOnClickListener((e)->{
            System.out.println("CLICKED PLAY!");
        });


//
//
//        LinearLayout layout = contentView.findViewById(R.id.book_details_background);
//        TextView test = new TextView(ctx);
//        test.setText("asdasd");
//
//        // Insert into View
//        ViewGroup insertPoint = contentView.findViewById(R.id.book_details_layout);
//        insertPoint.addView(test, -1, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

}
