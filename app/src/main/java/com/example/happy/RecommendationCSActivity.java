package com.example.happy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RecommendationCSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_cs);
    }

    public void launchRecommendationActivity(View view) {
        Intent intent = new Intent(this, RecommendationActivity.class);
        startActivity(intent);
    }
}
