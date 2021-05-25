package com.example.happy.adapters;

import android.app.Activity;
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
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.NoResult;
import com.example.happy.queries.SavedMovies;
import com.example.happy.queries.Utils;
import com.example.happy.screens.MovieDetailsActivity;
import com.example.happy.screens.RateActivity;
import com.example.happy.screens.RateCSActivity;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final List<SavedMovies> savedMovieList;
    private final List<MovieInfo> mMovieList;
    private final LayoutInflater inflater;
    private Boolean isSaved = false;
    private String selectedMovieId = "";
    private int currentUserId = -1;
    private final Context context;

    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        public final ImageView movieImage;
        public final TextView movieText;
        public final ImageView saveImage;
        public final Button rateButton;
        final MovieAdapter adapter;
        public View itemView;

        public MovieViewHolder(View view, MovieAdapter adapter){
            super(view);
            itemView = view;
            movieImage = view.findViewById(R.id.moviePhoto);
            movieText = view.findViewById(R.id.movieName);
            saveImage = view.findViewById(R.id.movieSave);
            rateButton = view.findViewById(R.id.buttonRate);
            this.adapter = adapter;
        }
    }

    public MovieAdapter(Context context, LinkedList<MovieInfo> mMovieList, LinkedList<SavedMovies> savedMovieList, int currentUserId){
        inflater = LayoutInflater.from(context);
        this.mMovieList = mMovieList;
        this.savedMovieList = savedMovieList;
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

        int idx = -1;

        //iterate over the movies to find the saved one
        for (SavedMovies saved : savedMovieList){
            if (saved.getMovieId().equals(movieAtPosition.getMovieId())){
                idx = savedMovieList.indexOf(saved);
                break;
            }
        }

        SavedMovies savedAtPosition = null;

        // if the index was found
        if (idx != -1){
            savedAtPosition  = savedMovieList.get(idx);
        }

        String imgURL = movieAtPosition.getPosterUrl();
        Picasso.get().load(imgURL).into(holder.movieImage);

        holder.movieText.setText(movieAtPosition.getPrimaryTitle() + " (" + movieAtPosition.getStartYear() + ")");
        holder.rateButton.setOnClickListener(v -> {
            Intent intent = new Intent(holder.adapter.context, RateActivity.class);
            intent.putExtra("SELECTED_MOVIE_ID", movieAtPosition.getMovieId());
            intent.putExtra("CURRENT_USER_ID", holder.adapter.currentUserId);
            context.startActivity(intent);
        });

        if (savedAtPosition != null && Integer.parseInt(savedAtPosition.getUserId()) == currentUserId){
            isSaved = true;
            holder.saveImage.setBackgroundResource(R.drawable.save_button_light_fixedsize);
        }

        holder.saveImage.setOnClickListener(v -> {
            // if the movie is saved and therefore clicked to be unsaved
            if (isSaved){

                try {
                    List<NoResult> deleteResult = Utils.executeQuery(
                            NoResult.class,
                            (Activity) holder.adapter.context,
                            "delete",
                            "",
                            "saved_movies",
                            "where",
                            String.format("movie_id='%s' and user_id=%s", movieAtPosition.getMovieId(), currentUserId)
                    );

                    if (!deleteResult.toString().equals("[]")){
                        holder.saveImage.setBackgroundResource(R.drawable.save_button_dark_fixedsize);
                    }

                } catch (ExecutionException e) {
                    Toast.makeText((Activity) holder.adapter.context, e.toString(), Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    Toast.makeText((Activity) holder.adapter.context, e.toString(), Toast.LENGTH_LONG).show();
                }

            }else{

                try {
                    List<NoResult> insertResult = Utils.executeQuery(
                            NoResult.class,
                            (Activity) holder.adapter.context,
                            "insert",
                            "(user_id, movie_id)",
                            "saved_movies",
                            "values",
                            String.format("(%s, '%s')", currentUserId, movieAtPosition.getMovieId())
                    );

                    if (!insertResult.toString().equals("[]")){
                        holder.saveImage.setBackgroundResource(R.drawable.save_button_light_fixedsize);
                    }

                } catch (ExecutionException e) {
                    Toast.makeText((Activity) holder.adapter.context, e.toString(), Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    Toast.makeText((Activity) holder.adapter.context, e.toString(), Toast.LENGTH_LONG).show();
                }

            }
            isSaved = !isSaved;
        });

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("MOVIE_ID", movieAtPosition.getMovieId());
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

}
