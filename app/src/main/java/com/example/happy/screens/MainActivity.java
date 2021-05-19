package com.example.happy.screens;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.QueryObject;
import com.example.happy.queries.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            List<MovieInfo> result = Utils.executeQuery(
                    MovieInfo.class,
                    MainActivity.this,
                    "select",
                    "*",
                    "movie_info",
                    "where",
                    "start_year=2010"
            );

            Toast.makeText(MainActivity.this, result.get(0).getOriginalTitle(), Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

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
