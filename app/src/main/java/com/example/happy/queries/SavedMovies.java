package com.example.happy.queries;

public class SavedMovies {

    private String savedId;
    private String userId;
    private String movieId;

    public String getSavedId() {
        return savedId;
    }

    public String getUserId() {
        return userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public SavedMovies(String savedId, String userId, String movieId) {
        this.savedId = savedId;
        this.userId = userId;
        this.movieId = movieId;
    }
}
