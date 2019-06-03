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
            categories = getSubcategoriesTestData();
        } else {
            // Categories, Fetch data from backend ...
            categories = getCategoriesTestData();
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


    /* Dummy test data */
    // Categories
    private ArrayList<Category> getCategoriesTestData() {
        return new ArrayList<Category>() {{
            this.add(new Category("Drama", "https://static7.depositphotos.com/1002238/754/v/450/depositphotos_7544717-stock-illustration-comedy-and-tragedy-theater-masks.jpg"));
            this.add(new Category("Comedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("Comedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("Comedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("Comedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("Comedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("Comedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("Comedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("Comedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("Comedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("Comedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("Comedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
        }};
    }

    // Subcategories
    private ArrayList<Category> getSubcategoriesTestData() {
        return new ArrayList<Category>() {{
            this.add(new Category("SubDrama", "https://static7.depositphotos.com/1002238/754/v/450/depositphotos_7544717-stock-illustration-comedy-and-tragedy-theater-masks.jpg"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("SubComedy", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
        }};
    }
}
