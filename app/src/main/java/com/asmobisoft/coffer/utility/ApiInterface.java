package com.asmobisoft.coffer.utility;

import com.asmobisoft.coffer.model.AllProviders;
import com.asmobisoft.coffer.model.ProvidersData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by root on 10/3/16.
 */
public interface ApiInterface {

    @GET("get-provider")
    Call<AllProviders> getProvider(@Query("api_token") String api_token);

    /*@GET("movie/{id}")
    Call<ProvidersData> getProviders(@Path("id") int id, @Query("api_key") String apiKey);*/


}
