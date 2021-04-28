package com.example.happy.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.happy.R;

public class RateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
    }

    public void launchCharacterStrengthsSeenActivity(View view) {
        Intent intent = new Intent(this, RateCSActivity.class);
        startActivity(intent);
    }
}
