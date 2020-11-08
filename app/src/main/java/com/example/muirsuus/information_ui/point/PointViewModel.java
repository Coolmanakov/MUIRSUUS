package com.example.muirsuus.information_ui.point;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.database.AppDatabase;

import java.io.IOException;
import java.util.List;

/*
 * ViewModel for PointsFragment,
 * which responsible for setting needed list of data to RecyclerView
 * */
public class PointViewModel extends ViewModel {
    private LiveData<List<String>> points;

    public PointViewModel(Context context, String subsection) throws IOException {

        AppDatabase appDatabase = AppDatabase.getInstance(context);
        points = appDatabase.informationDAO().getPoints(subsection);
    }

    public LiveData<List<String>> getPoints() {
        return points;
    }
}
