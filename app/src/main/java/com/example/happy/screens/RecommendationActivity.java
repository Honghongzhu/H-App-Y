package com.example.happy.screens;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.adapters.MovieAdapter;
import com.example.happy.adapters.RankAdapter;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.MovieRatings;
import com.example.happy.queries.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class RecommendationActivity extends AppCompatActivity {

    private List<MovieRatings> resultMovieRatings;
    private List<MovieInfo> allMovieInfo;
    private int currentUserId = -1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("CURRENT_USER_ID", -1);
        }

        ArrayList<String> chosenCS = (ArrayList<String>) getIntent().getSerializableExtra("chosenCS");
        String listCS = String.join(", ", chosenCS);
        String listSumCS = String.join(" + ", chosenCS);
        StringBuilder listCountCS = new StringBuilder();
        for(String cs: chosenCS) {
            listCountCS.append(String.format("(CASE WHEN SUM(%s)>0 THEN 1 ELSE 0 END)+", cs));
        }
        listCountCS.delete(listCountCS.length()-1, listCountCS.length());

        // Query all movieRatings with the requested CS of the user
        try {
            resultMovieRatings = Utils.executeQuery(
                    MovieRatings.class,
                    RecommendationActivity.this,
                    "select",
                    String.format("movie_id, average_enjoyment, average_meaning, %s, SUM(%s) AS COUNTVOTES, %s AS COUNTCS", listCS, listSumCS, listCountCS.toString()),
                    "movie_ratings",
                    "GROUP BY",
                    "movie_id HAVING COUNTVOTES>0 ORDER BY COUNTCS DESC, COUNTVOTES DESC, average_meaning DESC, average_enjoyment DESC"
            );
        } catch (ExecutionException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        // Get the first 12 movies
        List<MovieRatings> top12Movies = resultMovieRatings.stream().limit(12).collect(Collectors.toList());
        // Get movieId
        ArrayList<String> top12MovieIds = new ArrayList<>();
        for(MovieRatings mr : top12Movies) {
            top12MovieIds.add(mr.getMovieId());
        }

        StringBuilder movieIDs = new StringBuilder();
        for(String movieId : top12MovieIds) {
            movieIDs.append(String.format("'%s', ", movieId));
        }
        movieIDs.delete(movieIDs.length()-2, movieIDs.length());
        // Query all movies with the corresponding movieID
        try {
            allMovieInfo = Utils.executeQuery(
                    MovieInfo.class,
                    RecommendationActivity.this,
                    "select",
                    "movie_id, primary_title, start_year, poster_url",
                    "movie_info",
                    "WHERE movie_id in",
                    String.format("(%s) ORDER BY FIELD(movie_id, %s)", movieIDs.toString(), movieIDs.toString())
            );
        } catch (ExecutionException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        LinkedList<MovieInfo> moviesSaved = new LinkedList<>(allMovieInfo);

        RecyclerView recyclerView = findViewById(R.id.rv_recommendation);
        RankAdapter adapter = new RankAdapter(this, moviesSaved, currentUserId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void launchRateActivity(View view) {
        Intent intent = new Intent(this, RateActivity.class);
        startActivity(intent);
    }
}
