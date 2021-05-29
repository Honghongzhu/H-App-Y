package com.example.happy.screens;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.queries.NoResult;
import com.example.happy.queries.Users;
import com.example.happy.queries.Utils;

import java.util.List;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private int currentUserId;
    private String dialogValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // if no internet connection then show message and close the app
        if(!Utils.connectedInternet(MainActivity.this)){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Check internet connection")
                    .setMessage("No active internet connection was detected." +
                            " Please enable an internet connection to use Meaningful Movies.")
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> finish())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
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


                // if it isn't, ask for alias and insert in DB
                if (usersTable.toString().equals("[]")) {

                    final Handler handler = new Handler() {
                        @Override
                        public void handleMessage(Message mesg)
                        {
                            throw new RuntimeException();
                        }
                    };

                    EditText input = new EditText(this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setHint("movieenthusiast123");

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setView(input);
                    builder.setTitle("Enter alias");
                    builder.setMessage("Please enter an alias so that we know who you are :) " +
                            "Write it down somewhere and don't forget it, because we will ask" +
                            " for it again in the final questionnaire!\n\n" +
                            "Please don't include symbols, spaces or special characters and " +
                            "make sure the text field is not empty!");
                    builder.setPositiveButton("Confirm", (dialog, id) -> {
                        dialogValue = input.getText().toString();
                        handler.sendMessage(handler.obtainMessage());
                    });
                    builder.show();
                    try{
                        Looper.loop();
                    }
                    catch(RuntimeException e){ //don't print this as it'll always show
                    }

                    dialogValue = dialogValue.replace(" ", "_");
                    dialogValue = dialogValue.replace("%", "");
                    dialogValue = dialogValue.replace(";", "");
                    dialogValue = dialogValue.replace("#", "");
                    dialogValue = dialogValue.replace("\"", "");
                    dialogValue = dialogValue.replace("'", "");

                    List<NoResult> insertResult = Utils.executeQuery(
                            NoResult.class,
                            MainActivity.this,
                            "insert",
                            "(android_id, alias)",
                            "users",
                            "values",
                            String.format("(\'%s\', \'%s\')", androidId, dialogValue)
                    );

                    usersTable = Utils.executeQuery(
                            Users.class,
                            MainActivity.this,
                            "select",
                            "*",
                            "users",
                            "where",
                            String.format("android_id=\'%s\'", androidId)
                    );

                } else {
                    usersTable = Utils.executeQuery(
                            Users.class,
                            MainActivity.this,
                            "select",
                            "*",
                            "users",
                            "where",
                            String.format("android_id=\'%s\'", androidId)
                    );
                }

                currentUserId = Integer.parseInt(usersTable.get(0).getUserId());

            } catch (ExecutionException e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            } catch (InterruptedException e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void launchRecommendationCSActivity(View view) {
        Intent intent = new Intent(this, RecommendationCSActivity.class);
        intent.putExtra("CURRENT_USER_ID", currentUserId);
        startActivity(intent);
    }

    public void launchTop12Activity(View view) {
        Intent intent = new Intent(this, Top12Activity.class);
        intent.putExtra("CURRENT_USER_ID", currentUserId);
        startActivity(intent);
    }

    public void launchHistoryActivity(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("CURRENT_USER_ID", currentUserId);
        startActivity(intent);
    }

    public void launchSavedActivity(View view) {
        Intent intent = new Intent(this, SavedActivity.class);
        intent.putExtra("CURRENT_USER_ID", currentUserId);
        startActivity(intent);
    }

    public void launchSearchActivity(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("CURRENT_USER_ID", currentUserId);
        startActivity(intent);
    }

}
