package com.example.muirsuus.information_ui.section;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.database.AppDatabase;
import com.example.muirsuus.database.section;

import java.util.List;

/*
 * ViewModel for SectionFragment,
 * which responsible for setting needed list of data to RecyclerView
 * */
public class SectionViewModel extends ViewModel {
    private static final String LOG_TAG = "mLog";
    private final LiveData<List<section>> sections;


    public SectionViewModel(@NonNull Context context) {

        AppDatabase appDatabase = AppDatabase.getInstance(context);
        Log.d(LOG_TAG, "SectionViewModel: getSections from DB");
        sections = appDatabase.informationDAO().getSections();
    }


    public LiveData<List<section>> getSections() {
        return sections;
    }
}
