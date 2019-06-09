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

import com.bumptech.glide.Glide;
import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.activities.HomeActivity;
import com.nikola3in1.audiobooks.fragments.CategoryFragment;
import com.nikola3in1.audiobooks.fragments.menu.BrowseFragment;
import com.nikola3in1.audiobooks.model.Category;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class BrowseFragmentAdapter extends RecyclerView.Adapter<BrowseFragmentAdapter.ViewHolder> {

    private ArrayList<Category> categories;
    private Context context;
    private Boolean isSubcategory;

    public BrowseFragmentAdapter(Context context, ArrayList<Category> categories, Boolean isSubcategory) {
        this.categories = categories;
        this.context = context;
        this.isSubcategory = isSubcategory;
    }

    @NonNull
    @Override
    public BrowseFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_browse_categorylist, viewGroup, false);
        return new BrowseFragmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseFragmentAdapter.ViewHolder viewHolder, int i) {
        Category category = categories.get(i);

        Glide.with(context)
                .asBitmap().
                load(category.getImageUrl()).
                into(viewHolder.image);
        viewHolder.title.setText(category.getTitle());
        viewHolder.image.setOnClickListener((e) -> {
            if (isSubcategory) {
                // Clicked on subcategory
                displayBooksFragment(category);

            }else{
                // Clicked on category
                displaySubcategoies(category);
            }
        });

    }

    private void displayBooksFragment(Category category){
        Bundle categoriesData = new Bundle();
        categoriesData.putString("title", category.getTitle());
        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.setArguments(categoriesData);
        HomeActivity.displayFragment(categoryFragment);
    }

    private void displaySubcategoies(Category category) {
        // Setting new fragment with subcateories data
        Bundle subcategoriesData = new Bundle();
        subcategoriesData.putString("subcategoriesOf",category.getTitle());
        BrowseFragment subcategoriesFragment = new BrowseFragment();
        subcategoriesFragment.setArguments(subcategoriesData);
        // Calling FrameManager
        HomeActivity.displayFragment(subcategoriesFragment);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.category_image);
            title = itemView.findViewById(R.id.category_text);
        }
    }
}
