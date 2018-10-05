package com.example.badiaa.reelzdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.badiaa.reelzdb.R;
import com.example.badiaa.reelzdb.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    Context mContext;

    private List<Movie> mValues;
    private onMovieImageClickHandler mOnMovieImageClickHandler;

    public interface onMovieImageClickHandler {
        void onClick(Movie movie);
    }

    public MovieRecyclerViewAdapter(List<Movie> items, onMovieImageClickHandler clickHandler) {

        mValues = items;
        mOnMovieImageClickHandler = clickHandler;
    }



    @Override
    public MovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie, parent, false);

        mContext = parent.getContext();

        return new MovieRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieRecyclerViewAdapter.ViewHolder holder, final int position) {

        final Movie movie = mValues.get(position);
        final String title = movie.Title;
        final String imdbId = movie.imdbID;
        final String imdbRating = movie.imdbRating;
        final String year = movie.Year;
        holder.mImdbRatingView.setText(imdbRating);
        holder.mTitleView.setText(title);
        holder.mYearView.setText(year);


        final String imageUrl;
        if (! movie.Poster.equals(mContext.getResources().getString(R.string.no_data))) {
            imageUrl = movie.Poster;
        }
        else {
            // Display a no poster image if the movie has no Poster
            imageUrl = mContext.getResources().getString(R.string.no_poster);

        }

        // Use Picasso Lib to display images
        Picasso.with(mContext).load(imageUrl).into(holder.mImageView);
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final View mView;
        public final TextView mTitleView;
        public final TextView mYearView;
        public final TextView mImdbRatingView;
        public final ImageView mImageView;

        public ViewHolder(View view) {

            super(view);
            mView = view;
            mTitleView = view.findViewById(R.id.movie_title);
            mYearView = view.findViewById(R.id.movie_year);
            mImageView = view.findViewById(R.id.thumbnail);
            mImdbRatingView = view.findViewById(R.id.imdb_rating);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie selectedMovie = mValues.get(position);
            mOnMovieImageClickHandler.onClick(selectedMovie);
        }

    }

    public void swapData(List<Movie> items) {
        if(items != null) {
            mValues = items;
            notifyDataSetChanged();

        }
        else {
            mValues = null;
        }
    }

    @Override
    public int getItemCount() {
        if(mValues == null) {
            return 0;
        }
        return mValues.size();
    }

}
