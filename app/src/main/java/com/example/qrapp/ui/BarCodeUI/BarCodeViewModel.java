package com.example.qrapp.ui.BarCodeUI;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BarCodeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BarCodeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}