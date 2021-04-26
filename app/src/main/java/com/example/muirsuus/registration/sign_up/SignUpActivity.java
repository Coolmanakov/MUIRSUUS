package com.example.muirsuus.registration.sign_up;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.muirsuus.R;
import com.example.muirsuus.databinding.MaterialSignUpBinding;
import com.example.muirsuus.restApi.JsonPlaceHolderApi;

public class SignUpActivity extends AppCompatActivity {
    private MaterialSignUpBinding binding;
    private SignUpViewModel user;
    private final static String BASE_URL = "http://91.210.170.162:80";
    private Boolean wifiConnected, mobileConnected;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.material_sign_up);
        binding.setLifecycleOwner(this);
        user = new SignUpViewModel(this, binding.getLifecycleOwner());
        binding.setUser(user);

        //если сервер не подтвердил пользователя
        // выводим сообщение об ошибке: Неверно введён пароль
        user.getErrorMessage().observe(binding.getLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                binding.passwordTextLayout.setError(errorMessage);
            }
        });

        //выводим сообщение об ошибке
        binding.passwordEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.passwordTextLayout.setError(null);
            }
        });

        //убираем клавиатура при нажатии на экран
        binding.mainConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(SignUpActivity.this, binding.getRoot());
            }
        });

        /*user = new SignUpViewModel();
        user.setContext(this);
        binding.setUser(user);
        useRetrofit();

        user.accepted.observe(binding.getLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean accepted) {
                if (accepted != null) {
                    if (accepted) {
                        binding.passwordTextLayout.setError(null);

                        Intent i = new Intent(SignUpActivity.this, SplashActivity.class);
                        i.putExtra("name", user._name.getValue());
                        startActivity(i);
                    } else {
                        Toast.makeText(SignUpActivity.this, "Неверно ввведен пароль или имя", Toast.LENGTH_SHORT).show();
                        user._password.setValue("");
                        binding.passwordTextLayout.setError("Неверный пароль");
                    }
                }
            }
        });*/
    }

}