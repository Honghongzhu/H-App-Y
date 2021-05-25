package com.example.happy.screens;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.adapters.HistoryAdapter;
import com.example.happy.adapters.MovieAdapter;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.MovieRatings;
import com.example.happy.queries.NoResult;
import com.example.happy.queries.SavedMovies;
import com.example.happy.queries.UserRatings;
import com.example.happy.queries.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

public class HistoryActivity extends AppCompatActivity {

    List<MovieInfo> movieInfoTable = new List<MovieInfo>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<MovieInfo> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(MovieInfo movieInfo) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends MovieInfo> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends MovieInfo> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public MovieInfo get(int index) {
            return null;
        }

        @Override
        public MovieInfo set(int index, MovieInfo element) {
            return null;
        }

        @Override
        public void add(int index, MovieInfo element) {

        }

        @Override
        public MovieInfo remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<MovieInfo> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<MovieInfo> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<MovieInfo> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    List<MovieRatings> movieRatingsTable = new List<MovieRatings>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<MovieRatings> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(MovieRatings movieRatings) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends MovieRatings> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends MovieRatings> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public MovieRatings get(int index) {
            return null;
        }

        @Override
        public MovieRatings set(int index, MovieRatings element) {
            return null;
        }

        @Override
        public void add(int index, MovieRatings element) {

        }

        @Override
        public MovieRatings remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<MovieRatings> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<MovieRatings> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<MovieRatings> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    List<UserRatings> userRatingsTable;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Retrieve the current user of the app
        int currentUserId = -1;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("CURRENT_USER_ID", -1);
        }

        try{

            userRatingsTable = Utils.executeQuery(
                    UserRatings.class,
                    HistoryActivity.this,
                    "select",
                    "*",
                    "user_ratings",
                    "where",
                    String.format("user_id=%s", currentUserId)
            );

            //if not empty
            if (!userRatingsTable.toString().equals("[]")) {
                ArrayList<String> movieRatingsId = new ArrayList<>();
                for (UserRatings userRating: userRatingsTable){
                    movieRatingsId.add("'" + userRating.getMovieId() + "'");
                }

                movieRatingsTable = Utils.executeQuery(
                        MovieRatings.class,
                        HistoryActivity.this,
                        "select",
                        "*",
                        "movie_ratings",
                        "where",
                        String.format("movie_id in (%s)", String.join(", ", movieRatingsId))
                );

                movieInfoTable = Utils.executeQuery(
                    MovieInfo.class,
                    HistoryActivity.this,
                    "select",
                    "*",
                    "movie_info",
                    "where",
                    String.format("movie_id in (%s)", String.join(", ", movieRatingsId))
                );
            }

        } catch (ExecutionException e) {
            Toast.makeText(HistoryActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(HistoryActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        LinkedList<MovieInfo> moviesInfoRated = new LinkedList<>(movieInfoTable);
        LinkedList<MovieRatings> moviesRatingsRated = new LinkedList<>(movieRatingsTable);
        RecyclerView recyclerView = findViewById(R.id.rv_history);
        HistoryAdapter hAdapter = new HistoryAdapter(this, moviesInfoRated, moviesRatingsRated, currentUserId);
        recyclerView.setAdapter(hAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
