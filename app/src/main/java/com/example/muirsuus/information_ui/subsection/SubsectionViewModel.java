package com.example.muirsuus.information_ui.subsection;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.database.AppDatabase;
import com.example.muirsuus.database.SectionAndSubsection;

import java.util.List;

/*
 * ViewModel for SubsectionFragment,
 * which responsible for setting needed list of data to RecyclerView
 * */
public class SubsectionViewModel extends ViewModel {
    private final LiveData<List<SectionAndSubsection>> subsections;

    public SubsectionViewModel(Context context, String section) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        //subsections = appDatabase.informationDAO().getSubsections(section);
        subsections = appDatabase.informationDAO().getSectionAndSubsection(section);
    }

    public LiveData<List<SectionAndSubsection>> getSubsections() {
        return subsections;
    }
}
