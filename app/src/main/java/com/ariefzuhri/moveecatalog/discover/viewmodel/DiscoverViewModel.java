package com.ariefzuhri.moveecatalog.discover.viewmodel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ariefzuhri.moveecatalog.RetrofitClient;
import com.ariefzuhri.moveecatalog.movie.MovieResponse;
import com.ariefzuhri.moveecatalog.movie.model.Movie;
import com.ariefzuhri.moveecatalog.tvshow.TVShowResponse;
import com.ariefzuhri.moveecatalog.tvshow.model.TVShow;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.API_KEY;

public class DiscoverViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> listResultMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TVShow>> listResultTVShows = new MutableLiveData<>();

    public void setDiscoverMovie(String QUERY){
        RetrofitClient client = new RetrofitClient();
        final ArrayList<Movie> listFound = new ArrayList<>();
        client.getService().searchMovies(API_KEY, QUERY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                try{
                    if (response.isSuccessful()){
                        MovieResponse movieResponse = response.body();
                        listFound.addAll(movieResponse.getMovies());
                    }
                    listResultMovies.postValue(listFound);
                } catch (Exception error){
                    Log.d("Exception", error.getMessage());
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setDiscoverTVShow(String QUERY){
        RetrofitClient client = new RetrofitClient();
        final ArrayList<TVShow> listFound = new ArrayList<>();
        client.getService().searchTVShows(API_KEY, QUERY).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                try{
                    if (response.isSuccessful()){
                        TVShowResponse tvShowResponse = response.body();
                        listFound.addAll(tvShowResponse.getTVShows());
                    }
                    listResultTVShows.postValue(listFound);
                } catch (Exception error){
                    Log.d("Exception", error.getMessage());
                }

            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Movie>> getDiscoverMovie() {
        return listResultMovies;
    }

    public LiveData<ArrayList<TVShow>> getDiscoverTVShow() {
        return listResultTVShows;
    }
}
