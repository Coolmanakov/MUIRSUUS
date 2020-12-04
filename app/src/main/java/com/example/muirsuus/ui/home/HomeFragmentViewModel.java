package com.example.muirsuus.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.registration.RegistrationDB;
import com.example.muirsuus.registration.privateInfo;

@SuppressLint("StaticFieldLeak")
public class HomeFragmentViewModel extends ViewModel {
    private final String LOG_TAG = "mLog";
    public MutableLiveData<String> _nickname = new MutableLiveData<>();
    private final LiveData<String> nickname = _nickname;
    public MutableLiveData<String> _surname = new MutableLiveData<>();
    private final LiveData<String> surname = _surname;
    public MutableLiveData<String> _patronymic = new MutableLiveData<>();
    private final LiveData<String> patronymic = _patronymic;
    public MutableLiveData<String> _rank = new MutableLiveData<>();
    private final LiveData<String> rank = _rank;
    public MutableLiveData<String> _appointment = new MutableLiveData<>();
    private final LiveData<String> appointment = _appointment;
    public MutableLiveData<String> _workPlace = new MutableLiveData<>();
    private final LiveData<String> workPlace = _workPlace;
    private Context context;
    private privateInfo user;
    private String name;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsersInfo() {
        RegistrationDB registrationDB = RegistrationDB.getInstance(context);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (!registrationDB.registrationDAO().getUsersData(name).isEmpty()) {
                    user = registrationDB.registrationDAO().getUsersData(name).get(0);
                    Log.d(LOG_TAG, "HomeFragmentViewModel: set privateInfo of User = " + user.getName());
                } else {
                    Log.d(LOG_TAG, "HomeFragmentViewModel: no such user ");
                }

                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                _appointment.setValue(user.getAppointment());
                _nickname.setValue(user.getNickname());
                _surname.setValue(user.getSurname());
                _patronymic.setValue(user.getPatronymic());
                _rank.setValue(user.getRank());
                _workPlace.setValue(user.getWorkPlace());
            }
        }.execute();


    }

    public void changePrivateInfo() {

    }


}
