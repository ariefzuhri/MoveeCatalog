package com.ariefzuhri.moveecatalog.response.viewmodel;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ariefzuhri.moveecatalog.RetrofitClient;
import com.ariefzuhri.moveecatalog.response.TrailerResponse;
import com.ariefzuhri.moveecatalog.response.model.Trailer;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.API_KEY;

public class TrailerViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Trailer>> listTrailers = new MutableLiveData<>();

    public void setTrailer(String MOVIE_ID){
        RetrofitClient client = new RetrofitClient();
        final ArrayList<Trailer> listFound = new ArrayList<>();
        client.getService().getMovieTrailers(MOVIE_ID, API_KEY, "en-US").enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                try{
                    if (response.isSuccessful()){
                        TrailerResponse trailerResponse = response.body();
                        listFound.addAll(trailerResponse.getTrailers());
                    }
                    listTrailers.postValue(listFound);
                } catch (Exception error){
                    Log.d("Exception", error.getMessage());
                }

            }

            @Override
            public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Trailer>> getTrailer() {
        return listTrailers;
    }
}
