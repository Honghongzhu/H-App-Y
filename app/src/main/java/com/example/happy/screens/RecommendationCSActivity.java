package com.example.happy.screens;

import android.content.Intent;
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
    private Button appreciation,bravery,creativity,curiosity,fairness,forgiveness,gratitude,honesty,hope,humility,humor,judgement,kindness,leadership,love,learning,perseverance,perspective,prudence,selfregulation,socialintelligence,spirituality,teamwork,zest;
    private List<MovieInfo> allMovieInfo;
    private List<MovieRatings> allMovieRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_cs);

        // Create buttons
        appreciation = findViewById(R.id.buttonAppreciation);
        bravery = findViewById(R.id.buttonBravery);
        creativity = findViewById(R.id.buttonCreativity);
        curiosity = findViewById(R.id.buttonCuriosity);
        fairness = findViewById(R.id.buttonFairness);
        forgiveness = findViewById(R.id.buttonForgiveness);
        gratitude = findViewById(R.id.buttonGratitude);
        honesty = findViewById(R.id.buttonHonesty);
        hope = findViewById(R.id.buttonHope);
        humility = findViewById(R.id.buttonHumility);
        humor = findViewById(R.id.buttonHumor);
        judgement = findViewById(R.id.buttonJudgement);
        kindness = findViewById(R.id.buttonKindness);
        leadership = findViewById(R.id.buttonLeadership);
        love = findViewById(R.id.buttonLove);
        learning = findViewById(R.id.buttonLearning);
        perseverance = findViewById(R.id.buttonPerseverance);
        perspective = findViewById(R.id.buttonPerspective);
        prudence = findViewById(R.id.buttonPrudence);
        selfregulation = findViewById(R.id.buttonSelfRegulation);
        socialintelligence = findViewById(R.id.buttonSocialIntelligence);
        spirituality = findViewById(R.id.buttonSpirituality);
        teamwork = findViewById(R.id.buttonTeamwork);
        zest = findViewById(R.id.buttonZest);

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
        changeButtonBackground(appreciation, R.drawable.appreciation_light, R.drawable.appreciation_dark);
        changeButtonBackground(bravery, R.drawable.bravery_light, R.drawable.bravery_dark);
        changeButtonBackground(creativity, R.drawable.creativity_light, R.drawable.creativity_dark);
        changeButtonBackground(curiosity, R.drawable.curiosity_light, R.drawable.curiosity_dark);
        changeButtonBackground(fairness, R.drawable.fairness_light, R.drawable.fairness_dark);
        changeButtonBackground(forgiveness, R.drawable.forgiveness_light, R.drawable.forgiveness_dark);
        changeButtonBackground(gratitude, R.drawable.gratitude_light, R.drawable.gratitude_dark);
        changeButtonBackground(honesty, R.drawable.honesty_light, R.drawable.honesty_dark);
        changeButtonBackground(hope, R.drawable.hope_light, R.drawable.hope_dark);
        changeButtonBackground(humility, R.drawable.humility_light, R.drawable.humility_dark);
        changeButtonBackground(humor, R.drawable.humor_light, R.drawable.humor_dark);
        changeButtonBackground(judgement, R.drawable.judgement_light, R.drawable.judgement_dark);
        changeButtonBackground(kindness, R.drawable.kindness_light, R.drawable.kindness_dark);
        changeButtonBackground(leadership, R.drawable.leadership_light, R.drawable.leadership_dark);
        changeButtonBackground(love, R.drawable.love_light, R.drawable.love_dark);
        changeButtonBackground(learning, R.drawable.lol_light, R.drawable.lol_dark);
        changeButtonBackground(perseverance, R.drawable.perseverance_light, R.drawable.perseverance_dark);
        changeButtonBackground(perspective, R.drawable.perspective_light, R.drawable.perspective_dark);
        changeButtonBackground(prudence, R.drawable.prudence_light, R.drawable.prudence_dark);
        changeButtonBackground(selfregulation, R.drawable.self_light, R.drawable.self_dark);
        changeButtonBackground(socialintelligence, R.drawable.social_light, R.drawable.social_dark);
        changeButtonBackground(spirituality, R.drawable.spirituality_light, R.drawable.spirituality_dark);
        changeButtonBackground(teamwork, R.drawable.teamwork_light, R.drawable.teamwork_dark);
        changeButtonBackground(zest, R.drawable.zest_light, R.drawable.zest_dark);

    }

    // Helper function to change the background of the buttons
    public void changeButtonBackground(Button buttoncs, int light, int dark) {
        buttoncs.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    buttoncs.setBackgroundResource(light);
                    check=0;
                }else {
                    buttoncs.setBackgroundResource(dark);
                    check=1;
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
