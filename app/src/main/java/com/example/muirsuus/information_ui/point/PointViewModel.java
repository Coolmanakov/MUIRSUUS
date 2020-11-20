package com.example.muirsuus.information_ui.point;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.database.AppDatabase;
import com.example.muirsuus.database.SubsectionAndPoint;

import java.util.List;

/*
 * ViewModel for PointsFragment,
 * which responsible for setting needed list of data to RecyclerView
 * */
public class PointViewModel extends ViewModel {
    private final LiveData<List<SubsectionAndPoint>> points;

    public PointViewModel(Context context, String subsection) {

        AppDatabase appDatabase = AppDatabase.getInstance(context);
        points = appDatabase.informationDAO().getSubsectionAndPoint(subsection);
    }

    public LiveData<List<SubsectionAndPoint>> getPoints() {
        return points;
    }
}
