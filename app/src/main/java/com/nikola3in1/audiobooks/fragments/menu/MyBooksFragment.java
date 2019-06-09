package com.nikola3in1.audiobooks.fragments.menu;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.adapters.CategoryFragmentAdapter;
import com.nikola3in1.audiobooks.adapters.FeaturedFragmentAdapter;
import com.nikola3in1.audiobooks.adapters.MyBooksFragmentAdapter;
import com.nikola3in1.audiobooks.model.Book;
import com.nikola3in1.audiobooks.model.FeaturedList;
import com.nikola3in1.audiobooks.model.MyLibrary;

import java.util.ArrayList;

public class MyBooksFragment extends Fragment {
    private final String TITLE = "My Books";
    private Context ctx;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_my_books, null, false);
        //Fetch from backend
        ctx = getActivity().getApplicationContext();
        ArrayList<Book> books = MyLibrary.getBooks();
        initBooks(contentView, books);
        return contentView;
    }

    private void initBooks(View contentView, ArrayList<Book> books) {
        Context ctx = getActivity().getApplicationContext();


        System.out.println("BOOKS ARRAY "+books.size());
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = new RecyclerView(ctx);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        recyclerView.setLayoutManager(layoutManager);
        MyBooksFragmentAdapter adapter = new MyBooksFragmentAdapter(ctx, books);
        recyclerView.setAdapter(adapter);

        // Insert into ScrollView
        ViewGroup insertPoint = contentView.findViewById(R.id.my_books_layout);
        insertPoint.addView(recyclerView, -1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
