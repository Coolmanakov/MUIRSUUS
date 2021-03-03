package com.example.muirsuus.information_ui.information;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.information_database.AppDatabase;
import com.example.muirsuus.information_database.PointAndInformation;

@SuppressLint("StaticFieldLeak")
public class InformationViewModel extends ViewModel {

    private final LiveData<PointAndInformation> information;

    public InformationViewModel(Context context, String point) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        information = appDatabase.informationDAO().getPointAndInformation(point);
    }


    public LiveData<PointAndInformation> getInformation() {
        return information;
    }


}
