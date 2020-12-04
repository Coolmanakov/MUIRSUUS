package com.example.muirsuus.registration.sign_in;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.registration.RegistrationDB;
import com.example.muirsuus.registration.privateInfo;

import java.util.Objects;

@SuppressLint("StaticFieldLeak")
public class SignInViewModel extends ViewModel {
    private final String LOG_TAG = "mLog";
    public MutableLiveData<String> _name = new MutableLiveData<>();
    private final LiveData<String> name = _name;
    public MutableLiveData<String> _password = new MutableLiveData<>();
    private final LiveData<String> password = _password;
    public MutableLiveData<String> _password2 = new MutableLiveData<>();
    private final LiveData<String> password2 = _password2;
    public MutableLiveData<Boolean> _isSigned = new MutableLiveData<>(false);
    public LiveData<Boolean> isSigned = _isSigned;
    public Context context;
    private privateInfo newUser;

    public SignInViewModel() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MutableLiveData<String> get_name() {
        return _name;
    }

    // метод, отвечающий за регистрацию пользователей
    public void signIN() {
        //проверяем совпадают ли пароли, если нет, то выводим сообщение об этом
        if (!Objects.equals(password.getValue(), password2.getValue())) {
            Toast.makeText(context, "Введённые пароли не совпадают", Toast.LENGTH_LONG).show();
        } else {
            // в другом случае создаём пользователя с этим паролем

            newUser = new privateInfo(_name.getValue(), _password.getValue());
            RegistrationDB registrationDB = RegistrationDB.getInstance(context);

            new AsyncTask<Void, Boolean, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {

                    //получаем из базы список Пользователей с таким именем
                    if (registrationDB.registrationDAO().getUsersData(name.getValue()).isEmpty() && _name.getValue() != null) {
                        //если таких нет, то добавляем в БД
                        registrationDB.registrationDAO().insert(newUser);
                        Log.d(LOG_TAG, "Пользователь " + _name.getValue() + " успешно зарегистрирован");
                        publishProgress(true);
                    } else {
                        // если такие все же есть, тогда выводим в сообщение о том, что не возможно создать этого пользователя
                        Log.d(LOG_TAG, "Пользователь " + _name.getValue() + " уже зарегистрирован");
                        publishProgress(false);
                    }
                    return null;
                }

                //метод, в котором мы получаем значение в течение выполнение потока
                //мы не можем в потоке выводить Toast, поэтому делаем это по мере выполнения потока
                @Override
                protected void onProgressUpdate(Boolean... bool) {
                    super.onProgressUpdate(bool);
                    if (bool[0]) {
                        Toast.makeText(context, "Пользователь успешно добавлен", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Пользователь уже зарегистрирован", Toast.LENGTH_LONG).show();
                        _password.setValue("");
                        _password2.setValue("");
                    }
                    _isSigned.setValue(bool[0]);
                }

            }.execute();
        }
    }

}
