package com.example.happy.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.MovieRatings;
import com.example.happy.queries.NoResult;
import com.example.happy.queries.SavedMovies;
import com.example.happy.queries.Utils;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieDetailsActivity extends AppCompatActivity {
    private List<MovieInfo> movieInfo;
    private List<MovieRatings> movieRating;
    private int currentUserId = -1;
    List<SavedMovies> currentMovieSaved = new ArrayList<>();
    boolean isSaved = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("CURRENT_USER_ID", -1);
        }
        // Show back button in actionbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView titlePrimary = findViewById(R.id.detailName);
        ImageView poster = findViewById(R.id.detailPoster);
        TextView titleOriginal = findViewById(R.id.detailTitle);
        TextView year = findViewById(R.id.detailYear);
        TextView duration = findViewById(R.id.detailDuration);
        TextView genre = findViewById(R.id.detailGenre);
        TextView averageEnjoyment = findViewById(R.id.enjoyTextView);
        TextView averageMeaningful = findViewById(R.id.meaningTextView);
        ImageView cs1 = findViewById(R.id.cs1);
        ImageView cs2 = findViewById(R.id.cs2);
        ImageView cs3 = findViewById(R.id.cs3);
        ImageView cs4 = findViewById(R.id.cs4);
        RatingBar enjoyful = findViewById(R.id.detailEnjoy);
        RatingBar meaningful = findViewById(R.id.detailMeaning);
        Button save = findViewById(R.id.detailSaveButton);
        Button rate = findViewById(R.id.detailRateButton);

        // Retrieve movieID
        String movieID = getIntent().getStringExtra("MOVIE_ID");

        // Query all information and ratings from the corresponding movieID
        try {
            movieInfo = Utils.executeQuery(
                    MovieInfo.class,
                    MovieDetailsActivity.this,
                    "select",
                    "*",
                    "movie_info",
                    "WHERE",
                    String.format("movie_id='%s'", movieID)
            );
            movieRating = Utils.executeQuery(
                    MovieRatings.class,
                    MovieDetailsActivity.this,
                    "select",
                    "*",
                    "movie_ratings",
                    "WHERE",
                    String.format("movie_id='%s'", movieID)
            );
        } catch (ExecutionException e) {
            Toast.makeText(MovieDetailsActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(MovieDetailsActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        MovieInfo thisMovieInfo = movieInfo.get(0);
        MovieRatings thisMovieRatings = movieRating.get(0);

        // Fill all placeholders
        titlePrimary.setText(thisMovieInfo.getPrimaryTitle());
        String imgURL = thisMovieInfo.getPosterUrl();
        if (!imgURL.equals("Unknown")){
            Picasso.get().load(imgURL).into(poster);
        } else {
            poster.setImageResource(R.drawable.no_poster);
        }
        titleOriginal.setText(thisMovieInfo.getOriginalTitle());
        year.setText(thisMovieInfo.getStartYear());
        duration.setText(thisMovieInfo.getRuntime() + " min");
        genre.setText(thisMovieInfo.getGenres());

        enjoyful.setRating(Float.parseFloat(thisMovieRatings.getAverageEnjoyment()));
        meaningful.setRating(Float.parseFloat(thisMovieRatings.getAverageMeaning()));
        averageEnjoyment.setText("Average enjoyment (" + thisMovieRatings.getVotesEnjoyment() + " ratings) :");
        averageMeaningful.setText("Average meaningfulness (" + thisMovieRatings.getVotesMeaning() + " ratings) :");

        ArrayList<String> orderedCS = Utils.getOrderedCSFromMovieRatings(movieRating.get(0));

        switch(orderedCS.size()){
            case 0:
                break;
            case 1:
                cs1.setImageResource(Utils.getCSResourceId(orderedCS.get(0)));
                break;
            case 2:
                cs1.setImageResource(Utils.getCSResourceId(orderedCS.get(0)));
                cs2.setImageResource(Utils.getCSResourceId(orderedCS.get(1)));
                break;
            case 3:
                cs1.setImageResource(Utils.getCSResourceId(orderedCS.get(0)));
                cs2.setImageResource(Utils.getCSResourceId(orderedCS.get(1)));
                cs3.setImageResource(Utils.getCSResourceId(orderedCS.get(2)));
                break;
            default:
                cs1.setImageResource(Utils.getCSResourceId(orderedCS.get(0)));
                cs2.setImageResource(Utils.getCSResourceId(orderedCS.get(1)));
                cs3.setImageResource(Utils.getCSResourceId(orderedCS.get(2)));
                cs4.setImageResource(Utils.getCSResourceId(orderedCS.get(3)));
                break;
        }

        try {
            currentMovieSaved = Utils.executeQuery(
                    SavedMovies.class,
                    MovieDetailsActivity.this,
                    "SELECT",
                    "saved_id",
                    "saved_movies",
                    "WHERE",
                    String.format("user_id=%s AND movie_id='%s'", currentUserId, thisMovieInfo.getMovieId())
            );
        } catch (ExecutionException e) {
            Toast.makeText(MovieDetailsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            Toast.makeText(MovieDetailsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        // if not empty
        if (!currentMovieSaved.toString().equals("[]")){
            isSaved = true;
            save.setBackgroundResource(R.drawable.save_button_light_fixedsize);
        }

        // Save button functionality
        save.setOnClickListener(v -> {
            // if the movie is saved and therefore clicked to be unsaved
            if (isSaved){

                try {
                    List<NoResult> deleteResult = Utils.executeQuery(
                            NoResult.class,
                            MovieDetailsActivity.this,
                            "delete",
                            "",
                            "saved_movies",
                            "where",
                            String.format("movie_id='%s' and user_id=%s", thisMovieInfo.getMovieId(), currentUserId)
                    );

                    if (!deleteResult.toString().equals("[]")){
                        save.setBackgroundResource(R.drawable.save_button_dark_fixedsize);
                    }

                } catch (ExecutionException e) {
                    Toast.makeText(MovieDetailsActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    Toast.makeText(MovieDetailsActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            }else{

                try {
                    List<NoResult> insertResult = Utils.executeQuery(
                            NoResult.class,
                            MovieDetailsActivity.this,
                            "insert",
                            "(user_id, movie_id)",
                            "saved_movies",
                            "values",
                            String.format("(%s, '%s')", currentUserId, thisMovieInfo.getMovieId())
                    );

                    if (!insertResult.toString().equals("[]")){
                        save.setBackgroundResource(R.drawable.save_button_light_fixedsize);
                    }

                } catch (ExecutionException e) {
                    Toast.makeText(MovieDetailsActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    Toast.makeText(MovieDetailsActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            }
            isSaved = !isSaved;
        });

        movieRating.get(0).getAll(); // idk if this is what I want
        //switch in historyadapteer


        // Rate button functionality
        rate.setOnClickListener(v -> {
            Intent intent = new Intent(this, RateActivity.class);
            intent.putExtra("SELECTED_MOVIE_ID", movieInfo.get(0).getMovieId());
            intent.putExtra("CURRENT_USER_ID", currentUserId);
            startActivity(intent);
        });
    }

    // Make sure to go to previous screen when back button is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
