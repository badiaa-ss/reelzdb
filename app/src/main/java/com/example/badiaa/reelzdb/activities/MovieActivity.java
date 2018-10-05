package com.example.badiaa.reelzdb.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.Snackbar;


import com.example.badiaa.reelzdb.R;
import com.example.badiaa.reelzdb.adapters.MovieRecyclerViewAdapter;
import com.example.badiaa.reelzdb.models.Movie;

import com.example.badiaa.reelzdb.services.MovieSearchService;
import com.example.badiaa.reelzdb.services.GetMovieTask;
import com.example.badiaa.reelzdb.utils.Common;
import com.example.badiaa.reelzdb.utils.Network;


public class MovieActivity extends AppCompatActivity
        implements MovieRecyclerViewAdapter.onMovieImageClickHandler, LoaderManager.LoaderCallbacks<MovieSearchService.ResultData>{


    private GridLayoutManager gridLayoutManager;
    ImageView mSearchImage;
    private EditText mSearchEditText;
    private RecyclerView mMovieListRecyclerView;
    private MovieRecyclerViewAdapter mMovieAdapter;
    private String mMovieTitle;

    private static final int LOADER_ID = 1;

    private static final String LOG_TAG = "MovieActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        mSearchEditText = findViewById(R.id.search_edittext);

        // Specify 'Search' on the keyboard and set listner
        mSearchEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    launchSearch();
                    handled = true;
                }
                return handled;
            }
        });

        mSearchImage = findViewById(R.id.search_image);
        mMovieListRecyclerView = findViewById(R.id.recycler_view);
        mSearchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSearch();
            }
        });

        mMovieAdapter = new MovieRecyclerViewAdapter(null, this);
        mMovieListRecyclerView.setAdapter(mMovieAdapter);

        // Display two columns if in portrait mode and 4 columns if in landscape mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager( this, 2, LinearLayoutManager.VERTICAL, false);
        }
        else {
            gridLayoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
        }

        // Attach the grid layout manager to the recycler view
        mMovieListRecyclerView.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mMovieTitle", mMovieTitle);

    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        mMovieTitle = savedInstanceState.getString("mMovieTitle");
        if (mMovieTitle != null) {
            Bundle args = new Bundle();
            args.putString("movieTitle", mMovieTitle);
            getSupportLoaderManager().initLoader(LOADER_ID, args, this);
        }
    }

    // Implementation of abstract class onClick in MovieRecyclerViewAdapter.onMovieImageClickHandler
    // Used with help from this site: https://www.androiddesignpatterns.com/2012/08/implementing-loaders.html
    // And https://medium.com/google-developers/making-loading-data-on-android-lifecycle-aware-897e12760832

    @Override
    public void onClick(Movie movie) {


        Intent intent = new Intent(MovieActivity.this, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.MOVIE_INFO, movie);

        startActivity(intent);

    }


    @Override
    public Loader<MovieSearchService.ResultData> onCreateLoader(int id, Bundle args) {
        return new GetMovieTask(MovieActivity.this, args.getString("movieTitle"));
    }

    @Override
    public void onLoadFinished(Loader<MovieSearchService.ResultData> loader, MovieSearchService.ResultData ResultData) {

        mMovieListRecyclerView.setVisibility(View.VISIBLE);

        if(ResultData.getResponse().equals("True")) {
            mMovieAdapter.swapData(ResultData.getMovieList());
        }
        // Movie not found
        else {
            Snackbar.make(mMovieListRecyclerView,
                    getResources().getString(R.string.title_not_found), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<MovieSearchService.ResultData> loader) {
        mMovieAdapter.swapData(null);
    }


    // Begin the search
    private void launchSearch() {

        // Check first if there is network connectivity before running any network related tasks
        if(Network.isNetworkAvailable(getApplicationContext())) {

            // Collapse the keyboard once the search is initiated
            Common.hideKeyboard(MovieActivity.this);

            String movieTitle = mSearchEditText.getText().toString().trim();
            if (!movieTitle.isEmpty()) {
                Bundle args = new Bundle();
                args.putString("movieTitle", movieTitle);
                getSupportLoaderManager().restartLoader(LOADER_ID, args, this);
                mMovieTitle = movieTitle;
                mMovieListRecyclerView.setVisibility(View.GONE);
            }
            else
                Snackbar.make(mMovieListRecyclerView,
                        getResources().getString(R.string.empty_title),
                        Snackbar.LENGTH_LONG).show();
        }
        else {
            Snackbar.make(mMovieListRecyclerView,
                    getResources().getString(R.string.network_not_available),
                    Snackbar.LENGTH_LONG).show();
        }
    }

}
