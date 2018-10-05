package com.example.badiaa.reelzdb.models;


import java.util.List;

public class Result {
    public List<Movie> Search;
    public String Response;


    @Override
    public String toString() {
        return "Result{" +
                "Search=" + Search +
                ", Response='" + Response + '\'' +
                '}';
    }

}
