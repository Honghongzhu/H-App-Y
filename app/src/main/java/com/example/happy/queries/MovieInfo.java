package com.example.happy.queries;

public class MovieInfo {
    private String columns;
    private String table;

    private String movieId;
    private String primaryTitle;
    private String originalTitle;
    private String startYear;
    private String runtime;
    private String genres;
    private String posterUrl;

    public String getMovieId() {
        return movieId;
    }

    public String getPrimaryTitle() {
        return primaryTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getStartYear() {
        return startYear;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenres() {
        return genres;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public MovieInfo(String movieId, String primaryTitle, String originalTitle, String startYear, String runtime, String genres, String posterUrl) {
        this.movieId = movieId;
        this.primaryTitle = primaryTitle;
        this.originalTitle = originalTitle;
        this.startYear = startYear;
        this.runtime = runtime;
        this.genres = genres;
        this.posterUrl = posterUrl;
    }

}
