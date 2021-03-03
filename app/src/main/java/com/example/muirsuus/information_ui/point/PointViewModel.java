package com.example.muirsuus.information_ui.point;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.information_database.AppDatabase;
import com.example.muirsuus.information_database.SubsectionAndPoint;

/*
 * ViewModel for PointsFragment,
 * which responsible for setting needed list of data to RecyclerView
 * */
public class PointViewModel extends ViewModel {
    private final LiveData<SubsectionAndPoint> points;

    public PointViewModel(Context context, String subsection) {

        AppDatabase appDatabase = AppDatabase.getInstance(context);
        points = appDatabase.informationDAO().getSubsectionAndPoint(subsection);
    }

    public LiveData<SubsectionAndPoint> getPoints() {
        return points;
    }
}
