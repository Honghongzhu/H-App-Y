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
import com.example.happy.screens.MovieDetailsActivity;
import com.example.happy.screens.RateActivity;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {
    private final List<MovieInfo> mMovieList;
    private final LayoutInflater inflater;
    private final Boolean isSaved = false;
    private String selectedMovieId = "";
    private int currentUserId = -1;
    private final Context context;

    public static class RankViewHolder extends RecyclerView.ViewHolder{
        public final TextView movieRank;
        public final ImageView movieImage;
        public final TextView movieText;
        public final ImageView saveImage;
        public final Button rateButton;
        final RankAdapter adapter;
        public View itemView;

        public RankViewHolder(View view, RankAdapter adapter){
            super(view);
            itemView = view;
            movieRank = view.findViewById(R.id.rank);
            movieImage = view.findViewById(R.id.rankedMoviePhoto);
            movieText = view.findViewById(R.id.rankedMovieName);
            saveImage = view.findViewById(R.id.rankedMovieSave);
            rateButton = view.findViewById(R.id.rankedButtonRate);
            this.adapter = adapter;
        }

    }

    public RankAdapter(Context context, LinkedList<MovieInfo> mMovieList, int currentUserId){
        inflater = LayoutInflater.from(context);
        this.mMovieList = mMovieList;
        this.currentUserId = currentUserId;
        this.context = context;
    }

    @NonNull
    @Override
    public RankAdapter.RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_item_ranked, parent, false);
        return new RankViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull final RankAdapter.RankViewHolder holder, int position) {
        final MovieInfo movieAtPosition = mMovieList.get(position);
        holder.movieRank.setText(String.valueOf(position+1));

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
//                    holder.saveImage.setBackgroundResource(R.drawable.save_button_dark_fixedsize);
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
