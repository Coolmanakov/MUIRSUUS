package com.example.muirsuus.information_ui.section;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.information_database.InformationDatabase;
import com.example.muirsuus.information_database.section;
import com.example.muirsuus.restApi.ClientServerWorker;
import com.example.muirsuus.restApi.JsonPlaceHolderApi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * ViewModel for SectionFragment,
 * which responsible for setting needed list of data to RecyclerView
 * */
public class SectionViewModel extends ViewModel {
    private static final String LOG_TAG = "mLog";
    private LiveData<List<section>> sections;


    public SectionViewModel(@NonNull Context context) {

        InformationDatabase informationDatabase = InformationDatabase.getInstance(context);
        Log.d(LOG_TAG, "SectionViewModel: getSections from DB");
        sections = informationDatabase.informationDAO().getSections();
    }

    public SectionViewModel(String url) {
        JsonPlaceHolderApi jsonPlaceHolderApi = new ClientServerWorker(url).getInstance();
        Call<ResponseBody> call = jsonPlaceHolderApi.downloadImage();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    return;
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    public LiveData<List<section>> getSections() {
        return sections;
    }
}
