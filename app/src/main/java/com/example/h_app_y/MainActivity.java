package com.example.h_app_y;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView top10Button;
    private TextView historyButton;
    private TextView savedButton;
    private TextView searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        top10Button = findViewById(R.id.button_top10);
        historyButton = findViewById(R.id.button_history);
        savedButton = findViewById(R.id.button_saved);
        searchButton = findViewById(R.id.button_search);

    }

    public void launchRecommendationActivity(View view) {
        Intent intent = new Intent(this, RecommendationActivity.class);
        startActivity(intent);
    }

    public void launchTop10Activity(View view) {
        Intent intent = new Intent(this, Top10Activity.class);
        startActivity(intent);
    }

    public void launchHistoryActivity(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void launchSavedActivity(View view) {
        Intent intent = new Intent(this, SavedActivity.class);
        startActivity(intent);
    }

    public void launchSearchActivity(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
