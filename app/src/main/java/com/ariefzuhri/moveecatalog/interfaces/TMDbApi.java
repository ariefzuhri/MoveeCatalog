package com.ariefzuhri.moveecatalog.interfaces;

import com.ariefzuhri.moveecatalog.response.CreditResponse;
import com.ariefzuhri.moveecatalog.movie.model.DetailMovie;
import com.ariefzuhri.moveecatalog.movie.MovieResponse;
import com.ariefzuhri.moveecatalog.response.TrailerResponse;
import com.ariefzuhri.moveecatalog.tvshow.TVShowResponse;
import com.ariefzuhri.moveecatalog.tvshow.model.DetailTVShow;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDbApi{

    // Mendapatkan info kredit movie/tv
    @GET("{media}/{id}/credits")
    Call<CreditResponse> getCredits(
            @Path("media") String MEDIA_TYPE,
            @Path("id") String ID,
            @Query("api_key") String API_KEY
    );

    /**
     * Untuk movie
     */
    @GET("trending/movie/day")
    Call<MovieResponse> getTrendingMovies(
            @Query("api_key") String API_KEY,
            @Query("page") int PAGE
    );

    @GET("movie/now_playing")
    Call<MovieResponse> getInTheatersMovies(
            @Query("api_key") String API_KEY,
            @Query("page") int PAGE
    );

    @GET("discover/movie")
    Call<MovieResponse> getReleaseTodayMovies(
            @Query("api_key") String API_KEY,
            @Query("primary_release_date.gte") String TODAY_DATE,
            @Query("primary_release_date.lte") String TODAT_DATE,
            @Query("page") int PAGE
    );

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(
            @Query("api_key") String API_KEY,
            @Query("page") int PAGE
    );

    @GET("movie/{id}")
    Call<DetailMovie> getDetailMovie(
            @Path("id") String MOVIE_ID,
            @Query("api_key") String API_KEY
    );

    @GET("search/movie")
    Call<MovieResponse> searchMovies(
            @Query("api_key") String API_KEY,
            @Query("query") String QUERY
    );

    @GET("movie/{id}/videos")
    Call<TrailerResponse> getMovieTrailers(
            @Path("id") String MOVIE_ID,
            @Query("api_key") String API_KEY,
            @Query("language") String LANGUAGE
    );

    /**
     * Untuk tv
     */

    @GET("trending/tv/day")
    Call<TVShowResponse> getTrendingTVShows(
            @Query("api_key") String API_KEY,
            @Query("page") int PAGE
    );

    @GET("tv/airing_today")
    Call<TVShowResponse> getOnAirTVShows(
            @Query("api_key") String API_KEY,
            @Query("page") int PAGE
    );

    @GET("tv/{id}")
    Call<DetailTVShow> getDetailTVShow(
            @Path("id") String TV_ID,
            @Query("api_key") String API_KEY
    );

    @GET("search/tv")
    Call<TVShowResponse> searchTVShows(
            @Query("api_key") String API_KEY,
            @Query("query") String QUERY
    );
}