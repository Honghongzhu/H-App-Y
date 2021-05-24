package com.example.happy.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.queries.MovieRatings;
import com.example.happy.queries.NoResult;
import com.example.happy.queries.Utils;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RateCSActivity extends AppCompatActivity {

    Button appreciation, bravery, creativity, curiosity, fairness, forgiveness, gratitude,
            honesty, hope, humility, humor, judgement, kindness, leadership, love, learning,
            perseverance, perspective, prudence, selfRegulation, socialIntelligence, spirituality,
            teamwork, zest;

    int appreciationClicked = 0;
    int braveryClicked = 0;
    int creativityClicked = 0;
    int curiosityClicked = 0;
    int fairnessClicked = 0;
    int forgivenessClicked = 0;
    int gratitudeClicked = 0;
    int honestyClicked = 0;
    int hopeClicked = 0;
    int humilityClicked = 0;
    int humorClicked = 0;
    int judgementClicked = 0;
    int kindnessClicked = 0;
    int leadershipClicked = 0;
    int loveClicked = 0;
    int learningClicked = 0;
    int perseveranceClicked = 0;
    int perspectiveClicked = 0;
    int prudenceClicked = 0;
    int selfRegulationClicked = 0;
    int socialIntelligenceClicked = 0;
    int spiritualityClicked = 0;
    int teamworkClicked = 0;
    int zestClicked = 0;

    int currentUserId = -1;
    float enjoyRating = -1;
    float meaningRating = -1;
    String movieToRate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rate_cs);
        appreciation=(Button)findViewById(R.id.buttonAppreciation);
        bravery=(Button)findViewById(R.id.buttonBravery);
        creativity=(Button)findViewById(R.id.buttonCreativity);
        curiosity=(Button)findViewById(R.id.buttonCuriosity);
        fairness=(Button)findViewById(R.id.buttonFairness);
        forgiveness=(Button)findViewById(R.id.buttonForgiveness);
        gratitude=(Button)findViewById(R.id.buttonGratitude);
        honesty=(Button)findViewById(R.id.buttonHonesty);
        hope=(Button)findViewById(R.id.buttonHope);
        humility=(Button)findViewById(R.id.buttonHumility);
        humor=(Button)findViewById(R.id.buttonHumor);
        judgement=(Button)findViewById(R.id.buttonJudgement);
        kindness=(Button)findViewById(R.id.buttonKindness);
        leadership=(Button)findViewById(R.id.buttonLeadership);
        love=(Button)findViewById(R.id.buttonLove);
        learning=(Button)findViewById(R.id.buttonLearning);
        perseverance=(Button)findViewById(R.id.buttonPerseverance);
        perspective=(Button)findViewById(R.id.buttonPerspective);
        prudence=(Button)findViewById(R.id.buttonPrudence);
        selfRegulation=(Button)findViewById(R.id.buttonSelfRegulation);
        socialIntelligence=(Button)findViewById(R.id.buttonSocialIntelligence);
        spirituality=(Button)findViewById(R.id.buttonSpirituality);
        teamwork=(Button)findViewById(R.id.buttonTeamwork);
        zest=(Button)findViewById(R.id.buttonZest);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("CURRENT_USER_ID", -1);
            enjoyRating = extras.getFloat("ENJOY_RATING", -1);
            meaningRating = extras.getFloat("MEANING_RATING", -1);
            movieToRate = extras.getString("SELECTED_MOVIE_ID", "");
        }

        appreciation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(appreciationClicked == 1){
                    appreciation.setBackgroundResource(R.drawable.appreciation_dark);
                    appreciationClicked = 0;
                }else {
                    appreciation.setBackgroundResource(R.drawable.appreciation_light);
                    appreciationClicked = 1;
                }
            }
        });
        bravery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(braveryClicked == 1){
                    bravery.setBackgroundResource(R.drawable.bravery_dark);
                    braveryClicked = 0;
                }else {
                    bravery.setBackgroundResource(R.drawable.bravery_light);
                    braveryClicked = 1;
                }
            }
        });
        creativity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(creativityClicked == 1){
                    creativity.setBackgroundResource(R.drawable.creativity_dark);
                    creativityClicked = 0;
                }else {
                    creativity.setBackgroundResource(R.drawable.creativity_light);
                    creativityClicked = 1;
                }
            }
        });
        curiosity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(curiosityClicked == 1){
                    curiosity.setBackgroundResource(R.drawable.curiosity_dark);
                    curiosityClicked = 0;
                }else {
                    curiosity.setBackgroundResource(R.drawable.curiosity_light);
                    curiosityClicked = 1;
                }
            }
        });
        fairness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fairnessClicked == 1){
                    fairness.setBackgroundResource(R.drawable.fairness_dark);
                    fairnessClicked = 0;
                }else {
                    fairness.setBackgroundResource(R.drawable.fairness_light);
                    fairnessClicked = 1;
                }
            }
        });
        forgiveness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(forgivenessClicked == 1){
                    forgiveness.setBackgroundResource(R.drawable.forgiveness_dark);
                    forgivenessClicked = 0;
                }else {
                    forgiveness.setBackgroundResource(R.drawable.forgiveness_light);
                    forgivenessClicked = 1;
                }
            }
        });
        gratitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gratitudeClicked == 1){
                    gratitude.setBackgroundResource(R.drawable.gratitude_dark);
                    gratitudeClicked = 0;
                }else {
                    gratitude.setBackgroundResource(R.drawable.gratitude_light);
                    gratitudeClicked = 1;
                }
            }
        });
        honesty.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View view) {
                if(honestyClicked == 1){
                    honesty.setBackgroundResource(R.drawable.honesty_dark);
                    honestyClicked = 0;
                }else {
                    honesty.setBackgroundResource(R.drawable.honesty_light);
                    honestyClicked = 1;
                }
            }
        });
        hope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hopeClicked == 1){
                    hope.setBackgroundResource(R.drawable.hope_dark);
                    hopeClicked = 0;
                }else {
                    hope.setBackgroundResource(R.drawable.hope_light);
                    hopeClicked = 1;
                }
            }
        });
        humility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(humilityClicked == 1){
                    humility.setBackgroundResource(R.drawable.humility_dark);
                    humilityClicked = 0;
                }else {
                    humility.setBackgroundResource(R.drawable.humility_light);
                    humilityClicked = 1;
                }
            }
        });
        humor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(humorClicked == 1){
                    humor.setBackgroundResource(R.drawable.humor_dark);
                    humorClicked = 0;
                }else {
                    humor.setBackgroundResource(R.drawable.humor_light);
                    humorClicked = 1;
                }
            }
        });
        judgement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(judgementClicked == 1){
                    judgement.setBackgroundResource(R.drawable.judgement_dark);
                    judgementClicked = 0;
                }else {
                    judgement.setBackgroundResource(R.drawable.judgement_light);
                    judgementClicked = 1;
                }
            }
        });
        kindness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kindnessClicked == 1){
                    kindness.setBackgroundResource(R.drawable.kindness_dark);
                    kindnessClicked = 0;
                }else {
                    kindness.setBackgroundResource(R.drawable.kindness_light);
                    kindnessClicked = 1;
                }
            }
        });
        leadership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(leadershipClicked == 1){
                    leadership.setBackgroundResource(R.drawable.leadership_dark);
                    leadershipClicked = 0;
                }else {
                    leadership.setBackgroundResource(R.drawable.leadership_light);
                    leadershipClicked = 1;
                }
            }
        });
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loveClicked == 1){
                    love.setBackgroundResource(R.drawable.love_dark);
                    loveClicked = 0;
                }else {
                    love.setBackgroundResource(R.drawable.love_light);
                    loveClicked = 1;
                }
            }
        });
        learning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(learningClicked == 1){
                    learning.setBackgroundResource(R.drawable.lol_dark);
                    learningClicked = 0;
                }else {
                    learning.setBackgroundResource(R.drawable.lol_light);
                    learningClicked = 1;
                }
            }
        });
        perseverance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(perseveranceClicked == 1){
                    perseverance.setBackgroundResource(R.drawable.perseverance_dark);
                    perseveranceClicked = 0;
                }else {
                    perseverance.setBackgroundResource(R.drawable.perseverance_light);
                    perseveranceClicked = 1;
                }
            }
        });
        perspective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(perspectiveClicked == 1){
                    perspective.setBackgroundResource(R.drawable.perspective_dark);
                    perspectiveClicked = 0;
                }else {
                    perspective.setBackgroundResource(R.drawable.perspective_light);
                    perspectiveClicked = 1;
                }
            }
        });
        prudence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prudenceClicked == 1){
                    prudence.setBackgroundResource(R.drawable.prudence_dark);
                    prudenceClicked = 0;
                }else {
                    prudence.setBackgroundResource(R.drawable.prudence_light);
                    prudenceClicked = 1;
                }
            }
        });
        selfRegulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selfRegulationClicked == 1){
                    selfRegulation.setBackgroundResource(R.drawable.self_dark);
                    selfRegulationClicked = 0;
                }else {
                    selfRegulation.setBackgroundResource(R.drawable.self_light);
                    selfRegulationClicked = 1;
                }
            }
        });
        socialIntelligence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(socialIntelligenceClicked == 1){
                    socialIntelligence.setBackgroundResource(R.drawable.social_dark);
                    socialIntelligenceClicked = 0;
                }else {
                    socialIntelligence.setBackgroundResource(R.drawable.social_light);
                    socialIntelligenceClicked = 1;
                }
            }
        });
        spirituality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spiritualityClicked == 1){
                    spirituality.setBackgroundResource(R.drawable.spirituality_dark);
                    spiritualityClicked = 0;
                }else {
                    spirituality.setBackgroundResource(R.drawable.spirituality_light);
                    spiritualityClicked = 1;
                }
            }
        });
        teamwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamworkClicked == 1){
                    teamwork.setBackgroundResource(R.drawable.teamwork_dark);
                    teamworkClicked = 0;
                }else {
                    teamwork.setBackgroundResource(R.drawable.teamwork_light);
                    teamworkClicked = 1;
                }
            }
        });
        zest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zestClicked == 1){
                    zest.setBackgroundResource(R.drawable.zest_dark);
                    zestClicked = 0;
                }else {
                    zest.setBackgroundResource(R.drawable.zest_light);
                    zestClicked = 1;
                }
            }
        });
    }

    public void launchHistoryActivity(View view) {

        String columns = "(user_id, movie_id, enjoyment_rating, meaning_rating, creativity," +
                "curiosity, judgement, love_of_learning, perspective, bravery, honesty, zest," +
                "perseverance, love, kindness, social_intelligence, teamwork, fairness, leadership," +
                "forgiveness, humility, prudence, self_regulation, appreciation_beauty_excellence," +
                "gratitude, hope, humor, spirituality)";

        String unformatted = "(" + new String(new char[27]).replace("\0", "\'%s\', ") +
                "\'%s\')";

        try {

            // first get the data to update in movie_ratings
            List<MovieRatings> currentMovieRatingsTable = Utils.executeQuery(
                    MovieRatings.class,
                    RateCSActivity.this,
                    "select",
                    "*",
                    "movie_ratings",
                    "where",
                    String.format("movie_id=%s)", movieToRate)
            );

            // update the average and votes for enjoyment and meaningfulness
            MovieRatings currentMovieRating = currentMovieRatingsTable.get(0);
            int oldVotesEnjoyment = Integer.parseInt(currentMovieRating.getVotesEnjoyment());
            int oldVotesMeaning = Integer.parseInt(currentMovieRating.getVotesMeaning());
            float oldAverageEnjoyment = Float.parseFloat(currentMovieRating.getAverageEnjoyment());
            float oldAverageMeaning = Float.parseFloat(currentMovieRating.getAverageMeaning());

            int newVotesEnjoyment = oldVotesEnjoyment + 1;
            float newAverageEnjoyment = (oldAverageEnjoyment * oldVotesEnjoyment + enjoyRating)/newVotesEnjoyment;

            int newVotesMeaning = oldVotesMeaning + 1;
            float newAverageMeaning = (oldAverageMeaning * oldVotesMeaning + meaningRating)/newVotesMeaning;

            //update statement
            List<NoResult> updateResult = Utils.executeQuery(
                    NoResult.class,
                    RateCSActivity.this,
                    "update",
                    "(votes_enjoyment, average_enjoyment, votes_meaning, average_meaning)",
                    "movie_ratings",
                    "values",
                    String.format(("(%s, %s, %s, %s)"),
                            newVotesEnjoyment,
                            newAverageEnjoyment,
                            newVotesMeaning,
                            newAverageMeaning)
            );

            Toast.makeText(RateCSActivity.this, updateResult.get(0).getResult(), Toast.LENGTH_LONG).show();

            List<NoResult> insertResult = Utils.executeQuery(
                    NoResult.class,
                    RateCSActivity.this,
                    "insert",
                    columns,
                    "user_ratings",
                    "values",
                    String.format(unformatted,
                            currentUserId,
                            movieToRate,
                            enjoyRating,
                            meaningRating,
                            creativityClicked,
                            curiosityClicked,
                            judgementClicked,
                            learningClicked,
                            perspectiveClicked,
                            braveryClicked,
                            honestyClicked,
                            zestClicked,
                            perseveranceClicked,
                            loveClicked,
                            kindnessClicked,
                            socialIntelligenceClicked,
                            teamworkClicked,
                            fairnessClicked,
                            leadershipClicked,
                            forgivenessClicked,
                            humilityClicked,
                            prudenceClicked,
                            selfRegulationClicked,
                            appreciationClicked,
                            gratitudeClicked,
                            hopeClicked,
                            humorClicked,
                            spiritualityClicked)
            );
        } catch (ExecutionException e) {
            Toast.makeText(RateCSActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(RateCSActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }


        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void launchInfoCSActivity(View view) {
        Intent intent = new Intent(this, InfoCSActivity.class);
        startActivity(intent);
    }
}
