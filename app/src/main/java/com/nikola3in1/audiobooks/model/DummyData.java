package com.nikola3in1.audiobooks.model;

import java.sql.Date;
import java.util.ArrayList;

public class DummyData {

    /* Dummy test data */
    // Categories
    public static ArrayList<Category> getCategoriesTestData() {
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
    public static ArrayList<Category> getSubcategoriesTestData() {
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

    // TEST DATA
    public static ArrayList<FeaturedList> getFeaturedTestData() {
        ArrayList<Book> books = getBooks();
        ArrayList<FeaturedList> categories = new ArrayList<FeaturedList>() {{
            this.add(new FeaturedList("Top charts", books));
            this.add(new FeaturedList("New titles", books));
            this.add(new FeaturedList("Most popular", books));
            this.add(new FeaturedList("Most popular1", books));
            this.add(new FeaturedList("Most popular2", books));
            this.add(new FeaturedList("Most popular2", books));
            this.add(new FeaturedList("Most popular2", books));
        }};
        return categories;
    }

    // TEST DATA
    public static ArrayList<Book> getBooks() {
        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book("Harry Potter and the sorcerers stone", "J.K. Rowling",
                "https://images-na.ssl-images-amazon.com/images/I/51HSkTKlauL._SX346_BO1,204,203,200_.jpg");
        book.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent at dolor vel justo tincidunt molestie a eu massa. Phasellus eu nisl ut tortor ullamcorper consectetur. Vivamus at arcu non mauris mollis luctus. In hac habitasse platea dictumst. Vivamus tristique faucibus condimentum. Quisque at aliquet massa, consectetur aliquet magna. Nam non velit nec erat molestie luctus vel a arcu. Cras scelerisque malesuada dictum. Suspendisse faucibus in mi sit amet semper. Phasellus eu consectetur velit.\nPhasellus mattis lorem auctor erat tincidunt placerat bibendum vitae ligula. Proin gravida rutrum eros at ullamcorper. Mauris vestibulum cursus neque vel euismod. Sed commodo mattis lorem, nec lobortis lectus cursus eget. Donec eu venenatis lectus, quis bibendum quam. Praesent finibus, lorem in sollicitudin vestibulum, elit risus suscipit nunc, dapibus ultrices turpis massa vel ex.\n");
        //4h 46min
        book.setDuration(17160 + 35);
        book.setReleasedDate(new Date(System.currentTimeMillis()));
        book.setNarator("Nikola Pujic");
        book.setRating(4.8);
        book.setRatingsNumber(1230);

        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);
        return books;
    }
}
