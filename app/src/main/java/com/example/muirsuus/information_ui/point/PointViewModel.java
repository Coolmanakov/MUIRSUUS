package com.example.muirsuus.information_ui.point;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.information_database.InformationDatabase;
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

        InformationDatabase informationDatabase = InformationDatabase.getInstance(context);
        points = informationDatabase.informationDAO().getSubsectionAndPoint(subsection);
        points_pre = informationDatabase.informationDAO().getPreSubsectionAndPoint(subsection);
    }

    public LiveData<SubsectionAndPoint> getPoints() {
        return points;
    }

    public LiveData<PreSubsectionAndPoint> getPoints_pre() {
        return points_pre;
    }
}
