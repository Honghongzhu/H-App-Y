package com.example.happy.screens;

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
import com.example.happy.queries.SavedMovies;
import com.example.happy.queries.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieDetailsActivity extends AppCompatActivity {
    private List<MovieInfo> movieInfo;
    private List<MovieRatings> movieRating;
    private int currentUserId = -1;

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


        // Fill all placeholders
        titlePrimary.setText(movieInfo.get(0).getPrimaryTitle());
        String imgURL = movieInfo.get(0).getPosterUrl();
        Picasso.get().load(imgURL).into(poster);
        titleOriginal.setText(movieInfo.get(0).getOriginalTitle());
        year.setText(movieInfo.get(0).getStartYear());
        duration.setText(movieInfo.get(0).getRuntime() + " min");
        genre.setText(movieInfo.get(0).getGenres());
        enjoyful.setRating(Float.parseFloat(movieRating.get(0).getAverageEnjoyment()));
        meaningful.setRating(Float.parseFloat(movieRating.get(0).getAverageMeaning()));

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
