package com.ariefzuhri.moveecatalog;

import com.ariefzuhri.moveecatalog.interfaces.TMDbApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static TMDbApi apiService;

    public RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder()
                //.client(getInterceptor())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(TMDbApi.class);
    }

    public TMDbApi getService(){
        return apiService;
    }
}
