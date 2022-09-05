package com.example.multiview.listeners;

import com.example.multiview.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("b/GXOC")
    Call<ApiResponse> getData();
}
