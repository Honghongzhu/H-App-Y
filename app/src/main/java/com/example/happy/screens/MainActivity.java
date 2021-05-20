package com.example.happy.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.MovieRatings;
import com.example.happy.queries.Users;
import com.example.happy.queries.Utils;

import java.util.List;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            List<Users> result = Utils.executeQuery(
                    Users.class,
                    MainActivity.this,
                    "select",
                    "*",
                    "users",
                    "where",
                    "user_id=0"
            );

            if(result.toString() != "[]"){
                Toast.makeText(MainActivity.this, result.get(0).getAndroidId(), Toast.LENGTH_LONG).show();
            }
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
