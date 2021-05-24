package com.example.happy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happy.R;
import com.example.happy.queries.MovieInfo;
import com.example.happy.screens.RateActivity;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final List<MovieInfo> mMovieList;
    private final LayoutInflater inflater;
    private final Boolean isSaved = false;
    private String selectedMovieId = "";
    private int currentUserId = -1;
    private final Context context;

    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        public final ImageView movieImage;
        public final TextView movieText;
        public final ImageView saveImage;
        public final Button rateButton;
        final MovieAdapter adapter;

        public MovieViewHolder(View view, MovieAdapter adapter){
            super(view);
            movieImage = view.findViewById(R.id.moviePhoto);
            movieText = view.findViewById(R.id.movieName);
            saveImage = view.findViewById(R.id.movieSave);
            rateButton = view.findViewById(R.id.buttonRate);
            this.adapter = adapter;
        }
    }

    public MovieAdapter(Context context, LinkedList<MovieInfo> mMovieList, int currentUserId){
        inflater = LayoutInflater.from(context);
        this.mMovieList = mMovieList;
        this.currentUserId = currentUserId;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.MovieViewHolder holder, int position) {

        final MovieInfo movieAtPosition = mMovieList.get(position);
        String imgURL = movieAtPosition.getPosterUrl();
        Picasso.get().load(imgURL).into(holder.movieImage);
        holder.movieText.setText(movieAtPosition.getPrimaryTitle() + " (" + movieAtPosition.getStartYear() + ")");
        holder.rateButton.setOnClickListener(v -> {
            selectedMovieId = movieAtPosition.getMovieId();
            Intent intent = new Intent(holder.adapter.context, RateActivity.class);
            intent.putExtra("SELECTED_MOVIE_ID", selectedMovieId);
            intent.putExtra("CURRENT_USER_ID", holder.adapter.currentUserId);
            context.startActivity(intent);
        });

//        holder.saveImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isSaved){//it was saved and by clicking it is deleted from saved
//                     holder.saveImage.setBackgroundResource(R.drawable.save_button_dark_fixedsize);
//                    // TODO: update database
//                    movieAtPosition.setSaved(false);
//                }else{ //it was not saved and by clicking it became saved
//                    holder.saveImage.setBackgroundResource(R.drawable.save_button_light_fixedsize);
//                    // TODO: update database
//                    movieAtPosition.setSaved(true);
//                }
//                isSaved = !isSaved;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

}
