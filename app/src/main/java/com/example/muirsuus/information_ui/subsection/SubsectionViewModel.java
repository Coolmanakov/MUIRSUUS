package com.example.muirsuus.information_ui.subsection;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.information_database.InformationDatabase;
import com.example.muirsuus.information_database.PresubectionAndSubsection;
import com.example.muirsuus.information_database.SectionAndSubsection;

/*
 * ViewModel for SubsectionFragment,
 * which responsible for setting needed list of data to RecyclerView
 * */
public class SubsectionViewModel extends ViewModel {
    private final LiveData<SectionAndSubsection> subsections;
    private final LiveData<PresubectionAndSubsection> subsections_pre;

    public SubsectionViewModel(Context context, String section) {
        InformationDatabase informationDatabase = InformationDatabase.getInstance(context);
        subsections_pre = informationDatabase.informationDAO().getPreSubsectionAndSubsection(section);
        subsections = informationDatabase.informationDAO().getSectionAndSubsection(section);
    }

    public LiveData<SectionAndSubsection> getSubsections() {
        return subsections;
    }

    public LiveData<PresubectionAndSubsection> getSubsections_pre() {
        return subsections_pre;
    }
}
