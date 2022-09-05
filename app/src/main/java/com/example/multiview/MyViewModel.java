package com.example.multiview;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.multiview.listeners.DataListener;
import com.example.multiview.models.ApiResponse;

public class MyViewModel extends ViewModel implements DataListener {
    private MutableLiveData<ApiResponse> modelLiveData;

    public MyViewModel() {
        modelLiveData = new MutableLiveData<>();
        initData();
    }

    public LiveData<ApiResponse> getModelLiveData() {
        return modelLiveData;
    }


    private void initData() {
        RestController restController = RestController.getInstance();
        restController.getJsonData(this);
    }

    @Override
    public void sendData(ApiResponse apiResponse) {
        modelLiveData.setValue(apiResponse);
    }
}
