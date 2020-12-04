package com.example.muirsuus.information_ui.information;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.database.AppDatabase;
import com.example.muirsuus.database.PointAndInformation;

public class InformationViewModel extends ViewModel {

    private final LiveData<PointAndInformation> information;
    private String point;

    public InformationViewModel(Context context, String point) {

        AppDatabase appDatabase = AppDatabase.getInstance(context);
        information = appDatabase.informationDAO().getPointAndInformation(point);
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public LiveData<PointAndInformation> getInformation() {
        return information;
    }
}
