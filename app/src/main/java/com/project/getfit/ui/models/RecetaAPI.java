package com.project.getfit.ui.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RecetaAPI {
    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App",
            "app_id: b054b49b",
            "app_key: cc758b2be822d3e8f2eea92b195e957e"
    })

    @GET("search")

    public Call<List<Receta>> getRecipe(@Query("q") String q);

}

