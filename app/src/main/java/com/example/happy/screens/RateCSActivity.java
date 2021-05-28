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
import com.example.happy.queries.MovieRatings;
import com.example.happy.queries.NoResult;
import com.example.happy.queries.SavedMovies;
import com.example.happy.queries.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RateCSActivity extends AppCompatActivity {

    String characterStrengths = "creativity," +
            "curiosity, judgement, love_of_learning, perspective, bravery, honesty, zest," +
            "perseverance, love, kindness, social_intelligence, teamwork, fairness, leadership," +
            "forgiveness, humility, prudence, self_regulation, appreciation_beauty_excellence," +
            "gratitude, hope, humor, spirituality";

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

    ArrayList<String> pickedCS = new ArrayList<>();
    private final int maxNrCS = 4;
    private int countCS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rate_cs);
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
        selfRegulation = findViewById(R.id.buttonSelfRegulation);
        socialIntelligence = findViewById(R.id.buttonSocialIntelligence);
        spirituality = findViewById(R.id.buttonSpirituality);
        teamwork = findViewById(R.id.buttonTeamwork);
        zest = findViewById(R.id.buttonZest);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("CURRENT_USER_ID", -1);
            enjoyRating = extras.getFloat("ENJOY_RATING", -1);
            meaningRating = extras.getFloat("MEANING_RATING", -1);
            movieToRate = extras.getString("SELECTED_MOVIE_ID", "");
        }

        appreciation.setOnClickListener(view -> {
            if(appreciationClicked == 1){
                countCS--;
                appreciation.setBackgroundResource(R.drawable.appreciation_dark);
                appreciationClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    appreciation.setBackgroundResource(R.drawable.appreciation_light);
                    appreciationClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        bravery.setOnClickListener(view -> {
            if(braveryClicked == 1){
                countCS--;
                bravery.setBackgroundResource(R.drawable.bravery_dark);
                braveryClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    bravery.setBackgroundResource(R.drawable.bravery_light);
                    braveryClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        creativity.setOnClickListener(view -> {
            if(creativityClicked == 1){
                countCS--;
                creativity.setBackgroundResource(R.drawable.creativity_dark);
                creativityClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    creativity.setBackgroundResource(R.drawable.creativity_light);
                    creativityClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        curiosity.setOnClickListener(view -> {
            if(curiosityClicked == 1){
                countCS--;
                curiosity.setBackgroundResource(R.drawable.curiosity_dark);
                curiosityClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    curiosity.setBackgroundResource(R.drawable.curiosity_light);
                    curiosityClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        fairness.setOnClickListener(view -> {
            if(fairnessClicked == 1){
                countCS--;
                fairness.setBackgroundResource(R.drawable.fairness_dark);
                fairnessClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    fairness.setBackgroundResource(R.drawable.fairness_light);
                    fairnessClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        forgiveness.setOnClickListener(view -> {
            if(forgivenessClicked == 1){
                countCS--;
                forgiveness.setBackgroundResource(R.drawable.forgiveness_dark);
                forgivenessClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    forgiveness.setBackgroundResource(R.drawable.forgiveness_light);
                    forgivenessClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        gratitude.setOnClickListener(view -> {
            if(gratitudeClicked == 1){
                countCS--;
                gratitude.setBackgroundResource(R.drawable.gratitude_dark);
                gratitudeClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    gratitude.setBackgroundResource(R.drawable.gratitude_light);
                    gratitudeClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        honesty.setOnClickListener(view -> {
            if(honestyClicked == 1){
                countCS--;
                honesty.setBackgroundResource(R.drawable.honesty_dark);
                honestyClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    honesty.setBackgroundResource(R.drawable.honesty_light);
                    honestyClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        hope.setOnClickListener(view -> {
            if(hopeClicked == 1){
                countCS--;
                hope.setBackgroundResource(R.drawable.hope_dark);
                hopeClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    hope.setBackgroundResource(R.drawable.hope_light);
                    hopeClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        humility.setOnClickListener(view -> {
            if(humilityClicked == 1){
                countCS--;
                humility.setBackgroundResource(R.drawable.humility_dark);
                humilityClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    humility.setBackgroundResource(R.drawable.humility_light);
                    humilityClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        humor.setOnClickListener(view -> {
            if(humorClicked == 1){
                countCS--;
                humor.setBackgroundResource(R.drawable.humor_dark);
                humorClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    humor.setBackgroundResource(R.drawable.humor_light);
                    humorClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        judgement.setOnClickListener(view -> {
            if(judgementClicked == 1){
                countCS--;
                judgement.setBackgroundResource(R.drawable.judgement_dark);
                judgementClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    judgement.setBackgroundResource(R.drawable.judgement_light);
                    judgementClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        kindness.setOnClickListener(view -> {
            if(kindnessClicked == 1){
                countCS--;
                kindness.setBackgroundResource(R.drawable.kindness_dark);
                kindnessClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    kindness.setBackgroundResource(R.drawable.kindness_light);
                    kindnessClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        leadership.setOnClickListener(view -> {
            if(leadershipClicked == 1){
                countCS--;
                leadership.setBackgroundResource(R.drawable.leadership_dark);
                leadershipClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    leadership.setBackgroundResource(R.drawable.leadership_light);
                    leadershipClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        love.setOnClickListener(view -> {
            if(loveClicked == 1){
                countCS--;
                love.setBackgroundResource(R.drawable.love_dark);
                loveClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    love.setBackgroundResource(R.drawable.love_light);
                    loveClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        learning.setOnClickListener(view -> {
            if(learningClicked == 1){
                countCS--;
                learning.setBackgroundResource(R.drawable.lol_dark);
                learningClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    learning.setBackgroundResource(R.drawable.lol_light);
                    learningClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        perseverance.setOnClickListener(view -> {
            if(perseveranceClicked == 1){
                countCS--;
                perseverance.setBackgroundResource(R.drawable.perseverance_dark);
                perseveranceClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    perseverance.setBackgroundResource(R.drawable.perseverance_light);
                    perseveranceClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        perspective.setOnClickListener(view -> {
            if(perspectiveClicked == 1){
                countCS--;
                perspective.setBackgroundResource(R.drawable.perspective_dark);
                perspectiveClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    perspective.setBackgroundResource(R.drawable.perspective_light);
                    perspectiveClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        prudence.setOnClickListener(view -> {
            if(prudenceClicked == 1){
                countCS--;
                prudence.setBackgroundResource(R.drawable.prudence_dark);
                prudenceClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    prudence.setBackgroundResource(R.drawable.prudence_light);
                    prudenceClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        selfRegulation.setOnClickListener(view -> {
            if(selfRegulationClicked == 1){
                countCS--;
                selfRegulation.setBackgroundResource(R.drawable.self_dark);
                selfRegulationClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    selfRegulation.setBackgroundResource(R.drawable.self_light);
                    selfRegulationClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        socialIntelligence.setOnClickListener(view -> {
            if(socialIntelligenceClicked == 1){
                countCS--;
                socialIntelligence.setBackgroundResource(R.drawable.social_dark);
                socialIntelligenceClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    socialIntelligence.setBackgroundResource(R.drawable.social_light);
                    socialIntelligenceClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        spirituality.setOnClickListener(view -> {
            if(spiritualityClicked == 1){
                countCS--;
                spirituality.setBackgroundResource(R.drawable.spirituality_dark);
                spiritualityClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    spirituality.setBackgroundResource(R.drawable.spirituality_light);
                    spiritualityClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        teamwork.setOnClickListener(view -> {
            if(teamworkClicked == 1){
                countCS--;
                teamwork.setBackgroundResource(R.drawable.teamwork_dark);
                teamworkClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    teamwork.setBackgroundResource(R.drawable.teamwork_light);
                    teamworkClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
        zest.setOnClickListener(view -> {
            if(zestClicked == 1){
                countCS--;
                zest.setBackgroundResource(R.drawable.zest_dark);
                zestClicked = 0;
            }else {
                if(countCS < maxNrCS) {
                    countCS++;
                    zest.setBackgroundResource(R.drawable.zest_light);
                    zestClicked = 1;
                }
                else {
                    Toast.makeText(RateCSActivity.this, "You've reached the maximum of 4 character strengths", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public ArrayList getColumns(int... args) {
        ArrayList<String> pickedCS = new ArrayList<>();
        for (int i=0; i < args.length; i++) {
            if (args[i] == 1){
                pickedCS.add(characterStrengths.split(",")[i]);
            }
        }
        return pickedCS;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getValues(MovieRatings movie, ArrayList<String> pickedCS){

        ArrayList<String> finalValues = new ArrayList<>();
        String[] chStrs = characterStrengths.split(",");

        for(String cs : pickedCS) {
            if (cs.equals(chStrs[0])){
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getCreativity()) + 1));
            }else if (cs.equals(chStrs[1])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getCuriosity()) + 1));
            }else if (cs.equals(chStrs[2])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getJudgement()) + 1));
            }else if (cs.equals(chStrs[3])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getLoveOfLearning()) + 1));
            }else if (cs.equals(chStrs[4])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getPerspective()) + 1));
            }else if (cs.equals(chStrs[5])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getBravery()) + 1));
            }else if (cs.equals(chStrs[6])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getHonesty()) + 1));
            }else if (cs.equals(chStrs[7])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getZest()) + 1));
            }else if (cs.equals(chStrs[8])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getPerseverance()) + 1));
            }else if (cs.equals(chStrs[9])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getLove()) + 1));
            }else if (cs.equals(chStrs[10])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getKindness()) + 1));
            }else if (cs.equals(chStrs[11])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getSocialIntelligence()) + 1));
            }else if (cs.equals(chStrs[12])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getTeamwork()) + 1));
            }else if (cs.equals(chStrs[13])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getFairness()) + 1));
            }else if (cs.equals(chStrs[14])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getLeadership()) + 1));
            }else if (cs.equals(chStrs[15])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getForgiveness()) + 1));
            }else if (cs.equals(chStrs[16])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getHumility()) + 1));
            }else if (cs.equals(chStrs[17])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getPrudence()) + 1));
            }else if (cs.equals(chStrs[18])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getSelfRegulation()) + 1));
            }else if (cs.equals(chStrs[19])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getAppreciationBeautyExcellence()) + 1));
            }else if (cs.equals(chStrs[20])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getGratitude()) + 1));
            }else if (cs.equals(chStrs[21])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getHope()) + 1));
            }else if (cs.equals(chStrs[22])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getHumor()) + 1));
            }else if (cs.equals(chStrs[23])) {
                finalValues.add(cs + "=" + Integer.toString(Integer.parseInt(movie.getSpirituality()) + 1));
            }
        } // end for

        return String.join(", ", finalValues);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void launchHistoryActivity(View view) {

        String columns = "(user_id, movie_id, enjoyment_rating, meaning_rating, creativity," +
                "curiosity, judgement, love_of_learning, perspective, bravery, honesty, zest," +
                "perseverance, love, kindness, social_intelligence, teamwork, fairness, leadership," +
                "forgiveness, humility, prudence, self_regulation, appreciation_beauty_excellence," +
                "gratitude, hope, humor, spirituality)";

        String unformatted = "(" + new String(new char[27]).replace("\0", "'%s', ") +
                "'%s')";

        try {

            // first get the data to update in movie_ratings
            List<MovieRatings> currentMovieRatingsTable = Utils.executeQuery(
                    MovieRatings.class,
                    RateCSActivity.this,
                    "select",
                    "*",
                    "movie_ratings",
                    "where",
                    String.format("movie_id='%s'", movieToRate)
            );

            //update the average and votes for enjoyment and meaningfulness
            MovieRatings currentMovieRating = currentMovieRatingsTable.get(0);
            int oldVotesEnjoyment = Integer.parseInt(currentMovieRating.getVotesEnjoyment());
            int oldVotesMeaning = Integer.parseInt(currentMovieRating.getVotesMeaning());
            float oldAverageEnjoyment = Float.parseFloat(currentMovieRating.getAverageEnjoyment());
            float oldAverageMeaning = Float.parseFloat(currentMovieRating.getAverageMeaning());

            int newVotesEnjoyment = oldVotesEnjoyment + 1;
            float newAverageEnjoyment = (oldAverageEnjoyment * oldVotesEnjoyment + enjoyRating)/newVotesEnjoyment;

            int newVotesMeaning = oldVotesMeaning + 1;
            float newAverageMeaning = (oldAverageMeaning * oldVotesMeaning + meaningRating)/newVotesMeaning;

            // need to save same order as variable characterStrengths
            pickedCS = getColumns(creativityClicked, curiosityClicked, judgementClicked, learningClicked, perspectiveClicked, braveryClicked, honestyClicked, zestClicked, perseveranceClicked, loveClicked, kindnessClicked, socialIntelligenceClicked, teamworkClicked, fairnessClicked, leadershipClicked, forgivenessClicked, humilityClicked, prudenceClicked, selfRegulationClicked, appreciationClicked, gratitudeClicked, hopeClicked, humorClicked, spiritualityClicked);

            String valuesToAdd = getValues(currentMovieRating, pickedCS);

            //update statement
            List<NoResult> updateResult = Utils.executeQuery(
                    NoResult.class,
                    RateCSActivity.this,
                    "update",
                    String.format("votes_enjoyment=%s, average_enjoyment=%s, votes_meaning=%s, average_meaning=%s, %s",
                            newVotesEnjoyment,
                            newAverageEnjoyment,
                            newVotesMeaning,
                            newAverageMeaning,
                            valuesToAdd),
                    "movie_ratings",
                    "where",
                    String.format("movie_id='%s'", movieToRate)
            );

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

            //remove from saved when rated
            List<SavedMovies> savedByUserTable = Utils.executeQuery(
                    SavedMovies.class,
                    RateCSActivity.this,
                    "select",
                    "*",
                    "saved_movies",
                    "where",
                    String.format("user_id=%s", currentUserId)
            );
            //if not empty
            if (!savedByUserTable.toString().equals("[]")) {
                ArrayList<String> savedMovieId = new ArrayList<>();
                for (SavedMovies savedMovie: savedByUserTable){
                    savedMovieId.add(savedMovie.getMovieId());
                }
                // if the movie to rate is in the saved table
                if(savedMovieId.contains(movieToRate)){
                    List<NoResult> deleteResult = Utils.executeQuery(
                            NoResult.class,
                            RateCSActivity.this,
                            "delete",
                            "",
                            "saved_movies",
                            "where",
                            String.format("movie_id='%s' and user_id=%s", movieToRate, currentUserId)
                    );
                }
            }


        } catch (ExecutionException e) {
            Toast.makeText(RateCSActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(RateCSActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("CURRENT_USER_ID", currentUserId);
        startActivity(intent);
    }

    public void launchInfoCSActivity(View view) {
        Intent intent = new Intent(this, InfoCSActivity.class);
        startActivity(intent);
    }
}
