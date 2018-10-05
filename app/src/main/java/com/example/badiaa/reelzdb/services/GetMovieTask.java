package com.example.badiaa.reelzdb.services;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.badiaa.reelzdb.models.Movie;
import com.example.badiaa.reelzdb.models.Result;

import java.io.IOException;

public class GetMovieTask extends AsyncTaskLoader<MovieSearchService.ResultData> {

    private static final String LOG_TAG = "GetMovieTask";

    private final String mTitle;

    private MovieSearchService.ResultData mData;

    public GetMovieTask(Context context, String title) {
        super(context);
        mTitle = title;
    }

    @Override
    public MovieSearchService.ResultData loadInBackground() {

        // Run the search and return the results
        try {
            Result result =  MovieSearchService.executeSearch(mTitle);
            MovieSearchService.ResultData resultData = new MovieSearchService.ResultData(result);

            if(result.Search != null) {
                for(Movie movie: result.Search) {
                    resultData.addToList(MovieSearchService.getDetail(movie.imdbID));
                }
            }
            return  resultData;
        }
        catch(final IOException e) {
            Log.e(LOG_TAG, "Could not connect to the external resource/API", e);
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        }
        else {
            forceLoad();
        }
    }


    @Override
    protected void onReset() {
        super.onReset();
        mData = null;
    }

    @Override
    public void deliverResult(MovieSearchService.ResultData data) {

        if (isReset()) {
            return;
        }

        mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

    }
}

