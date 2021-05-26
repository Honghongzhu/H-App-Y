package com.example.happy.queries;

public class MovieRatings {

    private String movieId, votesEnjoyment, averageEnjoyment, votesMeaning, averageMeaning, 
            creativity, curiosity, judgement, loveOfLearning, perspective, bravery, honesty,
            zest, perseverance, love, kindness, socialIntelligence, teamwork, fairness, leadership, 
            forgiveness, humility, prudence, selfRegulation, appreciationBeautyExcellence, 
            gratitude, hope, humor, spirituality;

    public String[] getAll() {
        return new String[] {
                getCreativity(), getCuriosity(), getJudgement(), getLoveOfLearning(),
                getPerspective(), getBravery(), getHonesty(), getZest(), getPerseverance(),
                getLove(), getKindness(), getSocialIntelligence(), getTeamwork(), getFairness(),
                getLeadership(), getForgiveness(), getHumility(), getPrudence(), getSelfRegulation(),
                getAppreciationBeautyExcellence(), getGratitude(), getHope(), getHumor(), getSpirituality()
        };
    }

    public String getMovieId() {
        return movieId;
    }

    public String getVotesEnjoyment() {
        return votesEnjoyment;
    }

    public String getAverageEnjoyment() {
        return averageEnjoyment;
    }

    public String getVotesMeaning() {
        return votesMeaning;
    }

    public String getAverageMeaning() {
        return averageMeaning;
    }

    public String getCreativity() {
        return creativity;
    }

    public String getCuriosity() {
        return curiosity;
    }

    public String getJudgement() {
        return judgement;
    }

    public String getLoveOfLearning() {
        return loveOfLearning;
    }

    public String getPerspective() {
        return perspective;
    }

    public String getBravery() {
        return bravery;
    }

    public String getHonesty() {
        return honesty;
    }

    public String getZest() {
        return zest;
    }

    public String getPerseverance() {
        return perseverance;
    }

    public String getLove() {
        return love;
    }

    public String getKindness() {
        return kindness;
    }

    public String getSocialIntelligence() {
        return socialIntelligence;
    }

    public String getTeamwork() {
        return teamwork;
    }

    public String getFairness() {
        return fairness;
    }

    public String getLeadership() {
        return leadership;
    }

    public String getForgiveness() {
        return forgiveness;
    }

    public String getHumility() {
        return humility;
    }

    public String getPrudence() {
        return prudence;
    }

    public String getSelfRegulation() {
        return selfRegulation;
    }

    public String getAppreciationBeautyExcellence() {
        return appreciationBeautyExcellence;
    }

    public String getGratitude() {
        return gratitude;
    }

    public String getHope() {
        return hope;
    }

    public String getHumor() {
        return humor;
    }

    public String getSpirituality() {
        return spirituality;
    }

    public MovieRatings(String movieId, String votesEnjoyment, String averageEnjoyment, String votesMeaning, String averageMeaning, String creativity, String curiosity, String judgement, String loveOfLearning, String perspective, String bravery, String honesty, String zest, String perseverance, String love, String kindness, String socialIntelligence, String teamwork, String fairness, String leadership, String forgiveness, String humility, String prudence, String selfRegulation, String appreciationBeautyExcellence, String gratitude, String hope, String humor, String spirituality) {
        this.movieId = movieId;
        this.votesEnjoyment = votesEnjoyment;
        this.averageEnjoyment = averageEnjoyment;
        this.votesMeaning = votesMeaning;
        this.averageMeaning = averageMeaning;
        this.creativity = creativity;
        this.curiosity = curiosity;
        this.judgement = judgement;
        this.loveOfLearning = loveOfLearning;
        this.perspective = perspective;
        this.bravery = bravery;
        this.honesty = honesty;
        this.zest = zest;
        this.perseverance = perseverance;
        this.love = love;
        this.kindness = kindness;
        this.socialIntelligence = socialIntelligence;
        this.teamwork = teamwork;
        this.fairness = fairness;
        this.leadership = leadership;
        this.forgiveness = forgiveness;
        this.humility = humility;
        this.prudence = prudence;
        this.selfRegulation = selfRegulation;
        this.appreciationBeautyExcellence = appreciationBeautyExcellence;
        this.gratitude = gratitude;
        this.hope = hope;
        this.humor = humor;
        this.spirituality = spirituality;
    }
}
