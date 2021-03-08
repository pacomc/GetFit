package com.project.getfit.ui.rutina;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RutinaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RutinaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is rutina fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}