package com.ariefzuhri.moveecatalog.tvshow.viewmodel;

import android.app.Activity;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ariefzuhri.moveecatalog.RetrofitClient;
import com.ariefzuhri.moveecatalog.tvshow.model.DetailTVShow;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.API_KEY;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.showErrorDialog;

public class DetailTVShowViewModel extends ViewModel {
    private MutableLiveData<ArrayList<DetailTVShow>> listDetailTVShows = new MutableLiveData<>();

    public void setDetailTVShow(final Activity activity, String TV_ID){
        RetrofitClient client = new RetrofitClient();
        final ArrayList<DetailTVShow> listFound = new ArrayList<>();
        client.getService().getDetailTVShow(TV_ID, API_KEY).enqueue(new Callback<DetailTVShow>() {
            @Override
            public void onResponse(@NonNull Call<DetailTVShow> call, @NonNull Response<DetailTVShow> response) {
                try{
                    if (response.isSuccessful()){
                        DetailTVShow detailTVShow = response.body();
                        listFound.add(detailTVShow);
                    }
                    listDetailTVShows.postValue(listFound);
                } catch (Exception error){
                    Log.d("Exception", error.getMessage());
                }

            }

            @Override
            public void onFailure(@NonNull Call<DetailTVShow> call, @NonNull Throwable error) {
                Log.d("onFailure", error.getMessage());
                showErrorDialog(activity);
            }
        });
    }

    public LiveData<ArrayList<DetailTVShow>> getDetailTVShow() {
        return listDetailTVShows;
    }
}
