package com.nikola3in1.audiobooks.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.model.Book;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class CategoryFragmentAdapter extends RecyclerView.Adapter<CategoryFragmentAdapter.ViewHolder> {

    // even number of elements, ~20 elements = 10 pairs
    private ArrayList<ArrayList<Book>> bookPairs = new ArrayList<>();
    private Context context;

    public CategoryFragmentAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        for (int i = 0; i < books.size() - 1; i += 2) {
            bookPairs.add(new ArrayList<>(books.subList(i, i + 2)));
        }
    }

    @NonNull
    @Override
    public CategoryFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder : called");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_category_booklist, viewGroup, false);
        return new CategoryFragmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryFragmentAdapter.ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder : called");

        // Books which will be in same row
        ArrayList<Book> pair = bookPairs.get(i);

        Book leftBook = pair.get(0);
        Book rightBook = pair.get(1);

        //Loading images
        Glide.with(context)
                .asBitmap().
                load(leftBook.getImageUrl()).
                into(viewHolder.leftImage);
        Glide.with(context)
                .asBitmap().
                load(rightBook.getImageUrl()).
                into(viewHolder.rightImage);

        //Setting titles and authors
        viewHolder.leftTitle.setText(leftBook.getTitle());
        viewHolder.rightTitle.setText(rightBook.getTitle());
        viewHolder.leftAuthor.setText(leftBook.getAuthor());
        viewHolder.rightAuthor.setText(rightBook.getAuthor());

        //Events
        viewHolder.leftImage.setOnClickListener((e) -> {
            Log.d(TAG, "onClick : clicked on a left book" + leftBook.getTitle());
//            displayBookFragment(leftBook);
        });

        viewHolder.rightImage.setOnClickListener((e) -> {
            Log.d(TAG, "onClick : clicked on a right book" + rightBook.getTitle());
//            displayBookFragment(rightImage);
        });
    }

    private void displayBookFragment(Book book) throws Exception {
        throw new Exception("NOT IMPLEMENTED YET");
    }

    @Override
    public int getItemCount() {
        return bookPairs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //Left book
        ImageView leftImage;
        TextView leftTitle;
        TextView leftAuthor;

        //Right book
        ImageView rightImage;
        TextView rightTitle;
        TextView rightAuthor;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftImage = itemView.findViewById(R.id.category_book_image1);
            leftTitle = itemView.findViewById(R.id.category_book_title1);
            leftAuthor = itemView.findViewById(R.id.category_book_author1);
            rightImage = itemView.findViewById(R.id.category_book_image2);
            rightTitle = itemView.findViewById(R.id.category_book_title2);
            rightAuthor = itemView.findViewById(R.id.category_book_author2);
        }
    }
}
