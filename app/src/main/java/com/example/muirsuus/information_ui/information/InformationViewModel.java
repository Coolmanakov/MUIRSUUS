package com.example.muirsuus.information_ui.information;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.database.AppDatabase;
import com.example.muirsuus.database.PointAndInformation;

import java.util.List;

public class InformationViewModel extends ViewModel {

    private final LiveData<List<PointAndInformation>> information;
    private String point;

    public InformationViewModel(Context context, String point) {

        AppDatabase appDatabase = AppDatabase.getInstance(context);
        information = appDatabase.informationDAO().getPointAndInformation(point);
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public LiveData<List<PointAndInformation>> getInformation() {
        return information;
    }
}
