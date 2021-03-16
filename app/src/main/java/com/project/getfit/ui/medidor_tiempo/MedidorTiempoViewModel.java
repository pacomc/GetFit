package com.project.getfit.ui.medidor_tiempo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MedidorTiempoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MedidorTiempoViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is medidor tiempo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}