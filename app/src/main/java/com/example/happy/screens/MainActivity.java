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

    private String currentUserId;

    public String getCurrentUserId() {
        return this.currentUserId;
    }

    public void setCurrentUserId (String currentUserId) {
        this.currentUserId = currentUserId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        try {
            // check if the android id is already in the table
            List<Users> usersTable = Utils.executeQuery(
                    Users.class,
                    MainActivity.this,
                    "select",
                    "*",
                    "users",
                    "where",
                    String.format("android_id=\'%s\'", androidId)
            );

            // if it isn't, add it
            if (usersTable.toString().equals("[]")) {
                List<NoResult> insertResult = Utils.executeQuery(
                        NoResult.class,
                        MainActivity.this,
                        "insert",
                        "(android_id)",
                        "users",
                        "values",
                        String.format("(\'%s\')", androidId)
                );
            }

            // Regardless of the case, get the ID
            usersTable = Utils.executeQuery(
                    Users.class,
                    MainActivity.this,
                    "select",
                    "*",
                    "users",
                    "where",
                    String.format("android_id=\'%s\'", androidId)
            );

            setCurrentUserId(usersTable.get(0).getUserId());

        } catch (ExecutionException e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void launchRecommendationCSActivity(View view) {
        Intent intent = new Intent(this, RecommendationCSActivity.class);
        intent.putExtra("CURRENT_USER_ID", getCurrentUserId());
        startActivity(intent);
    }

    public void launchTop12Activity(View view) {
        Intent intent = new Intent(this, Top12Activity.class);
        intent.putExtra("CURRENT_USER_ID", getCurrentUserId());
        startActivity(intent);
    }

    public void launchHistoryActivity(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("CURRENT_USER_ID", getCurrentUserId());
        startActivity(intent);
    }

    public void launchSavedActivity(View view) {
        Intent intent = new Intent(this, SavedActivity.class);
        intent.putExtra("CURRENT_USER_ID", getCurrentUserId());
        startActivity(intent);
    }

    public void launchSearchActivity(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("CURRENT_USER_ID", getCurrentUserId());
        startActivity(intent);
    }

}
