package com.example.muirsuus.information_ui.presubsection;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.information_database.AppDatabase;
import com.example.muirsuus.information_database.SectionAndPresubsection;

public class PresubsectionViewModel extends ViewModel {
    private final LiveData<SectionAndPresubsection> presubsection;

    public PresubsectionViewModel(Context context, String section) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        //subsections = appDatabase.informationDAO().getSubsections(section);
        presubsection = appDatabase.informationDAO().getSectionAndPresubsection(section);
    }

    public LiveData<SectionAndPresubsection> getPresubsection() {
        return presubsection;
    }
}