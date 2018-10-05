package com.example.badiaa.reelzdb.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.badiaa.reelzdb.R;
import com.example.badiaa.reelzdb.models.Movie;
import com.squareup.picasso.Picasso;


public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_INFO = "movie_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        final Movie movie = getIntent().getParcelableExtra(MOVIE_INFO);

        final String imageUrl =  movie.Poster;

        Picasso.with(this).load(imageUrl).into((ImageView) findViewById(R.id.main_poster));


        ((TextView) findViewById(R.id.detail_title)).setText(movie.Title);
        ((TextView) findViewById(R.id.detail_released)).setText(movie.Released);
        ((TextView) findViewById(R.id.detail_imdbrating)).setText(movie.imdbRating);
        ((TextView) findViewById(R.id.detail_writers)).setText(movie.Writer);
        ((TextView) findViewById(R.id.detail_actors)).setText(movie.Actors);
        ((TextView) findViewById(R.id.detail_director)).setText(movie.Director);
        ((TextView) findViewById(R.id.detail_genre)).setText(movie.Genre);
        ((TextView) findViewById(R.id.detail_plot)).setText(movie.Plot);
        ((TextView) findViewById(R.id.detail_runtime)).setText(movie.Runtime);

    }

}