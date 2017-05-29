package com.example.olamide.moviesa.rest;

import com.example.olamide.moviesa.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.R.attr.apiKey;
/**
 * Created by Olamide on 5/22/17.
 */

public interface Service {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<MovieResponse> getMoviesDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
