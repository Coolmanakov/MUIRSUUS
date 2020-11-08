package com.example.muirsuus.information_ui.subsection;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.database.AppDatabase;

import java.io.IOException;
import java.util.List;

/*
 * ViewModel for SubsectionFragment,
 * which responsible for setting needed list of data to RecyclerView
 * */
public class SubsectionViewModel extends ViewModel {
    private LiveData<List<String>> subsections;

    public SubsectionViewModel(Context context, String section) throws IOException {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        subsections = appDatabase.informationDAO().getSubsections(section);
    }

    public LiveData<List<String>> getSubsections() {
        return subsections;
    }
}
