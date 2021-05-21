package com.example.happy.screens;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.MovieRatings;
import com.example.happy.queries.NoResult;
import com.example.happy.queries.Users;
import com.example.happy.queries.Utils;

import java.util.List;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int USER_ID;

        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        try {
            // check if the android id is already in the table
            List<Users> result = Utils.executeQuery(
                    Users.class,
                    MainActivity.this,
                    "select",
                    "*",
                    "users",
                    "where",
                    String.format("android_id=\'%s\'", androidId)
            );

            // if it isn't, add it
            if (result.toString().equals("[]")) {
                List<NoResult> selectResult = Utils.executeQuery(
                        NoResult.class,
                        MainActivity.this,
                        "insert",
                        "(android_id)",
                        "users",
                        "values",
                        String.format("(\'%s\')", androidId)
                );

            // if it is, store it in a global variable
            } else {
                USER_ID = result.get(0).getUserId();
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
