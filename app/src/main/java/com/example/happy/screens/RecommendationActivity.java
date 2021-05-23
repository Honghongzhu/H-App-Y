package com.example.happy.screens;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.adapters.MovieAdapter;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.MovieRatings;
import com.example.happy.queries.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecommendationActivity extends AppCompatActivity {

    //private LinkedList<MovieInfo> moviesToAdapt;
    private List<MovieInfo> allMovieInfo;
    private List<MovieRatings> resultMovieRatings;
    private List<MovieRatings> top12Movies;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        ArrayList<String> chosenCS = (ArrayList<String>) getIntent().getSerializableExtra("chosenCS");
        String listCS = String.join(", ", chosenCS);

        Toast.makeText(RecommendationActivity.this, listCS, Toast.LENGTH_LONG).show();

        // Query all movieRatings with the requested CS of the user
        try {
            resultMovieRatings = Utils.executeQuery(
                    MovieRatings.class,
                    RecommendationActivity.this,
                    "select",
                    "movie_id, average_meaning, " + listCS,
                    "movie_ratings",
                    "",
                    ""
            );
        } catch (ExecutionException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        for(MovieRatings mr : resultMovieRatings) {
            int countCS = 0;
            int countVotes = 0;
            for(String cs : chosenCS) {
                if( getCsValue(cs, mr) > 0 );
                countCS++;
                countVotes += getCsValue(cs, mr);
            }
            mr.setCsCount(countCS);
            mr.setVotesCount(countVotes);
        }

        // Sort the movies based
        resultMovieRatings.sort(Comparator.comparing(MovieRatings::getCsCount)
                                    .thenComparing(MovieRatings::getVotesCount)
                                    .thenComparing(MovieRatings::getAverageMeaning)
                                    .thenComparing(MovieRatings::getAverageEnjoyment));

        // Get the first 12 movies
        //top12Movies = resultMovieRatings.stream().limit()

        // Query all movieInfo
//        try {
//            allMovieInfo = Utils.executeQuery(
//                    MovieInfo.class,
//                    RecommendationActivity.this,
//                    "select",
//                    "*",
//                    "movie_info",
//                    "",
//                    ""
//            );
//        } catch (ExecutionException e) {
//            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//        } catch (InterruptedException e) {
//            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//        }




//        recyclerView = findViewById(R.id.rv_recommendation);
//        adapter = new MovieAdapter(this, allMovieInfo);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void launchRateActivity(View view) {
        Intent intent = new Intent(this, RateActivity.class);
        startActivity(intent);
    }

    public int getCsValue(String cs, MovieRatings mr) {
        int csvalue = 0;
        switch(cs) {
            case "appreciation":
                csvalue = Integer.parseInt(mr.getAppreciationBeautyExcellence());
                break;
            case "bravery":
                csvalue = Integer.parseInt(mr.getBravery());
                break;
            case "creativity":
                csvalue = Integer.parseInt(mr.getCreativity());
                break;
            case "curiosity":
                csvalue = Integer.parseInt(mr.getCuriosity());
                break;
            case "fairness":
                csvalue = Integer.parseInt(mr.getFairness());
                break;
            case "forgiveness":
                csvalue = Integer.parseInt(mr.getForgiveness());
                break;
            case "gratitude":
                csvalue = Integer.parseInt(mr.getGratitude());
                break;
            case "honesty":
                csvalue = Integer.parseInt(mr.getHonesty());
                break;
            case "hope":
                csvalue = Integer.parseInt(mr.getHope());
                break;
            case "humility":
                csvalue = Integer.parseInt(mr.getHumility());
                break;
            case "humor":
                csvalue = Integer.parseInt(mr.getHumor());
                break;
            case "judgement":
                csvalue = Integer.parseInt(mr.getJudgement());
                break;
            case "kindness":
                csvalue = Integer.parseInt(mr.getKindness());
                break;
            case "leadership":
                csvalue = Integer.parseInt(mr.getLeadership());
                break;
            case "love":
                csvalue = Integer.parseInt(mr.getLove());
                break;
            case "learning":
                csvalue = Integer.parseInt(mr.getLoveOfLearning());
                break;
            case "perseverance":
                csvalue = Integer.parseInt(mr.getPerseverance());
                break;
            case "perspective":
                csvalue = Integer.parseInt(mr.getPerspective());
                break;
            case "prudence":
                csvalue = Integer.parseInt(mr.getPrudence());
                break;
            case "selfregulation":
                csvalue = Integer.parseInt(mr.getSelfRegulation());
                break;
            case "socialintelligence":
                csvalue = Integer.parseInt(mr.getSocialIntelligence());
                break;
            case "spirituality":
                csvalue = Integer.parseInt(mr.getSpirituality());
                break;
            case "teamwork":
                csvalue = Integer.parseInt(mr.getTeamwork());
                break;
            case "zest":
                csvalue = Integer.parseInt(mr.getZest());
                break;
        }
        return csvalue;
    }
}
