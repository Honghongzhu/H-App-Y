package com.example.happy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final LinkedList<String> moviesToAdapt;
    private final LayoutInflater inflater;
    private Boolean isSaved = false;

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        //TODO: add the corresponding movie images from database, now it's a default
        public final TextView movieText;
        public final ImageView saveImage;
        final MovieAdapter adapter;

        public MovieViewHolder(View view, MovieAdapter adapter){
            super(view);
            movieText = view.findViewById(R.id.movieName);
            saveImage = view.findViewById(R.id.movieSave);
            this.adapter = adapter;
        }
    }

    public MovieAdapter(Context context, LinkedList<String> moviesToAdapt){
        inflater = LayoutInflater.from(context);
        this.moviesToAdapt = moviesToAdapt;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.MovieViewHolder holder, int position) {
        String movieAtPosition = moviesToAdapt.get(position);
        holder.movieText.setText(movieAtPosition); //TODO: get from database

        holder.saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSaved){//it was saved and by clicking it is deleted from saved
                    holder.saveImage.setImageResource(R.drawable.heart_border);
                    // TODO: update database
                }else{ //it was not saved and by clicking it became saved
                    holder.saveImage.setImageResource(R.drawable.heart_filled);
                    // TODO: update database
                }
                isSaved = !isSaved;
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesToAdapt.size();
    }


}
