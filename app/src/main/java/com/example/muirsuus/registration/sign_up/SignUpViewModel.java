package com.example.muirsuus.registration.sign_up;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.SplashActivity;
import com.example.muirsuus.restApi.ChainRequest;

@SuppressLint("StaticFieldLeak")
public class SignUpViewModel extends ViewModel {

    private final Context context;
    private final LifecycleOwner lifecycleOwner;
    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    // эти поля использовались при работе с локальной бд
    /*private final String LOG_TAG = "mLog";
    public MutableLiveData<String> _name = new MutableLiveData<>();
    private final LiveData<String> name = _name;
    public MutableLiveData<Boolean> _accepted = new MutableLiveData<>(null);
    public LiveData<Boolean> accepted = _accepted;
    public MutableLiveData<String> _password = new MutableLiveData<>();
    private final LiveData<String> password = _password;
    private Context context;*/
    public MutableLiveData<String> _name = new MutableLiveData<>();
    public MutableLiveData<String> _password = new MutableLiveData<>();
    public MutableLiveData<Boolean> visiblity = new MutableLiveData<>(false);
    public MutableLiveData<String> _responseName = new MutableLiveData<>(); //имя, полученное из запроса
    private final LiveData<String> responseName = _responseName;
    private LiveData<String> name = _name;
    private LiveData<String> password = _password;
    private LiveData<String> errorMessage = _errorMessage;

    public SignUpViewModel(Context context, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
    }

    public MutableLiveData<Boolean> getVisiblity() {
        return visiblity;
    }

    public void setVisiblity(MutableLiveData<Boolean> visiblity) {
        this.visiblity = visiblity;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(LiveData<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(LiveData<String> name) {
        this.name = name;
    }

    public String getPassword() {
        return password.getValue();
    }

    public void setPassword(LiveData<String> password) {
        this.password = password;
    }

    public LiveData<String> getResponseName() {
        return responseName;
    }

    public void sentDataToServer() {
        //начинаем цепочку запросов в облачное хранилище
        //                                              и на локальный сервер
        ChainRequest request = new ChainRequest(context, name.getValue(), password.getValue());
        request.nextRequest();

        visiblity.setValue(true);
        //получая ответ от ChainRequest, обрабатываем его и выводим пользователю
        request.responseName.observe(lifecycleOwner, new Observer<String>() {
            @Override
            public void onChanged(String userName) {
                visiblity.setValue(false);
                if (userName != null) {
                    switch (userName) {
                        case ("Error Response Code"):
                            _errorMessage.setValue("Пароль неверный");
                            break;
                        case ("No Internet Connection"):
                            break;
                        default:
                            Intent intent = new Intent(context, SplashActivity.class);
                            intent.putExtra("name", userName);
                            context.startActivity(intent);
                    }
                }
            }
        });

    }

    /*public void setContext(Context context) {
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
    }*/


    //переход на клиент серверное приложение,
    // поэтому вход, через локальную базу данных не используем
    // метод отвечающий за вход Пользователя в систему
  /*  public void enter() {
        RegistrationDB registrationDB = RegistrationDB.getInstance(context);

        new AsyncTask<Void, Boolean, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                //проверяем есть ли такой пользователь в Базе
                if (registrationDB.registrationDAO().getUsersData(name.getValue()) != null) {
                    Log.d(LOG_TAG, "SignUpViewModel: Пользователья " + registrationDB.registrationDAO().getUsersData(name.getValue()).getName() + " существует");

                    //если такой  пользователь есть, тогда сравниваем пароли
                    if (registrationDB.registrationDAO().getUsetPasword(name.getValue()).equals(password.getValue())) {
                        publishProgress(true);
                        Log.d(LOG_TAG, "SignUpViewModel: введённый пароль совпадает с паролем в Базе");
                    }
                    else {
                        //пароль неверный, сбросить введенное значение и вывести toast - пароль неверный
                        publishProgress(false);
                        Log.d(LOG_TAG, "SignUpViewModel: введённый пароль не совпадает с паролем в Базе");
                    }
                }
                else {
                    publishProgress(false);
                    Log.d(LOG_TAG, "SignUpViewModel: такого пользователя нет в Базе");
                }
                return null;
            }


            @Override
            protected void onProgressUpdate(Boolean... values) {
                _accepted.setValue(values[0]);
            }
        }.execute();
    }*/
}
