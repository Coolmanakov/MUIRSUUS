package com.example.muirsuus.main_navigation.home;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.restApi.ClientServerWorker;
import com.example.muirsuus.restApi.JsonPlaceHolderApi;
import com.example.muirsuus.restApi.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("StaticFieldLeak")
public class HomeFragmentViewModel extends ViewModel {
    private final String LOG_TAG = "mLog";
    private final String url;
    public String token;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

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


    public HomeFragmentViewModel(String url, String token) {
        this.url = url;
        this.token = token;
    }

    public void loadUserInfo() {
        JsonPlaceHolderApi jsonPlaceHolderApi = new ClientServerWorker(url).getInstance();
        Call<User> userInfo = jsonPlaceHolderApi.clientInfo(token);

        userInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                User user = response.body();
                _nickname.setValue(user.getFirst_name());
                _surname.setValue(user.getLast_name());
                _patronymic.setValue(user.getMiddle_name());
                _rank.setValue(user.getRank());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(LOG_TAG, "" + t.getMessage());
            }
        });
    }


    //использовалось  при работе с локальной БД
    /*public MutableLiveData<String> _nickname = new MutableLiveData<>();
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
                if (registrationDB.registrationDAO().getUsersData(name) != null) {
                    user = registrationDB.registrationDAO().getUsersData(name);
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


    }*/


}
