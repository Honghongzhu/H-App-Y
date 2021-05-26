//package com.example.happy.data;
//
//import com.example.happy.R;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//public class MovieDatabase {
//    private static final HashMap<Integer, Movie> movies = new HashMap<>();
//
//    public static Movie getMovieById(String movieId) {
//        return movies.get(movieId);
//    }
//
//    @SuppressWarnings("unchecked")
//    public static LinkedList<Movie> getAllMovies() {
//        return new LinkedList<Movie>((List) Arrays.asList(movies.values().toArray()));
//    }
//
//    public static LinkedList<Movie> getSavedMovies() {
//        LinkedList<Movie> savedMovies = new LinkedList<>();
//        for(Movie movie : movies.values()){
//            if(movie.isSaved())
//                savedMovies.add(movie);
//        }
//        return savedMovies;
//    }
//
//    //TODO: this function is hard-coded for usability study
//    public static LinkedList<Movie> getCreativityMovies() {
//        LinkedList<Movie> creativityMovies = new LinkedList<>();
//        for(Movie movie : movies.values()){
//            if(movie.getCs().toLowerCase().contains("creativity"))
//                creativityMovies.add(movie);
//        }
//        return creativityMovies;
//    }
//
//
//    static {
//        movies.put(1, new Movie(
//                "1",
//                "The Imitation Game (2014)",
//                R.drawable.the_imitation_game,
//                false,
//                "curiosity"
//        ));
//        movies.put(2, new Movie(
//                "2",
//                "The Public (2018)",
//                R.drawable.the_public,
//                false,
//                "love_of_learning"
//        ));
//        movies.put(3, new Movie(
//                "3",
//                "Forrest Gump (1994)",
//                R.drawable.forrest_gump,
//                false,
//                "self_regulation"
//        ));
//        movies.put(4, new Movie(
//                "4",
//                "Juno (2007)",
//                R.drawable.juno,
//                false,
//                "social_intelligence"
//        ));
//        movies.put(5, new Movie(
//                "5",
//                "Freedom Writers (2007)",
//                R.drawable.freedom_writers,
//                false,
//                "creativity"
//        ));
//        movies.put(6, new Movie(
//                "6",
//                "Return to Oz (1985)",
//                R.drawable.return_to_oz,
//                false,
//                "creativity"
//        ));
//        movies.put(7, new Movie(
//                "7",
//                "Inside Out (2015)",
//                R.drawable.inside_out,
//                false,
//                "love"
//        ));
//        movies.put(8, new Movie(
//                "8",
//                "Bird Box (2018)",
//                R.drawable.bird_box,
//                false,
//                "perseverance"
//        ));
//        movies.put(9, new Movie(
//                "9",
//                " Before Midnight (2013)",
//                R.drawable.before_midnight,
//                false,
//                "honesty"
//        ));
//        movies.put(10, new Movie(
//                "10",
//                "Trolls (2016)",
//                R.drawable.trolls,
//                false,
//                "zest"
//        ));
//        movies.put(11, new Movie(
//                "11",
//                "The Aviator (2004)",
//                R.drawable.aviator,
//                false,
//                "creativity"
//        ));
//        movies.put(12, new Movie(
//                "12",
//                "Invictus (2009)",
//                R.drawable.invictus,
//                false,
//                "leadership"
//
//        ));
//    }
//}
