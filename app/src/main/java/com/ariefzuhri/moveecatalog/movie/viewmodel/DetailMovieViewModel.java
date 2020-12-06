package com.ariefzuhri.moveecatalog.movie.viewmodel;

import android.app.Activity;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ariefzuhri.moveecatalog.RetrofitClient;
import com.ariefzuhri.moveecatalog.movie.model.DetailMovie;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.API_KEY;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.showErrorDialog;

public class DetailMovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<DetailMovie>> listDetailMovies = new MutableLiveData<>();

    public void setDetailMovie(final Activity activity, String MOVIE_ID){
        RetrofitClient client = new RetrofitClient();
        final ArrayList<DetailMovie> listFound = new ArrayList<>();
        client.getService().getDetailMovie(MOVIE_ID, API_KEY).enqueue(new Callback<DetailMovie>() {
            @Override
            public void onResponse(@NonNull Call<DetailMovie> call, @NonNull Response<DetailMovie> response) {
                try{
                    if (response.isSuccessful()){
                        DetailMovie detailMovie = response.body();
                        listFound.add(detailMovie);
                    }
                    listDetailMovies.postValue(listFound);
                } catch (Exception error){
                    Log.d("Exception", error.getMessage());
                }

            }

            @Override
            public void onFailure(@NonNull Call<DetailMovie> call, @NonNull Throwable error) {
                Log.d("onFailure", error.getMessage());
                showErrorDialog(activity);
            }
        });
    }

    public LiveData<ArrayList<DetailMovie>> getDetailMovie() {
        return listDetailMovies;
    }
}
