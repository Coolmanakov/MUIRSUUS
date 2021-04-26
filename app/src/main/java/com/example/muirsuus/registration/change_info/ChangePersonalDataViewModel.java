package com.example.muirsuus.registration.change_info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.MainActivity;
import com.example.muirsuus.registration.RegistrationDB;
import com.example.muirsuus.registration.privateInfo;

@SuppressLint("StaticFieldLeak")
public class ChangePersonalDataViewModel extends ViewModel {
    private final MutableLiveData<String> name = new MutableLiveData<>();
    private final String LOG_TAG = "mLog";
    public MutableLiveData<Boolean> _userDataChanged = new MutableLiveData<>();
    public LiveData<Boolean> userDataChanged = _userDataChanged;
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

    public ChangePersonalDataViewModel() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setName(String _name) {
        name.setValue(_name);
    }

    public void setUsersInfo() {
        RegistrationDB registrationDB = RegistrationDB.getInstance(context);

        new AsyncTask<Void, Boolean, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (registrationDB.registrationDAO().getUsersData(name.getValue()) != null) {
                    user = registrationDB.registrationDAO().getUsersData(name.getValue());
                    Log.d(LOG_TAG, "ChangePersonalDataViewModel: set privateInfo of User = " + user.getName());
                    publishProgress(true);
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Boolean... values) {
                //_userDataChanged.setValue(values[0]);

                _appointment.setValue(user.getAppointment());
                _nickname.setValue(user.getNickname());
                _surname.setValue(user.getSurname());
                _patronymic.setValue(user.getPatronymic());
                _rank.setValue(user.getRank());
                _workPlace.setValue(user.getWorkPlace());
            }
        }.execute();
    }

    //изменить данные
    public void changePrivateInfo() {
        RegistrationDB registrationDB = RegistrationDB.getInstance(context);

        new AsyncTask<Void, String, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                if (registrationDB.registrationDAO().getUsersData(name.getValue()) != null) {
                    user = registrationDB.registrationDAO().getUsersData(name.getValue());
                    if (inputNotEmpty()) {
                        user.setNickname(nickname.getValue());
                        user.setSurname(surname.getValue());
                        user.setPatronymic(patronymic.getValue());
                        user.setRank(rank.getValue());
                        user.setWorkPlace(workPlace.getValue());
                        user.setAppointment(appointment.getValue());
                        publishProgress(user.name);
                        //registrationDB.registrationDAO().deleteUser(user);
                        Log.d(LOG_TAG, "ChangePersonalDataViewModel: change privateInfo of User = " + user.getName());
                        registrationDB.registrationDAO().insertNewUser(user);
                        Log.d(LOG_TAG, "ChangePersonalDataViewModel: change UsersData = " + user.getName() + " registrationDB after changing = " + registrationDB.registrationDAO().all());
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                name.setValue(values[0]);
            }

            @Override
            protected void onPostExecute(Void aVoids) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("name", name.getValue());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

            }
        }.execute();
    }


    // проверка, чтобы были заполнены все поля
    private boolean inputNotEmpty() {
        return !nickname.getValue().equals("") && !surname.getValue().equals("") && !patronymic.getValue().equals("") && !rank.getValue().equals("")
                && !appointment.getValue().equals("") && !workPlace.getValue().equals("");
    }
}
