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

        ArrayList<Chapter> chapters = new ArrayList<>();

        chapters.add(new Chapter("uvod1", "asdasdas", "http://www.dkcmajdan.org.rs/data/audio_bajke/1.uvod.mp3"));
        chapters.add(new Chapter("uvod2", "asdasdas", "http://www.dkcmajdan.org.rs/data/audio_bajke/1.uvod.mp3"));
        chapters.add(new Chapter("uvod3", "asdasdas", "http://www.dkcmajdan.org.rs/data/audio_bajke/1.uvod.mp3"));
        chapters.add(new Chapter("uvod4", "asdasdas", "http://www.dkcmajdan.org.rs/data/audio_bajke/1.uvod.mp3"));
//        chapters.add(new Chapter("audio 1", "asdasdas", "http://192.168.0.69/audio/audio1.mp3"));
//        chapters.add(new Chapter("audio 2", "asdaaasd1as", "http://192.168.0.69/audio/audio2.mp3"));
//        chapters.add(new Chapter("audio 22", "asd423asdas", "http://192.168.0.69/audio/audio2.mp3"));
//        chapters.add(new Chapter("horse", "asdas12412das", "https://www.w3schools.com/html/horse.ogg"));
//        chapters.add(new Chapter("audio 23", "adas", "http://192.168.0.69/audio/audio2.mp3"));
        book.setLastPlayedChapter(new Chapter("audio 1", "asdasdas", "http://192.168.0.69/audio/audio1.mp3"));
        book.setChapters(chapters);

        Book book1 = new Book("Harry Potter and the deadly golows", "J.K. Rowling",
                "https://images-na.ssl-images-amazon.com/images/I/91-gMZT24AL.jpg");
        book1.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent at dolor vel justo tincidunt molestie a eu massa. Phasellus eu nisl ut tortor ullamcorper consectetur. Vivamus at arcu non mauris mollis luctus. In hac habitasse platea dictumst. Vivamus tristique faucibus condimentum. Quisque at aliquet massa, consectetur aliquet magna. Nam non velit nec erat molestie luctus vel a arcu. Cras scelerisque malesuada dictum. Suspendisse faucibus in mi sit amet semper. Phasellus eu consectetur velit.\nPhasellus mattis lorem auctor erat tincidunt placerat bibendum vitae ligula. Proin gravida rutrum eros at ullamcorper. Mauris vestibulum cursus neque vel euismod. Sed commodo mattis lorem, nec lobortis lectus cursus eget. Donec eu venenatis lectus, quis bibendum quam. Praesent finibus, lorem in sollicitudin vestibulum, elit risus suscipit nunc, dapibus ultrices turpis massa vel ex.\n");
        //4h 46min
        book1.setDuration(14124 + 35);
        book1.setReleasedDate(new Date(System.currentTimeMillis()));
        book1.setNarator("Nikola Pujic");
        book1.setRating(2.0);
        book1.setRatingsNumber(100);

        ArrayList<Chapter> chapters1 = new ArrayList<>();

        chapters1.add(new Chapter("uvod12", "asdasdas", "http://www.dkcmajdan.org.rs/data/audio_bajke/1.uvod.mp3"));
        chapters1.add(new Chapter("uvod22", "asdasdas", "http://www.dkcmajdan.org.rs/data/audio_bajke/1.uvod.mp3"));
        chapters1.add(new Chapter("uvod32", "asdasdas", "http://www.dkcmajdan.org.rs/data/audio_bajke/1.uvod.mp3"));
        chapters1.add(new Chapter("uvod42", "asdasdas", "http://www.dkcmajdan.org.rs/data/audio_bajke/1.uvod.mp3"));
//        chapters1.add(new Chapter("audio 1", "asdasdas", "http://192.168.0.69/audio/audio1.mp3"));
//        chapters1.add(new Chapter("audio 2", "asdaaasd1as", "http://192.168.0.69/audio/audio2.mp3"));
//        chapters1.add(new Chapter("audio 22", "asd423asdas", "http://192.168.0.69/audio/audio2.mp3"));
//        chapters1.add(new Chapter("horse", "asdas12412das", "https://www.w3schools.com/html/horse.ogg"));
//        chapters1.add(new Chapter("audio 23", "adas", "http://192.168.0.69/audio/audio2.mp3"));
//        book1.setLastPlayedChapter(new Chapter("audio 1", "asdasdas", "http://192.168.0.69/audio/audio1.mp3"));
        book1.setChapters(chapters1);


        books.add(book);
        books.add(book1);
        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);
        return books;
        // bajke -> http://www.dkcmajdan.org.rs/lat/audio-bajke/

//        Chunk audio2 = new Chunk("http://192.168.0.69/audio/audio2.mp3");
//        Chunk audio1 = new Chunk("http://192.168.0.69/audio/audio1.mp3");
//
//        Chunk chunk1 = new Chunk("http://www.dkcmajdan.org.rs/data/audio_bajke/1.uvod.mp3");
//        Chunk chunk1 = new Chunk("https://www.w3schools.com/html/horse.ogg");
//        Chunk chunk2 = new Chunk("http://www.dkcmajdan.org.rs/data/audio_bajke/2.zlatna_jabuka_i_devet_paunica.mp3");
//        Chunk chunk2 = new Chunk("https://www.w3schools.com/html/horse.ogg");
//        Chunk chunk3 = new Chunk("https://www.w3schools.com/html/horse.ogg");

    }
}
