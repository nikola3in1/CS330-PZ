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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.model.Category;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class BrowseFragmentAdapter extends RecyclerView.Adapter<BrowseFragmentAdapter.ViewHolder>{

    private ArrayList<Category> categories;
    private Context context;


    public BrowseFragmentAdapter(Context context, ArrayList<Category> categories) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public BrowseFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder : called");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_browse_categorylist, viewGroup, false);
        return new BrowseFragmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseFragmentAdapter.ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder : called");

        Category category = categories.get(i);

        Glide.with(context)
                .asBitmap().
                load(category.getImageUrl()).
                into(viewHolder.image);
        viewHolder.title.setText(category.getTitle());
        viewHolder.image.setOnClickListener((e)->{
            Log.d(TAG, "onClick : clicked on an image"+ category.getTitle());
            Toast.makeText(context,  category.getTitle(),Toast.LENGTH_SHORT);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.category_image);
            title = itemView.findViewById(R.id.category_text);
        }
    }
}