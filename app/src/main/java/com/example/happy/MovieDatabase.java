package com.example.happy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MovieDatabase {
    private static final HashMap<Integer, Movie> movies = new HashMap<>();

    public static Movie getMovieById(int movieId) {
        return movies.get(movieId);
    }

    @SuppressWarnings("unchecked")
    public static LinkedList<Movie> getAllMovies() {
        return new LinkedList<Movie>((List) Arrays.asList(movies.values().toArray()));
    }

    public static ArrayList<Movie> getSavedMovies() {
        ArrayList<Movie> savedMovies = new ArrayList<>();
        for(Movie movie : movies.values()){
            if(movie.isSaved())
                savedMovies.add(movie);
        }
        return savedMovies;
    }

    static {
        movies.put(1, new Movie(
                1,
                "The Imitation Game (2014)",
                R.drawable.the_imitation_game,
                false
        ));
        movies.put(2, new Movie(
                2,
                "The Public (2018)",
                R.drawable.the_public,
                false
        ));
        movies.put(3, new Movie(
                3,
                "Forrest Gump (1994)",
                R.drawable.forrest_gump,
                false
        ));
        movies.put(4, new Movie(
                4,
                "Juno (2007)",
                R.drawable.juno,
                false
        ));
        movies.put(5, new Movie(
                5,
                "Freedom Writers (2007)",
                R.drawable.freedom_writers,
                false
        ));
        movies.put(6, new Movie(
                6,
                "Rear Window (1954)",
                R.drawable.rear_window,
                false
        ));
        movies.put(7, new Movie(
                7,
                "Whale Rider (2003)",
                R.drawable.whale_rider,
                false
        ));
        movies.put(8, new Movie(
                8,
                "Bird Box (2018)",
                R.drawable.bird_box,
                false
        ));
        movies.put(9, new Movie(
                9,
                " Before Midnight (2013)",
                R.drawable.before_midnight,
                false
        ));
        movies.put(10, new Movie(
                10,
                "Trolls (2016)",
                R.drawable.trolls,
                false
        ));
        movies.put(11, new Movie(
                11,
                "The Blind Side (2009)",
                R.drawable.the_blind_side,
                false
        ));
        movies.put(12, new Movie(
                12,
                "Invictus (2009)",
                R.drawable.invictus,
                false
        ));
    }
}
