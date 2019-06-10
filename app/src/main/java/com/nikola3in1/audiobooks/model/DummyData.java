package com.nikola3in1.audiobooks.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

public class DummyData {

    /* Dummy test data */
    // Categories
    public static ArrayList<Category> getCategoriesTestData() {
        return new ArrayList<Category>() {{
            this.add(new Category("Biography & Memoir", "https://image.shutterstock.com/image-illustration/white-book-upright-human-head-260nw-767127346.jpg"));
            this.add(new Category("Business & Economics", "https://www.master-and-more.eu/fileadmin/user_upload/Find_your_Masters/Master_degree_programmes/business_and_economics.jpg"));
            this.add(new Category("Comedy", "https://st4.depositphotos.com/3396639/20865/i/1600/depositphotos_208653038-stock-photo-cheerful-hipster-guy-watching-comedy.jpg"));
            this.add(new Category("Drama", "http://res.freestockphotos.biz/pictures/5/5065-illustration-of-funny-novelty-glasses-pv.png"));
            this.add(new Category("Fiction & Literature", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTs7F3LAR6asWwdIK01Y9Wy8Tpcs24vT9amm-HzN3FuDpGqoyp99Q"));
            this.add(new Category("Health & Wellness", "https://image.shutterstock.com/image-photo/yoga-on-beach-group-people-260nw-374810599.jpg"));
            this.add(new Category("History", "https://previews.123rf.com/images/nejron/nejron0812/nejron081200162/3985302-old-antique-clock.jpg"));
        }};
    }

    // Subcategories
    public static ArrayList<Category> getSubcategoriesTestData(Category category) {
        return new ArrayList<Category>() {{
            this.add(new Category("Sub"+category.getTitle(), category.getImageUrl()));
            this.add(new Category("Sub1"+category.getTitle(), category.getImageUrl()));
            this.add(new Category("Sub2"+category.getTitle(), category.getImageUrl()));
            this.add(new Category("Sub3"+category.getTitle(), category.getImageUrl()));
        }};
    }

    // TEST DATA
    public static ArrayList<FeaturedList> getFeaturedTestData() {
        ArrayList<FeaturedList> categories = new ArrayList<FeaturedList>() {{
            this.add(new FeaturedList("Top charts", getBooks()));
            this.add(new FeaturedList("New titles", getBooks()));
            this.add(new FeaturedList("Most popular", getBooks()));
            this.add(new FeaturedList("Viral", getBooks()));
        }};
        return categories;
    }


    public static ArrayList<Chapter> getChapters(){
        // bajke -> http://www.dkcmajdan.org.rs/lat/audio-bajke/
        ArrayList<Chapter> chapters = new ArrayList<>();
        /*
        * Uvod  -  /data/audio_bajke/1.uvod.mp3"
         Zlatna jabuka i devet paunica  -  /data/audio_bajke/2.zlatna_jabuka_i_devet_paunica.mp3" target="_blank" title="Zlatna jabuka i devet paunica
         Biberče  -  /data/audio_bajke/3.biberce.mp3" target="_blank" title="Biberče
         Čardak ni na nebu ni na zemlji  -  /data/audio_bajke/4.cardak_ni_na_nebu_ni_na_zemlji.mp3" target="_blank" title="Čardak ni na nebu ni na zemlji
         Baš čelik  -  /data/audio_bajke/5.bas_celik.mp3"
         Vasilisa premudra  -  /data/audio_bajke/6.vasilisa_premudra.mp3"
         Riba i prsten  -  /data/audio_bajke/7.riba_i_prsten.mp3"
         Priča o palčici  -  /data/audio_bajke/8.prica_o_palcici.mp3"
         Ružno pače  -  /data/audio_bajke/9.ruzno_pace.mp3"
         Zlatne cipele  -  /data/audio_bajke/10.zlatne_cipele.mp3"
         Vuk i sedam jarića  -  /data/audio_bajke/11.vuk_i_sedam_jarica.mp3"
         Snežna kraljica  -  /data/audio_bajke/12.snezna_kraljica.mp3"
         Trgovac i papagaj  -  /data/audio_bajke/13.trgovac_i_papagaj.mp3"
         Aladin i čarobna lampa  -  /data/audio_bajke/14.aladin_i_carobna_lampa.mp3"
         Tri praseta  -  /data/audio_bajke/15.tri_praseta.mp3"
         Ivica i Marica  -  /data/audio_bajke/16.ivica_i_marica.mp3"
        * */

        chapters.add(new Chapter("Uvod", "Uvod", "http://www.dkcmajdan.org.rs/data/audio_bajke/1.uvod.mp3"));
        chapters.add(new Chapter("Zlatna jabuka i devet paunica", "Zlatna jabuka i devet paunica", "http://www.dkcmajdan.org.rs/data/audio_bajke/2.zlatna_jabuka_i_devet_paunica.mp3"));
        chapters.add(new Chapter("Biberče", "Biberče", "http://www.dkcmajdan.org.rs/data/audio_bajke/3.biberce.mp3"));
        chapters.add(new Chapter("Čardak ni na nebu ni na zemlji", "Čardak ni na nebu ni na zemlji", "http://www.dkcmajdan.org.rs/data/audio_bajke/4.cardak_ni_na_nebu_ni_na_zemlji.mp3"));
        chapters.add(new Chapter("Vasilisa premudra", "Vasilisa premudra", "http://www.dkcmajdan.org.rs/data/audio_bajke/6.vasilisa_premudra.mp3"));
//        chapters.add(new Chapter("Riba i prsten", "Riba i prsten", " http://www.dkcmajdan.org.rs/data/audio_bajke/7.riba_i_prsten.mp3"));
        chapters.add(new Chapter("Priča o palčici", "Priča o palčici", "http://www.dkcmajdan.org.rs/data/audio_bajke/8.prica_o_palcici.mp3"));
        chapters.add(new Chapter("Ružno pače", "Ružno pače", "http://www.dkcmajdan.org.rs/data/audio_bajke/9.ruzno_pace.mp3"));
        chapters.add(new Chapter("Zlatne cipele", "Zlatne cipele", "http://www.dkcmajdan.org.rs/data/audio_bajke/10.zlatne_cipele.mp3"));
        chapters.add(new Chapter("Vuk i sedam jarića ", "Vuk i sedam jarića ", "http://www.dkcmajdan.org.rs/data/audio_bajke/11.vuk_i_sedam_jarica.mp3"));
        chapters.add(new Chapter("Snežna kraljica", "Snežna kraljica", "http://www.dkcmajdan.org.rs/data/audio_bajke/12.snezna_kraljica.mp3"));
        chapters.add(new Chapter("Trgovac i papagaj", "Trgovac i papagaj", "http://www.dkcmajdan.org.rs/data/audio_bajke/13.trgovac_i_papagaj.mp3"));
        chapters.add(new Chapter("Aladin i čarobna lampa", "Aladin i čarobna lampa", "http://www.dkcmajdan.org.rs/data/audio_bajke/14.aladin_i_carobna_lampa.mp3"));
        chapters.add(new Chapter("Tri praseta", "Tri praseta", "http://www.dkcmajdan.org.rs/data/audio_bajke/15.tri_praseta.mp3"));
        chapters.add(new Chapter("Ivica i Marica", "Ivica i Marica", "http://www.dkcmajdan.org.rs/data/audio_bajke/16.ivica_i_marica.mp3"));

        return chapters;
    }

    // TEST DATA
    public static ArrayList<Book> getBooks() {

        //Book 0
        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book("Audio Bajke", "Narodne bajke",
                "http://www.dkcmajdan.org.rs/data/images/audio-bajke-web.jpg");
        book.setDescription("Slušanje dečijih bajki i priča pomaže razvoju pažnje i imaginacije. Stimuliše ljubav prema rečima. Obrazovnog je karaktera i pre svega, dobra je zabava.\n" +
                "\n" +
                "Audio bajke su takođe, jedan od programa koji je prilagođen za slepu decu – to je dobar način da i oni dobiju pristup literaturi kroz zvučni prikaz.\n" +
                "\n" +
                "Projekat “Audio bajke” je započet 2009. godine i obuhvatio je zvučnu produkciju 15 najpoznatijih svetskih i domaćih bajki za decu.");
            //
        //3h 12min
        book.setDuration(11261);
        Calendar calendar = new GregorianCalendar();
        calendar.set(2019,6,10);
        book.setReleasedDate(new Date(calendar.getTimeInMillis()));
        book.setNarator("Milena Mihailović");
        book.setRating(4.8);
        book.setRatingsNumber(132);
        book.setChapters(getChapters());

        // Book 1
        Book book1 = new Book("Hitchhiker's Guide to the Galaxy", "Douglas Adams",
                "https://m.media-amazon.com/images/I/51e9B6ehTGL._SL500_.jpg");
        book1.setDescription("How shall we begin?\n" +
                "\n" +
                "This is the story of a book called The Hitchhiker’s Guide to the Galaxy—not an Earth book, never published on Earth and, until the terrible catastrophe occurred, never seen or even heard of by any Earthman. Nevertheless, a wholly remarkable book.\n" +
                "\n" +
                "or\n" +
                "\n" +
                "This is the story of The Hitchhiker’s Guide to the Galaxy, a number-one best seller in England, a weekly radio series with millions of fanatic listeners, and soon to be a television spectacle on both sides of the Atlantic Ocean.\n" +
                "\n" +
                "or\n" +
                "\n" +
                "This is the story of Arthur Dent, who, secnds before Earth is demolished to make way for a galactic freeway, is plucked off the planet by his friend, Ford Prefect, who has been posing as an out-of-work actor for the last fifteen years but is really a researcher for the revised edition of The Hitchhiker’s Guide to the Galaxy. Together they begin a journey through the galaxy aided by quotes from The Hitchhiker’s Guide to the Galaxy, with the words don’t panic written on the front. (“A towel is about the most massively useful thing an interstellar hitchhiker can have.”)\n" +
                "\n" +
                "In their travels they meet:\n" +
                "•Zaphod Beeblebrox—the two-headed, three-armed ex-hippie and totally out-to-lunch President of the Galaxy\n" +
                "•Trillian—Zaphod’s girl friend, formerly Tricia McMillan, whom Arthur once tried to pick up at a cocktail party\n" +
                "•Marvin—a paranoid android, a brilliant but chronically depressed robot\n" +
                "•Veet Voojagig—former graduate student obsessed with the disappearance of all the ballpoint pens he bought over the years\n" +
                "\n" +
                "To find the answers to these burning questions: Why are we born? Why do we die? And why do we spend so much time in between wearing digital watches? read The Hitchhiker’s Guide to the Galaxy. But remember . . . don’t panic, and don’t forget to bring a towel.\n" +
                "\n");
        //4h 46min
        book1.setDuration(21060);
        book1.setReleasedDate(new Date(System.currentTimeMillis()));
        book1.setNarator("Stephen Fry");
        book1.setRating(4.9);
        book1.setRatingsNumber(5676);
        book1.setChapters(getChapters());


        // Book 2
        Book book2 = new Book("Sherlock Holmes: The Definitive Collection", "Arthur Conan Doyle",
                "https://m.media-amazon.com/images/I/519q0gOMn9L._SL500_.jpg");
        book2.setDescription("Ever since he made his first appearance in A Study In Scarlet, Sherlock Holmes has enthralled and delighted millions of fans throughout the world. Now Audible is proud to present Arthur Conan Doyle's Sherlock Holmes: The Definitive Collection, read by Stephen Fry. A lifelong fan of Doyle's detective fiction, Fry has narrated the complete works of Sherlock Holmes - four novels and five collections of short stories. And, exclusively for Audible, Stephen has written and narrated nine insightful, intimate and deeply personal introductions to each title. \n" +
                "\n" +
                "Stephen Fry is an English actor, screenwriter, author, playwright, journalist, comedian, television presenter, film director and all round national treasure. He is the acclaimed narrator of J.K. Rowling's Harry Potter audiobooks and most recently recorded The Tales of Max Carrados for Audible Studios. Stephen has contributed columns and articles to newspapers and magazines, appears frequently on radio and has written four novels and three volumes of autobiography. ");
        //4h 46min
        book2.setDuration(259020);
        book2.setReleasedDate(new Date(System.currentTimeMillis()));
        book2.setNarator("Stephen Fry");
        book2.setRating(4.8);
        book2.setRatingsNumber(7328);
        book2.setChapters(getChapters());


        // Book 3
        Book book3 = new Book("Harry Potter and the Chamber of Secrets, Book 2", "J.K. Rowling",
                "https://m.media-amazon.com/images/I/619tkhTTJzL._SL500_.jpg");
        book3.setDescription("\"'There is a plot, Harry Potter. A plot to make most terrible things happen at Hogwarts School of Witchcraft and Wizardry this year.'\"\n" +
                "\n" +
                "Harry Potter's summer has included the worst birthday ever, doomy warnings from a house-elf called Dobby, and rescue from the Dursleys by his friend Ron Weasley in a magical flying car! Back at Hogwarts School of Witchcraft and Wizardry for his second year, Harry hears strange whispers echo through empty corridors - and then the attacks start. Students are found as though turned to stone... Dobby's sinister predictions seem to be coming true.\n" +
                "\n" +
                "Theme music composed by James Hannigan.\n" +
                "\n" +
                "Listen to Harry Potter and the Prisoner of Azkaban here.\n" +
                "©1998 J.K. Rowling (P)2015 J.K. Rowling");
        //4h 46min
        book3.setDuration(36480);
        book3.setReleasedDate(new Date(System.currentTimeMillis()));
        book3.setNarator("Stephen Fry");
        book3.setRating(4.9);
        book3.setRatingsNumber(11599);
        book3.setChapters(getChapters());


        // Book 4
        Book book4 = new Book("A Game of Thrones", "George R. R. Martin",
                "https://m.media-amazon.com/images/I/51AO0fWIeeL._SL500_.jpg");
        book4.setDescription("Game of Thrones Book One: A Song of Ice and Fire written by George R. R. Martin is an immensely popular fantasy series audiobook narrated by veteran British actor Roy Dotrice. Now also a leading Sky Atlantic TV series by HBO. Listeners are introduced to an addictive plot and unforgettable characters in this first installment. Time feels lost in the harshness of endless brutal seasons, impenetrable landscapes and the constant, unwavering battle for the Iron Throne. The Throne means survival. Dark forces, lust and bloodshed will be used to get to it. Available from Audible now.");
        //4h 46min
        book4.setDuration(121500);
        book4.setReleasedDate(new Date(System.currentTimeMillis()));
        book4.setNarator("Roy Dotrice");
        book4.setRating(4.7);
        book4.setRatingsNumber(13467);
        book4.setChapters(getChapters());


        books.add(book);
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);

        Collections.shuffle(books);
        return books;

    }
}
