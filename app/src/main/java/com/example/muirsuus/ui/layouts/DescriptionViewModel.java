package com.example.muirsuus.ui.layouts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DescriptionViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public DescriptionViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("Description");
    }

    public LiveData<String> getText() {return mText;}
}