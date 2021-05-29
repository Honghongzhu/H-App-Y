package com.example.happy.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.media.Image;
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
import com.example.happy.screens.MovieDetailsActivity;
import com.example.happy.screens.RateActivity;
import com.example.happy.screens.SavedActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
        public final RatingBar enjoyRate;
        public final RatingBar meaningRate;
        public final ImageView imageCS1;
        public final ImageView imageCS2;
        public final ImageView imageCS3;
        public final ImageView imageCS4;
        final HistoryAdapter adapter;
        public View itemView;

        public MovieViewHolder(View view, HistoryAdapter adapter){
            super(view);
            itemView = view;
            movieImage = view.findViewById(R.id.historyMoviePhoto);
            movieText = view.findViewById(R.id.historyMovieName);
            enjoyRate = view.findViewById(R.id.historyEnjoyRating);
            meaningRate = view.findViewById(R.id.historyMeaningfulRating);
            imageCS1 = view.findViewById(R.id.historyCS1);
            imageCS2 = view.findViewById(R.id.historyCS2);
            imageCS3 = view.findViewById(R.id.historyCS3);
            imageCS4 = view.findViewById(R.id.historyCS4);
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

        ArrayList<String> characterStrengths = Utils.getCSFromUserRatings(movieRatingAtPosition);

        switch(characterStrengths.size()){
            case 1:
                holder.imageCS1.setImageResource(Utils.getCSResourceId(characterStrengths.get(0)));
                break;
            case 2:
                holder.imageCS1.setImageResource(Utils.getCSResourceId(characterStrengths.get(0)));
                holder.imageCS2.setImageResource(Utils.getCSResourceId(characterStrengths.get(1)));
                break;
            case 3:
                holder.imageCS1.setImageResource(Utils.getCSResourceId(characterStrengths.get(0)));
                holder.imageCS2.setImageResource(Utils.getCSResourceId(characterStrengths.get(1)));
                holder.imageCS3.setImageResource(Utils.getCSResourceId(characterStrengths.get(2)));
                break;
            case 4:
                holder.imageCS1.setImageResource(Utils.getCSResourceId(characterStrengths.get(0)));
                holder.imageCS2.setImageResource(Utils.getCSResourceId(characterStrengths.get(1)));
                holder.imageCS3.setImageResource(Utils.getCSResourceId(characterStrengths.get(2)));
                holder.imageCS4.setImageResource(Utils.getCSResourceId(characterStrengths.get(3)));
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("MOVIE_ID", movieAtPosition.getMovieId());
                intent.putExtra("CURRENT_USER_ID", currentUserId);
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

}
