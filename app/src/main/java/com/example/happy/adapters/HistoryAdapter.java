package com.example.happy.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.MovieRatings;
import com.example.happy.queries.UserRatings;
import com.example.happy.queries.Utils;
import com.example.happy.screens.HistoryActivity;
import com.example.happy.screens.RateActivity;
import com.example.happy.screens.SavedActivity;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MovieViewHolder> {

    private List<MovieInfo> mMovieList;
    private List<UserRatings> UserRatingList;
    private final LayoutInflater inflater;
    private Boolean isSaved = false;
    private String selectedMovieId = "";
    private int currentUserId = -1;
    private Context context;

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        public final ImageView movieImage;
        public final TextView movieText;
        public final ImageView saveImage;
        public final RatingBar enjoyRate;
        public final RatingBar meaningRate;
        final HistoryAdapter adapter;

        public MovieViewHolder(View view, HistoryAdapter adapter){
            super(view);
            movieImage = view.findViewById(R.id.moviePhoto);
            movieText = view.findViewById(R.id.movieName);
            saveImage = view.findViewById(R.id.movieSave);
            enjoyRate = view.findViewById(R.id.enjoy_rating);
            meaningRate = view.findViewById(R.id.meaningful_rating);
            this.adapter = adapter;
        }
    }

    public HistoryAdapter(Context context, LinkedList<MovieInfo> mMovieList, LinkedList<UserRatings> UserRatingList, int currentUserId){
        inflater = LayoutInflater.from(context);
        this.UserRatingList = UserRatingList;
        this.mMovieList = mMovieList;
        this.currentUserId = currentUserId;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.history_item, parent, false);
        return new MovieViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryAdapter.MovieViewHolder holder, int position) {

        final MovieInfo movieAtPosition = mMovieList.get(position);
        final UserRatings movieRatingAtPosition = UserRatingList.get(position);

        String imgURL = movieAtPosition.getPosterUrl();
        Picasso.get().load(imgURL).into(holder.movieImage);
        holder.movieText.setText(movieAtPosition.getPrimaryTitle() + " (" + movieAtPosition.getStartYear() + ")");
        holder.enjoyRate.setRating(Float.parseFloat(movieRatingAtPosition.getEnjoymentRating()));
        holder.meaningRate.setRating(Float.parseFloat(movieRatingAtPosition.getMeaningRating()));

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

}
