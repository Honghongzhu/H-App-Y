package com.example.happy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

public class Top10Activity extends AppCompatActivity {
    private LinkedList<String> moviesToAdapt = new LinkedList<>();
    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);

        // This is dummy code to display movie names
        for (int i = 0; i < 10; i++) {
            moviesToAdapt.addLast("Movie " + (i+1));
        }

        // Get a handle to the RecyclerView
        recyclerView = findViewById(R.id.rv_top10);
        // Create an adapter and supply the data to be displayed
        adapter = new MovieAdapter(this, moviesToAdapt);
        // Connect the adapter with the RecyclerView
        recyclerView.setAdapter(adapter);
        // Give the RecyclerView a default layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void launchRateActivity(View view) {
        Intent intent = new Intent(this, RateActivity.class);
        startActivity(intent);
    }
}
