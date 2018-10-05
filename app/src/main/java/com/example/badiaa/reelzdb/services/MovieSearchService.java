package com.example.badiaa.reelzdb.services;


import com.example.badiaa.reelzdb.BuildConfig;
import com.example.badiaa.reelzdb.models.Movie;
import com.example.badiaa.reelzdb.models.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MovieSearchService {

    private static final String API_URL = "http://www.omdbapi.com";
    private static MovieAPI sMovieAPI;

    public static class ResultData {
        
        private List<Movie> movieList;
        private String Response;

        public ResultData(Result result) {

            this.Response = result.Response;
            movieList = new ArrayList<>();
        }

        public void addToList(Movie movie) {
            movieList.add(movie);
        }

        public List<Movie> getMovieList() {
            return movieList;
        }

        public String getResponse() {
            return Response;
        }
    }


    public interface MovieAPI {

        @GET("?type=movie")
        Call<Result> Result(
                @Query("s") String Title);

        @GET("?plot=full")
        Call<Movie> Movie(
                @Query("i") String ImdbId);
    }



    private static void createCommunicator() {

        // Method to set up the connection to the API and creating an instance of interface MovieAPI

        if (sMovieAPI == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("apikey", BuildConfig.MOVIE_DB_API_KEY)
                            .build();

                    // Adding request headers: API Key
                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            sMovieAPI = retrofit.create(MovieAPI.class);
        }
    }

    public static Result executeSearch(String title) throws IOException {

        createCommunicator();

        Call<Result> call = sMovieAPI.Result(title);

        return call.execute().body();
    }

    public static Movie getDetail(String imdbId) throws IOException {

        createCommunicator();

        Call<Movie> call = sMovieAPI.Movie(imdbId);

        return call.execute().body();
    }
}
