package com.nikola3in1.audiobooks.fragments.menu;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.adapters.BrowseFragmentAdapter;
import com.nikola3in1.audiobooks.model.DummyData;
import com.nikola3in1.audiobooks.model.Category;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

/*This fragment is used for representation of both categories and subcategories*/
public class BrowseFragment extends Fragment {
    private final String TITLE = "Browse";
    private Boolean isSubcategory = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(TITLE);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_browse, container, false);
        ArrayList<Category> categories;
        Bundle args = getArguments();

        if (args != null && args.get("subcategoriesOf") != null) {
            // Subcategories, args passed from BrowseFragmentAdapter,
            // from onClick event
            isSubcategory = true;
            String parentCategory = (String) args.get("subcategoriesOf");
            Log.d(TAG,"Getting subcategories of " + parentCategory);
            categories = DummyData.getSubcategoriesTestData();
        } else {
            // Categories, Fetch data from backend ...
            categories = DummyData.getCategoriesTestData();
        }
        initCategories(contentView, categories);
        return contentView;
    }

    private void initCategories(View contentView, ArrayList<Category> categories) {
        Context ctx = getActivity().getApplicationContext();

        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = new RecyclerView(ctx);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        recyclerView.setLayoutManager(layoutManager);
        BrowseFragmentAdapter adapter = new BrowseFragmentAdapter(ctx, categories, this.isSubcategory);
        recyclerView.setAdapter(adapter);

        // Insert into ScrollView
        ViewGroup insertPoint = contentView.findViewById(R.id.browse_categories_layout);
        insertPoint.addView(recyclerView, -1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private ArrayList<Category> fetchCategories() throws Exception {
        throw new Exception("NOT IMPLEMENTED YET");
    }

    private ArrayList<Category> fetchSubcategories() throws Exception {
        throw new Exception("NOT IMPLEMENTED YET");
    }



}
