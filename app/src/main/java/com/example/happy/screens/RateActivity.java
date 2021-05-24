package com.example.happy.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import com.example.happy.R;

public class RateActivity extends AppCompatActivity {

    private float enjoyRating = -1;
    private float meaningRating = -1;
    private int currentUserId = -1;
    private String movieToRate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("CURRENT_USER_ID", -1);
            movieToRate = extras.getString("SELECTED_MOVIE_ID", "");
        }

        RatingBar enjoyBar = findViewById(R.id.enjoyableRating);
        enjoyBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> enjoyRating = rating);

        RatingBar meaningBar = findViewById(R.id.meaningfulRating);
        meaningBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> meaningRating = rating);
    }

    public void launchCharacterStrengthsSeenActivity(View view) {
        Intent intent = new Intent(this, RateCSActivity.class);
        intent.putExtra("ENJOY_RATING", enjoyRating);
        intent.putExtra("MEANING_RATING", meaningRating);
        intent.putExtra("CURRENT_USER_ID", currentUserId);
        intent.putExtra("SELECTED_MOVIE_ID", movieToRate);
        startActivity(intent);
    }
}
