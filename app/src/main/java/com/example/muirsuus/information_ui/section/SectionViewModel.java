package com.example.muirsuus.information_ui.section;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.database.AppDatabase;

import java.io.IOException;
import java.util.List;

/*
 * ViewModel for SectionFragment,
 * which responsible for setting needed list of data to RecyclerView
 * */
public class SectionViewModel extends ViewModel {
    private static final String LOG_TAG = "mLog";
    private LiveData<List<String>> sections;


    public SectionViewModel(@NonNull Context context) throws IOException {

        AppDatabase appDatabase = AppDatabase.getInstance(context);
        Log.d(LOG_TAG, "Actively retrieving the tasks from the DataBase");

        sections = appDatabase.informationDAO().getSections();
        Log.d(LOG_TAG, "" + sections.getValue());
    }

    public LiveData<List<String>> getSections() {
        return sections;
    }


}
