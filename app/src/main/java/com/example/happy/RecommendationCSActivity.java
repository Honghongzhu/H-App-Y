package com.example.happy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecommendationCSActivity extends AppCompatActivity {
    Button appreciation,bravery,creativity,curiosity,fairness,forgiveness,gratitude,honesty,hope,humility,humor,judgement,kindness,leadership,love,learning,perseverance,perspective,prudence,selfregulation,socialintelligence,spirituality,teamwork,zest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_cs);

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
        selfregulation=(Button)findViewById(R.id.buttonSelfRegulation);
        socialintelligence=(Button)findViewById(R.id.buttonSocialIntelligence);
        spirituality=(Button)findViewById(R.id.buttonSpirituality);
        teamwork=(Button)findViewById(R.id.buttonTeamwork);
        zest=(Button)findViewById(R.id.buttonZest);

        appreciation.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    appreciation.setBackgroundResource(R.drawable.appreciation_light);
                    check=0;
                }else {
                    appreciation.setBackgroundResource(R.drawable.appreciation_dark);
                    check=1;
                }


            }

        });
        bravery.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    bravery.setBackgroundResource(R.drawable.bravery_light);
                    check=0;
                }else {
                    bravery.setBackgroundResource(R.drawable.bravery_dark);
                    check=1;
                }


            }

        });
        creativity.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    creativity.setBackgroundResource(R.drawable.creativity_light);
                    check=0;
                }else {
                    creativity.setBackgroundResource(R.drawable.creativity_dark);
                    check=1;
                }


            }

        });
        curiosity.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    curiosity.setBackgroundResource(R.drawable.curiosity_light);
                    check=0;
                }else {
                    curiosity.setBackgroundResource(R.drawable.curiosity_dark);
                    check=1;
                }


            }

        });
        fairness.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    fairness.setBackgroundResource(R.drawable.fairness_light);
                    check=0;
                }else {
                    fairness.setBackgroundResource(R.drawable.fairness_dark);
                    check=1;
                }


            }

        });
        forgiveness.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    forgiveness.setBackgroundResource(R.drawable.forgiveness_light);
                    check=0;
                }else {
                    forgiveness.setBackgroundResource(R.drawable.forgiveness_dark);
                    check=1;
                }


            }

        });
        gratitude.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    gratitude.setBackgroundResource(R.drawable.gratitude_light);
                    check=0;
                }else {
                    gratitude.setBackgroundResource(R.drawable.gratitude_dark);
                    check=1;
                }


            }

        });
        honesty.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    honesty.setBackgroundResource(R.drawable.honesty_light);
                    check=0;
                }else {
                    honesty.setBackgroundResource(R.drawable.honesty_dark);
                    check=1;
                }


            }

        });
        hope.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    hope.setBackgroundResource(R.drawable.hope_light);
                    check=0;
                }else {
                    hope.setBackgroundResource(R.drawable.hope_dark);
                    check=1;
                }


            }

        });
        humility.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    humility.setBackgroundResource(R.drawable.humility_light);
                    check=0;
                }else {
                    humility.setBackgroundResource(R.drawable.humility_dark);
                    check=1;
                }


            }

        });
        humor.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    humor.setBackgroundResource(R.drawable.humor_light);
                    check=0;
                }else {
                    humor.setBackgroundResource(R.drawable.humor_dark);
                    check=1;
                }


            }

        });
        judgement.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    judgement.setBackgroundResource(R.drawable.judgement_light);
                    check=0;
                }else {
                    judgement.setBackgroundResource(R.drawable.judgement_dark);
                    check=1;
                }


            }

        });
        kindness.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    kindness.setBackgroundResource(R.drawable.kindness_light);
                    check=0;
                }else {
                    kindness.setBackgroundResource(R.drawable.kindness_dark);
                    check=1;
                }


            }

        });
        leadership.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    leadership.setBackgroundResource(R.drawable.leadership_light);
                    check=0;
                }else {
                    leadership.setBackgroundResource(R.drawable.leadership_dark);
                    check=1;
                }


            }

        });
        love.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    love.setBackgroundResource(R.drawable.love_light);
                    check=0;
                }else {
                    love.setBackgroundResource(R.drawable.love_dark);
                    check=1;
                }


            }

        });
        learning.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    learning.setBackgroundResource(R.drawable.lol_light);
                    check=0;
                }else {
                    learning.setBackgroundResource(R.drawable.lol_dark);
                    check=1;
                }


            }

        });
        perseverance.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    perseverance.setBackgroundResource(R.drawable.perseverance_light);
                    check=0;
                }else {
                    perseverance.setBackgroundResource(R.drawable.perseverance_dark);
                    check=1;
                }


            }

        });
        perspective.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    perspective.setBackgroundResource(R.drawable.perspective_light);
                    check=0;
                }else {
                    perspective.setBackgroundResource(R.drawable.perspective_dark);
                    check=1;
                }


            }

        });
        prudence.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    prudence.setBackgroundResource(R.drawable.prudence_light);
                    check=0;
                }else {
                    prudence.setBackgroundResource(R.drawable.prudence_dark);
                    check=1;
                }


            }

        });
        selfregulation.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    selfregulation.setBackgroundResource(R.drawable.self_light);
                    check=0;
                }else {
                    selfregulation.setBackgroundResource(R.drawable.self_dark);
                    check=1;
                }


            }

        });
        socialintelligence.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    socialintelligence.setBackgroundResource(R.drawable.social_light);
                    check=0;
                }else {
                    socialintelligence.setBackgroundResource(R.drawable.social_dark);
                    check=1;
                }


            }

        });
        spirituality.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    spirituality.setBackgroundResource(R.drawable.spirituality_light);
                    check=0;
                }else {
                    spirituality.setBackgroundResource(R.drawable.spirituality_dark);
                    check=1;
                }


            }

        });
        teamwork.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    teamwork.setBackgroundResource(R.drawable.teamwork_light);
                    check=0;
                }else {
                    teamwork.setBackgroundResource(R.drawable.teamwork_dark);
                    check=1;
                }


            }

        });
        zest.setOnClickListener(new View.OnClickListener() {
            int check=1;
            @Override
            public void onClick(View view) {
                if(check==1){
                    zest.setBackgroundResource(R.drawable.zest_light);
                    check=0;
                }else {
                    zest.setBackgroundResource(R.drawable.zest_dark);
                    check=1;
                }


            }

        });


    }

    public void launchRecommendationActivity(View view) {
        Intent intent = new Intent(this, RecommendationActivity.class);
        startActivity(intent);
    }
}
