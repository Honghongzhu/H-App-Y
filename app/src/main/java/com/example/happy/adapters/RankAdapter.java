package com.example.happy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happy.data.Movie;
import com.example.happy.R;

import java.util.LinkedList;
import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {
    private List<Movie> mMovieList;
    private final LayoutInflater inflater;
    private Boolean isSaved = false;

    public void setData(List<Movie> mMovieList){
        this.mMovieList = mMovieList;
    }

    public class RankViewHolder extends RecyclerView.ViewHolder{
        public final TextView movieRank;
        public final ImageView movieImage;
        public final TextView movieText;
        public final ImageView saveImage;
        final RankAdapter adapter;

        public RankViewHolder(View view, RankAdapter adapter){
            super(view);
            movieRank = view.findViewById(R.id.rank);
            movieImage = view.findViewById(R.id.rankedMoviePhoto);
            movieText = view.findViewById(R.id.rankedMovieName);
            saveImage = view.findViewById(R.id.rankedMovieSave);
            this.adapter = adapter;
        }
    }

    public RankAdapter(Context context, LinkedList<Movie> mMovieList){
        inflater = LayoutInflater.from(context);
        this.mMovieList = mMovieList;
    }

    @NonNull
    @Override
    public RankAdapter.RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_item_ranked, parent, false);
        return new RankViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull final RankAdapter.RankViewHolder holder, int position) {
        final Movie movieAtPosition = mMovieList.get(position);
        holder.movieRank.setText(movieAtPosition.getItemId());
        holder.movieImage.setImageResource(movieAtPosition.getImageDrawableId());
        holder.movieText.setText(movieAtPosition.getName());

        holder.saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSaved){//it was saved and by clicking it is deleted from saved
                    holder.saveImage.setBackgroundResource(R.drawable.save_button_dark_fixedsize);
                    // TODO: update database
                    movieAtPosition.setSaved(false);
                }else{ //it was not saved and by clicking it became saved
                    holder.saveImage.setBackgroundResource(R.drawable.save_button_light_fixedsize);
                    // TODO: update database
                    movieAtPosition.setSaved(true);
                }
                isSaved = !isSaved;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

}
