package com.example.happy.queries;

public class SavedMovies {

    private int savedId;
    private int userId;
    private String movieId;

    public int getSavedId() { return savedId; }

    public int getUserId() { return userId; }

    public String getMovieId() {
        return movieId;
    }

    public SavedMovies(int userId, String androidId) {
        this.savedId = savedId;
        this.userId = userId;
        this.movieId = movieId;
    }
}
