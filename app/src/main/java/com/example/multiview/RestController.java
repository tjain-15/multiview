package com.example.multiview;

import android.util.Log;

import com.example.multiview.listeners.ApiInterface;
import com.example.multiview.listeners.DataListener;
import com.example.multiview.models.ApiResponse;
import com.example.multiview.models.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestController {
    private static RestController restController = null;

    private RestController() {
    }

    public static RestController getInstance() {
        if (restController == null) {
            return new RestController();
        }
        return restController;
    }

    public void getJsonData(DataListener dataListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonkeeper.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ApiResponse> call = apiInterface.getData();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.i("jsonData", Thread.currentThread().getName());
                //TODO: check on which thread this callback is received
                dataListener.sendData(rearrangeData(response.body()));

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i("jsonData", t.getMessage());
            }
        });

    }

    private ApiResponse rearrangeData(ApiResponse apiResponse) {
        Result last = apiResponse.getResults().get(0);
        apiResponse.getResults().set(0, apiResponse.getResults().get(1));
        apiResponse.getResults().set(1, apiResponse.getResults().get(2));
        apiResponse.getResults().set(2, last);
        return apiResponse;

    }

}
