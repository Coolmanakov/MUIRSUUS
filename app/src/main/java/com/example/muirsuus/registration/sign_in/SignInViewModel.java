package com.example.muirsuus.registration.sign_in;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.registration.RegistrationDB;
import com.example.muirsuus.registration.privateInfo;
import com.example.muirsuus.registration.sign_up.SignUpActivity;

@SuppressLint("StaticFieldLeak")
public class SignInViewModel extends ViewModel {
    private final String LOG_TAG = "mLog";
    public MutableLiveData<String> _name = new MutableLiveData<>();
    private final LiveData<String> name = _name;
    public MutableLiveData<String> _password = new MutableLiveData<>();
    private final LiveData<String> password = _password;
    public MutableLiveData<String> _password2 = new MutableLiveData<>();
    private final LiveData<String> password2 = _password2;
    public MutableLiveData<Boolean> _isSigned = new MutableLiveData<>(null);
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
        RegistrationDB registrationDB = RegistrationDB.getInstance(context);
        boolean valid = isNameValidate() && isPasswordValidate();

        new AsyncTask<Void, Boolean, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                //Проверяем введенные значения
                if(valid) {
                    //получаем из базы список Пользователей с таким именем
                    if (registrationDB.registrationDAO().getUsersData(name.getValue()) == null) {
                        newUser = new privateInfo(name.getValue(), password.getValue());

                        //если таких нет, то добавляем в БД
                        registrationDB.registrationDAO().insert(newUser);
                        Log.d(LOG_TAG, "SignInViewModel Пользователь " + name.getValue() + " успешно зарегистрирован");
                        publishProgress(true);
                    } else {
                        // если такие все же есть, тогда выводим в сообщение о том, что невозможно создать этого пользователя
                        Log.d(LOG_TAG, "SignInViewModel Пользователь " + name.getValue() + " уже зарегистрирован");
                        publishProgress(false);
                    }
                }
                else {publishProgress(false);}
                return null;
            }

            //метод, в котором мы получаем значение в течение выполнение потока
            //мы не можем в потоке выводить Toast, поэтому делаем это по мере выполнения потока
            @Override
            protected void onProgressUpdate(Boolean... bool) {

                _isSigned.setValue(bool[0]);
            }

        }.execute();

    }
    public void navigateToSignUp(){
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }
    @SuppressLint("ShowToast")
    public boolean isPasswordValidate(){
        boolean valid = false;

        //проверка ввёл ли пользователь пароли
        if (password.getValue() != null && password2.getValue() != null){
            String parol1 = password.getValue();
            String parol2 = password2.getValue();

            //проверка на идентичность паролей
            if(parol1.equals(parol2)){
                if (parol1.length() >= 5 && parol1.length() <= 10) {
                    valid = true;
                }
                else {
                    Log.d(LOG_TAG, "SignInViewModel Passwords aren't valid: length isn't correct");
                    Toast.makeText(context,"Пароль должен состоять из более 5 символов", Toast.LENGTH_LONG);
                }
            }
            else {
                Log.d(LOG_TAG, "SignInViewModel Passwords aren't valid: passwords aren't identify");
                Toast.makeText(context,"Пароли не совпадают", Toast.LENGTH_LONG);
            }
        }
        else {
            Log.d(LOG_TAG, "SignInViewModel: Passwords aren't valid: password = " + password.getValue()
                                                    + " password2 = " + password2.getValue());
            Toast.makeText(context,"Введите оба пароля", Toast.LENGTH_LONG);
        }
        return  valid;
    }
    @SuppressLint("ShowToast")
    private boolean isNameValidate(){
        boolean valid = false;
        if(name.getValue() != null){
            if(name.getValue().length() >= 5 && name.getValue().length() <= 15){
                valid = true;
                Log.d(LOG_TAG, "SignInViewModel: Name " + name.getValue() + " is valid");
            }
        }
        else {
            Log.d(LOG_TAG, "SignInViewModel: Name isn't valid: name is null");
            Toast.makeText(context,"Введите имя", Toast.LENGTH_LONG);
        }
        return valid;
    }

}
