package com.example.muirsuus.information_ui.presubsection;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.information_database.InformationDatabase;
import com.example.muirsuus.information_database.SectionAndPresubsection;

public class PresubsectionViewModel extends ViewModel {
    private final LiveData<SectionAndPresubsection> presubsection;

    public PresubsectionViewModel(Context context, String section) {
        InformationDatabase informationDatabase = InformationDatabase.getInstance(context);
        //subsections = informationDatabase.informationDAO().getSubsections(section);
        presubsection = informationDatabase.informationDAO().getSectionAndPresubsection(section);
    }

    public LiveData<SectionAndPresubsection> getPresubsection() {
        return presubsection;
    }
}