package com.example.muirsuus.registration.sign_up;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.registration.RegistrationDB;

@SuppressLint("StaticFieldLeak")
public class SignUpViewModel extends ViewModel {
    private final String LOG_TAG = "mLog";
    public MutableLiveData<String> _name = new MutableLiveData<>();
    private final LiveData<String> name = _name;
    public MutableLiveData<Boolean> _accepted = new MutableLiveData<>();
    public LiveData<Boolean> accepted = _accepted;
    public MutableLiveData<String> _password = new MutableLiveData<>();
    private final LiveData<String> password = _password;
    private Context context;

    public SignUpViewModel() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MutableLiveData<String> get_name() {
        return _name;
    }

    public void set_name(MutableLiveData<String> _name) {
        this._name = _name;
    }

    public MutableLiveData<String> get_password() {
        return _password;
    }

    public void set_password(MutableLiveData<String> _password) {
        this._password = _password;
    }

    // метод отвечающий за вход Пользователя в систему
    public void enter() {
        RegistrationDB registrationDB = RegistrationDB.getInstance(context);

        new AsyncTask<Void, Boolean, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //проверяем есть ли такой пользователь в Базе
                if (!registrationDB.registrationDAO().getUsersData(name.getValue()).isEmpty()) {
                    Log.d(LOG_TAG, "SignUpViewModel: User " + registrationDB.registrationDAO().getUsersData(name.getValue()).get(0).getName() + " exists");
                    //если такой есть, тогда сравниваем пароли
                    if (registrationDB.registrationDAO().getUsetPasword(name.getValue()).equals(password.getValue())) {
                        publishProgress(true);
                        Log.d(LOG_TAG, "SignUpViewModel: input password is correct");
                    } else {
                        //пароль неверный, сбросить введенное значение и вывести toast - пароль неверный
                        publishProgress(false);
                        Log.d(LOG_TAG, "SignUpViewModel: input password isn't correct");
                    }
                } else {
                    publishProgress(false);
                    Log.d(LOG_TAG, "SignUpViewModel: no such User in RegistrationDB");
                }
                return null;
            }


            @Override
            protected void onProgressUpdate(Boolean... values) {
                super.onProgressUpdate(values);
                _accepted.setValue(values[0]);
            }
        }.execute();
    }
}
