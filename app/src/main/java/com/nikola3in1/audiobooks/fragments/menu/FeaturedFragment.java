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
import com.nikola3in1.audiobooks.adapters.FeaturedFragmentAdapter;
import com.nikola3in1.audiobooks.model.DummyData;
import com.nikola3in1.audiobooks.model.FeaturedList;
import com.synnapps.carouselview.CarouselView;

import java.util.ArrayList;

public class FeaturedFragment extends Fragment {
    private final String TITLE = "Featured";
    private Context ctx;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(TITLE);
        ctx = getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_featured, container, false);

        // Fetch data from backend ...
        ArrayList<FeaturedList> featuredList = DummyData.getFeaturedTestData();

        getActivity().runOnUiThread(() -> {
            initFeaturedTitles(contentView);
            initFeaturedLists(contentView, featuredList);
        });
        return contentView;
    }

    private void initFeaturedTitles(View contentView) {
        final int[] pics = new int[]{R.raw.ad1, R.raw.ad2, R.raw.ad3};
        CarouselView carouselView = contentView.findViewById(R.id.carouselView);
        carouselView.setPageCount(pics.length);
        carouselView.setImageListener((position, imageView) -> {
            imageView.setImageResource(pics[position]);
        });
    }

    private void initFeaturedLists(View contentView, ArrayList<FeaturedList> featuredList) {

        // Add Categories to home page
        for (FeaturedList c : featuredList) {

            // Create LinearLayout for category, here we add RecyclerView and TextView
            LinearLayout v = new LinearLayout(ctx);
            v.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
            v.setOrientation(LinearLayout.VERTICAL);

            // FeaturedList name
            TextView txt = new TextView(ctx);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(20, 20, 2, 2);
            txt.setLayoutParams(params);
            txt.setText(c.getName());
            txt.setTextSize(24);

            // Init RecyclerView
            LinearLayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);
            RecyclerView recyclerView = new RecyclerView(ctx);
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
            recyclerView.setLayoutManager(layoutManager);
            FeaturedFragmentAdapter adapter = new FeaturedFragmentAdapter(ctx, c.getBooks());
            recyclerView.setAdapter(adapter);

            // Add nodes to new LinearLayout
            v.addView(txt);
            v.addView(recyclerView);

            // Insert into ScrollView
            ViewGroup insertPoint = contentView.findViewById(R.id.featured_lists_layout);
            insertPoint.addView(v, -1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }


}
