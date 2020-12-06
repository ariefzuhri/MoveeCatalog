package com.ariefzuhri.moveecatalog.response.viewmodel;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ariefzuhri.moveecatalog.RetrofitClient;
import com.ariefzuhri.moveecatalog.response.CreditResponse;
import com.ariefzuhri.moveecatalog.response.model.CastCredit;
import com.ariefzuhri.moveecatalog.response.model.CrewCredit;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.API_KEY;

public class CreditViewModel extends ViewModel {
    private MutableLiveData<ArrayList<CrewCredit>> listCrews = new MutableLiveData<>();
    private MutableLiveData<ArrayList<CastCredit>> listCasts = new MutableLiveData<>();

    public void setCrewCredit(String MEDIA_TYPE, String ID){
        RetrofitClient client = new RetrofitClient();
        final ArrayList<CrewCredit> listFound = new ArrayList<>();
        client.getService().getCredits(MEDIA_TYPE, ID, API_KEY).enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreditResponse> call, @NonNull Response<CreditResponse> response) {
                try{
                    if (response.isSuccessful()){
                        CreditResponse creditResponse = response.body();
                        listFound.addAll(creditResponse.getCrews());
                    }
                    listCrews.postValue(listFound);
                } catch (Exception error){
                    Log.d("Exception", error.getMessage());
                }

            }

            @Override
            public void onFailure(@NonNull Call<CreditResponse> call, @NonNull Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setCastCredit(String MEDIA_TYPE, String ID){
        RetrofitClient client = new RetrofitClient();
        final ArrayList<CastCredit> listFound = new ArrayList<>();
        client.getService().getCredits(MEDIA_TYPE, ID, API_KEY).enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreditResponse> call, @NonNull Response<CreditResponse> response) {
                try{
                    if (response.isSuccessful()){
                        CreditResponse creditResponse = response.body();
                        listFound.addAll(creditResponse.getCasts());
                    }
                    listCasts.postValue(listFound);
                } catch (Exception error){
                    Log.d("Exception", error.getMessage());
                }

            }

            @Override
            public void onFailure(@NonNull Call<CreditResponse> call, @NonNull Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<CrewCredit>> getCrewCredit() {
        return listCrews;
    }

    public LiveData<ArrayList<CastCredit>> getCastCredit() {
        return listCasts;
    }
}
