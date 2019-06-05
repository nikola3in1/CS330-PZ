package com.nikola3in1.audiobooks.fragments;

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

import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.adapters.CategoryFragmentAdapter;
import com.nikola3in1.audiobooks.model.DummyData;
import com.nikola3in1.audiobooks.model.Book;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {
    private String TITLE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        //Setting the title of the category
        if (args != null && args.get("title") != null) {
            this.TITLE = (String) args.get("title");
            getActivity().setTitle(TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_category, container, false);
        ArrayList<Book> books;

        // Fetch data from backend...
        books = DummyData.getBooks();

        initBooks(contentView,books);
        return contentView;
    }

    private void initBooks(View contentView, ArrayList<Book> books) {
        Context ctx = getActivity().getApplicationContext();

        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = new RecyclerView(ctx);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        recyclerView.setLayoutManager(layoutManager);
        CategoryFragmentAdapter adapter = new CategoryFragmentAdapter(ctx, books);
        recyclerView.setAdapter(adapter);

        // Insert into ScrollView
        ViewGroup insertPoint = contentView.findViewById(R.id.browse_books_layout);
        insertPoint.addView(recyclerView, -1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
