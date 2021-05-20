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
import com.example.happy.queries.Utils;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecommendationCSActivity extends AppCompatActivity {

    private List<MovieInfo> allMovieInfo;
    private List<MovieRatings> allMovieRatings;
    private List<String> chosenCS;
    private final int maxNrCS = 4;
    private int countCS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_cs);

        // Query all movieInfo
        try {
            allMovieInfo = Utils.executeQuery(
                    MovieInfo.class,
                    RecommendationCSActivity.this,
                    "select",
                    "*",
                    "movie_info",
                    "",
                    ""
            );
        } catch (ExecutionException e) {
            Toast.makeText(RecommendationCSActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(RecommendationCSActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        // Query all movieRatings
        try {
            allMovieRatings = Utils.executeQuery(
                    MovieRatings.class,
                    RecommendationCSActivity.this,
                    "select",
                    "*",
                    "movie_ratings",
                    "",
                    ""
            );
        } catch (ExecutionException e) {
            Toast.makeText(RecommendationCSActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(RecommendationCSActivity.this, e.toString(), Toast.LENGTH_LONG).show();
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
    public void setUpButton(int id, String tag, int light, int dark) {
        Button buttoncs = findViewById(id);
        buttoncs.setTag(tag);

        buttoncs.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(maxNrCS<5){
                    if(check==1){ // Add CS
                        Toast.makeText(RecommendationCSActivity.this, (String)view.getTag(), Toast.LENGTH_LONG).show();
                        buttoncs.setBackgroundResource(light);
                        countCS++;
                        check=0;
                    }else { // Remove CS
                        buttoncs.setBackgroundResource(dark);
                        countCS--;
                        check=1;
                    }
                }

            }
        });
    }

    public void launchRecommendationActivity(View view) {
        Intent intent = new Intent(this, RecommendationActivity.class);
        startActivity(intent);
    }

    public void launchInfoCSActivity(View view) {
        Intent intent = new Intent(this, InfoCSActivity.class);
        startActivity(intent);
    }
}
