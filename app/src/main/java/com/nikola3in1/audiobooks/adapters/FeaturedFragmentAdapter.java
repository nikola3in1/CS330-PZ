package com.nikola3in1.audiobooks.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.activities.HomeActivity;
import com.nikola3in1.audiobooks.fragments.BookFragment;
import com.nikola3in1.audiobooks.model.Book;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class FeaturedFragmentAdapter extends RecyclerView.Adapter<FeaturedFragmentAdapter.ViewHolder>{

    // Data
    private ArrayList<Book> books;
    private Context context;

    public FeaturedFragmentAdapter(Context context, ArrayList<Book> books) {
        this.books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_featured_booklist, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Book book = books.get(i);

        Glide.with(context)
                .asBitmap().
                load(book.getImageUrl()).
                into(viewHolder.image);
        viewHolder.author.setText(book.getAuthor());
        viewHolder.title.setText(book.getTitle());
        viewHolder.image.setOnClickListener((e)->{
            displayBookFragment(book);
        });
    }

    private void displayBookFragment(Book book){
        Bundle categoriesData = new Bundle();
        categoriesData.putSerializable("book", book);
        BookFragment bookFragment = new BookFragment();
        bookFragment.setArguments(categoriesData);
        HomeActivity.displayFragment(bookFragment);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView author;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.book_image);
            title = itemView.findViewById(R.id.book_title);
            author = itemView.findViewById(R.id.book_author);
        }
    }
}

