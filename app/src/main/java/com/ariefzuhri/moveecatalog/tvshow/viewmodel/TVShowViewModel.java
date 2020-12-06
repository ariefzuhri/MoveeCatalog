package com.ariefzuhri.moveecatalog.tvshow.viewmodel;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ariefzuhri.moveecatalog.RetrofitClient;
import com.ariefzuhri.moveecatalog.tvshow.TVShowResponse;
import com.ariefzuhri.moveecatalog.tvshow.model.TVShow;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.API_KEY;

public class TVShowViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TVShow>> listTVShows = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TVShow>> listOnAirTVShows = new MutableLiveData<>();

    private ArrayList<TVShow> listFoundTVShows = new ArrayList<>();
    private ArrayList<TVShow> listFoundOnAirTVShows = new ArrayList<>();

    public void setTVShow(int page){
        RetrofitClient client = new RetrofitClient();
        client.getService().getTrendingTVShows(API_KEY, page).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowResponse> call, @NonNull Response<TVShowResponse> response) {
                try{
                    if (response.isSuccessful()){
                        TVShowResponse tvShowResponse = response.body();
                        listFoundTVShows.addAll(tvShowResponse.getTVShows());
                    }
                    listTVShows.postValue(listFoundTVShows);
                } catch (Exception error){
                    Log.d("Exception", error.getMessage());
                }

            }

            @Override
            public void onFailure(@NonNull Call<TVShowResponse> call, @NonNull Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setOnAirTVShow(int page){
        RetrofitClient client = new RetrofitClient();
        client.getService().getOnAirTVShows(API_KEY, page).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowResponse> call, @NonNull Response<TVShowResponse> response) {
                try{
                    if (response.isSuccessful()){
                        TVShowResponse tvshowResponse = response.body();
                        listFoundOnAirTVShows.addAll(tvshowResponse.getTVShows());
                    }
                    listOnAirTVShows.postValue(listFoundOnAirTVShows);
                } catch (Exception error){
                    Log.d("Exception", error.getMessage());
                }

            }

            @Override
            public void onFailure(@NonNull Call<TVShowResponse> call, @NonNull Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TVShow>> getTVShow() {
        return listTVShows;
    }
    public LiveData<ArrayList<TVShow>> getOnAirTVShow() {
        return listOnAirTVShows;
    }
}
