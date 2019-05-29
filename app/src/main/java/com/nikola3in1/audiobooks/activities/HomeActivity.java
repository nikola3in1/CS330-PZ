package com.nikola3in1.audiobooks.activities;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.model.Book;
import com.nikola3in1.audiobooks.model.Category;
import com.nikola3in1.audiobooks.adapters.HomeCategoryAdapter;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    LinearLayout linearLayout;

    // TEST DATA
    ArrayList<Category> getTestData(){
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("Harry Potter and the sorcerers stone", "J.K. Rowling", "https://images-na.ssl-images-amazon.com/images/I/51HSkTKlauL._SX346_BO1,204,203,200_.jpg"));
        books.add(new Book("Harry Potter and the goblet of fire", "J.K. Rowling", "https://images-na.ssl-images-amazon.com/images/I/71ykU-RQ0nL._SY606_.jpg"));
        books.add(new Book("Harry Potter and the chamber of secrets", "J.K. Rowling", "https://hpmedia.bloomsbury.com/rep/s/9781408855904_309575.jpeg"));
        books.add(new Book("Harry Potter and the chamber of secrets", "J.K. Rowling", "https://hpmedia.bloomsbury.com/rep/s/9781408855904_309575.jpeg"));
        books.add(new Book("Harry Potter and the chamber of secrets", "J.K. Rowling", "https://hpmedia.bloomsbury.com/rep/s/9781408855904_309575.jpeg"));

        ArrayList<Category> categories = new ArrayList<Category>() {{
            this.add(new Category("Top charts", books));
            this.add(new Category("New titles", books));
            this.add(new Category("Most popular", books));
            this.add(new Category("Most popular1", books));
            this.add(new Category("Most popular2", books));
            this.add(new Category("Most popular2", books));
            this.add(new Category("Most popular2", books));
        }};
        return categories;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        linearLayout = new LinearLayout(this);

        // Fetch data from backend ...
        ArrayList<Category> categories = getTestData();
        initCategories(categories);
    }

    private void initCategories(ArrayList<Category> categories) {
        // Add Categories to home page
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (Category c : categories) {
            // Create LinearLayout for category, here we add RecyclerView and TextView
            LinearLayout v = new LinearLayout(this);
            v.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
            v.setOrientation(LinearLayout.VERTICAL);

            //Category name
            TextView txt = new TextView(this);
            txt.setText(c.getName());
            txt.setTextSize(24);

            // Init RecyclerView
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            RecyclerView recyclerView = new RecyclerView(this);
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT));
            recyclerView.setLayoutManager(layoutManager);
            HomeCategoryAdapter adapter = new HomeCategoryAdapter(this, c.getBooks());
            recyclerView.setAdapter(adapter);

            // Add nodes to new LinearLayout
            v.addView(txt);
            v.addView(recyclerView);

            // Insert into ScrollView
            ViewGroup insertPoint = (ViewGroup) findViewById(R.id.home_categories_layout);
            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        }
    }
}
