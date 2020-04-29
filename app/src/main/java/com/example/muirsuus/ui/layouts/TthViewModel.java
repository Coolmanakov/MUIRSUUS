package com.example.muirsuus.ui.layouts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TthViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public TthViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("Tth");
    }

    public LiveData<String> getText() {return mText;}
}