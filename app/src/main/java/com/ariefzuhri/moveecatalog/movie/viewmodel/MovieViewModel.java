package com.ariefzuhri.moveecatalog.movie.viewmodel;

import android.app.Activity;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ariefzuhri.moveecatalog.RetrofitClient;
import com.ariefzuhri.moveecatalog.movie.MovieResponse;
import com.ariefzuhri.moveecatalog.movie.model.Movie;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.API_KEY;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.getTodayDate;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.showErrorDialog;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> listInTheatersMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> listReleaseTodayMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> listUpcomingMovies = new MutableLiveData<>();

    private ArrayList<Movie> listFoundMovies = new ArrayList<>();
    private ArrayList<Movie> listFoundInTheatersMovies = new ArrayList<>();
    private ArrayList<Movie> listFoundReleaseTodayMovies = new ArrayList<>();
    private ArrayList<Movie> listFoundUpcomingMovies = new ArrayList<>();

    public void setMovie(final Activity activity, int page){
        RetrofitClient client = new RetrofitClient();
        client.getService().getTrendingMovies(API_KEY, page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                try{
                    if (response.isSuccessful()){
                        MovieResponse movieResponse = response.body();
                        listFoundMovies.addAll(movieResponse.getMovies());
                    }
                    listMovies.postValue(listFoundMovies);
                } catch (Exception error){
                    Log.d("Exception", error.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable error) {
                Log.d("onFailure", error.getMessage());
                showErrorDialog(activity);
            }
        });
    }

    public void setInTheatersMovie(int page){
        RetrofitClient client = new RetrofitClient();
        client.getService().getInTheatersMovies(API_KEY, page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                try{
                    if (response.isSuccessful()){
                        MovieResponse movieResponse = response.body();
                        listFoundInTheatersMovies.addAll(movieResponse.getMovies());
                    }
                    listInTheatersMovies.postValue(listFoundInTheatersMovies);
                } catch (Exception error){
                    Log.d("Exception", error.getMessage());
                }

            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setReleaseTodayMovie(int page){
        RetrofitClient client = new RetrofitClient();
        client.getService().getReleaseTodayMovies(API_KEY, getTodayDate(), getTodayDate(), page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                try{
                    if (response.isSuccessful()){
                        MovieResponse movieResponse = response.body();
                        listFoundReleaseTodayMovies.addAll(movieResponse.getMovies());
                    }
                    listReleaseTodayMovies.postValue(listFoundReleaseTodayMovies);
                } catch (Exception error){
                    Log.d("Exception", error.getMessage());
                }

            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setUpcomingMovie(int page){
        RetrofitClient client = new RetrofitClient();
        client.getService().getUpcomingMovies(API_KEY, page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                try{
                    if (response.isSuccessful()){
                        MovieResponse movieResponse = response.body();
                        listFoundUpcomingMovies.addAll(movieResponse.getMovies());
                    }
                    listUpcomingMovies.postValue(listFoundUpcomingMovies);
                } catch (Exception error){
                    Log.d("Exception", error.getMessage());
                }

            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovie() {
        return listMovies;
    }
    public LiveData<ArrayList<Movie>> getInTheatersMovie() {
        return listInTheatersMovies;
    }
    public LiveData<ArrayList<Movie>> getReleaseTodayMovie() {
        return listReleaseTodayMovies;
    }
    public LiveData<ArrayList<Movie>> getUpcomingMovie() {
        return listUpcomingMovies;
    }
}
