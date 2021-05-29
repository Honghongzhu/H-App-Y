package com.example.happy.screens;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.MovieRatings;

import java.util.ArrayList;
import java.util.List;

public class RecommendationCSActivity extends AppCompatActivity {

    private List<MovieInfo> allMovieInfo;
    private List<MovieRatings> allMovieRatings;
    private ArrayList<String> chosenCS = new ArrayList<>();
    private final int maxNrCS = 4;
    private int countCS = 0;
    int currentUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_cs);
        countCS = 0; // Reset count

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("CURRENT_USER_ID", -1);
        }

        // Create onClickListeners for the buttons to change background
        setUpButton(R.id.buttonAppreciation, "appreciation_beauty_excellence", R.drawable.appreciation_light, R.drawable.appreciation_dark);
        setUpButton(R.id.buttonBravery, "bravery", R.drawable.bravery_light, R.drawable.bravery_dark);
        setUpButton(R.id.buttonCreativity, "creativity", R.drawable.creativity_light, R.drawable.creativity_dark);
        setUpButton(R.id.buttonCuriosity, "curiosity", R.drawable.curiosity_light, R.drawable.curiosity_dark);
        setUpButton(R.id.buttonFairness, "fairness", R.drawable.fairness_light, R.drawable.fairness_dark);
        setUpButton(R.id.buttonForgiveness, "forgiveness", R.drawable.forgiveness_light, R.drawable.forgiveness_dark);
        setUpButton(R.id.buttonGratitude, "gratitude", R.drawable.gratitude_light, R.drawable.gratitude_dark);
        setUpButton(R.id.buttonHonesty, "honesty", R.drawable.honesty_light, R.drawable.honesty_dark);
        setUpButton(R.id.buttonHope, "hope", R.drawable.hope_light, R.drawable.hope_dark);
        setUpButton(R.id.buttonHumility, "humility", R.drawable.humility_light, R.drawable.humility_dark);
        setUpButton(R.id.buttonHumor, "humor", R.drawable.humor_light, R.drawable.humor_dark);
        setUpButton(R.id.buttonJudgement, "judgement", R.drawable.judgement_light, R.drawable.judgement_dark);
        setUpButton(R.id.buttonKindness, "kindness", R.drawable.kindness_light, R.drawable.kindness_dark);
        setUpButton(R.id.buttonLeadership, "leadership", R.drawable.leadership_light, R.drawable.leadership_dark);
        setUpButton(R.id.buttonLove, "love", R.drawable.love_light, R.drawable.love_dark);
        setUpButton(R.id.buttonLearning, "love_of_learning", R.drawable.lol_light, R.drawable.lol_dark);
        setUpButton(R.id.buttonPerseverance, "perseverance", R.drawable.perseverance_light, R.drawable.perseverance_dark);
        setUpButton(R.id.buttonPerspective, "perspective", R.drawable.perspective_light, R.drawable.perspective_dark);
        setUpButton(R.id.buttonPrudence, "prudence", R.drawable.prudence_light, R.drawable.prudence_dark);
        setUpButton(R.id.buttonSelfRegulation, "self_regulation", R.drawable.self_light, R.drawable.self_dark);
        setUpButton(R.id.buttonSocialIntelligence, "social_intelligence", R.drawable.social_light, R.drawable.social_dark);
        setUpButton(R.id.buttonSpirituality, "spirituality", R.drawable.spirituality_light, R.drawable.spirituality_dark);
        setUpButton(R.id.buttonTeamwork, "teamwork", R.drawable.teamwork_light, R.drawable.teamwork_dark);
        setUpButton(R.id.buttonZest, "zest", R.drawable.zest_light, R.drawable.zest_dark);
        
    }

    // Helper function to change the background of the buttons
    public void setUpButton(int id, String csName, int light, int dark) {
        Button buttonCS = findViewById(id);

        buttonCS.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(check==1){ // Add CS
                    if(countCS < maxNrCS) {
                        chosenCS.add(csName);
                        countCS++;
                        buttonCS.setBackgroundResource(light);
                        check = 0;
                    }
                    else {
                        Toast.makeText(RecommendationCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                    }
                }else { // Remove CS
                    chosenCS.remove(csName);
                    countCS--;
                    buttonCS.setBackgroundResource(dark);
                    check=1;
                }
            }
        });
    }

    public void launchRecommendationActivity(View view) {
        if(countCS==0) {
            Toast.makeText(this, "Please choose at least 1 character strength", Toast.LENGTH_LONG).show();
        }else {
            Intent intent = new Intent(this, RecommendationActivity.class);
            intent.putExtra("chosenCS", chosenCS);
            intent.putExtra("CURRENT_USER_ID", currentUserId);
            startActivity(intent);
        }
    }

    public void launchInfoCSActivity(View view) {
        Intent intent = new Intent(this, InfoCSActivity.class);
        startActivity(intent);
    }
}
