package com.example.happy.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.happy.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void launchRecommendationCSActivity(View view) {
        Intent intent = new Intent(this, RecommendationCSActivity.class);
        startActivity(intent);
    }

    public void launchTop12Activity(View view) {
        Intent intent = new Intent(this, Top12Activity.class);
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
