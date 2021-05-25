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
import com.example.happy.screens.RateActivity;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Top12Adapter extends RecyclerView.Adapter<Top12Adapter.Top12ViewHolder> {

    private final List<SavedMovies> savedMovieList;
    private final List<MovieInfo> mMovieList;
    private final List<String> recommendationScores;
    private final LayoutInflater inflater;
    private Boolean isSaved = false;
    private final int currentUserId;
    private final Context context;

    public static class Top12ViewHolder extends RecyclerView.ViewHolder{
        public final ImageView movieImage;
        public final TextView movieText;
        public final ImageView saveImage;
        public final Button rateButton;
        public final TextView similarityScore;
        final Top12Adapter adapter;

        public Top12ViewHolder(View view, Top12Adapter adapter){
            super(view);
            movieImage = view.findViewById(R.id.top12MoviePhoto);
            movieText = view.findViewById(R.id.top12MovieName);
            saveImage = view.findViewById(R.id.top12MovieSave);
            rateButton = view.findViewById(R.id.top12ButtonRate);
            similarityScore = view.findViewById(R.id.top12SimilarityScore);
            this.adapter = adapter;
        }
    }

    public Top12Adapter(Context context, LinkedList<MovieInfo> mMovieList, LinkedList<SavedMovies> savedMovieList, int currentUserId, List<String> recommendationScores){
        inflater = LayoutInflater.from(context);
        this.mMovieList = mMovieList;
        this.savedMovieList = savedMovieList;
        this.currentUserId = currentUserId;
        this.recommendationScores = recommendationScores;
        this.context = context;
    }

    @NonNull
    @Override
    public Top12Adapter.Top12ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.top12_item, parent, false);
        return new Top12ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull final Top12Adapter.Top12ViewHolder holder, int position) {

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

        int roundedCosineSimilarity = Math.round(Float.parseFloat(holder.adapter.recommendationScores.get(position)) * 100);

        holder.similarityScore.setText(Integer.toString(roundedCosineSimilarity) + "% Match");

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
                    Toast.makeText(holder.adapter.context, e.toString(), Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    Toast.makeText(holder.adapter.context, e.toString(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(holder.adapter.context, e.toString(), Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    Toast.makeText(holder.adapter.context, e.toString(), Toast.LENGTH_LONG).show();
                }

            }
            isSaved = !isSaved;
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

}
