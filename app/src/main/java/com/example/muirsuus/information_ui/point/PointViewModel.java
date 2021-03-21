package com.example.muirsuus.information_ui.point;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.information_database.AppDatabase;
import com.example.muirsuus.information_database.PreSubsectionAndPoint;
import com.example.muirsuus.information_database.SubsectionAndPoint;

/*
 * ViewModel for PointsFragment,
 * which responsible for setting needed list of data to RecyclerView
 * */
public class PointViewModel extends ViewModel {
    private final LiveData<SubsectionAndPoint> points;
    private final LiveData<PreSubsectionAndPoint> points_pre;

    public PointViewModel(Context context, String subsection) {

        AppDatabase appDatabase = AppDatabase.getInstance(context);
        points = appDatabase.informationDAO().getSubsectionAndPoint(subsection);
        points_pre = appDatabase.informationDAO().getPreSubsectionAndPoint(subsection);
    }

    public LiveData<SubsectionAndPoint> getPoints() {
        return points;
    }

    public LiveData<PreSubsectionAndPoint> getPoints_pre() {
        return points_pre;
    }
}
