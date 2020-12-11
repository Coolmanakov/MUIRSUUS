package com.example.muirsuus.registration.sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.muirsuus.R;
import com.example.muirsuus.SplashActivity;
import com.example.muirsuus.databinding.ActivitySignInBinding;
import com.example.muirsuus.registration.sign_up.SignUpActivity;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private SignInViewModel newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        newUser = new SignInViewModel();
        newUser.setContext(this);
        binding.setNewUser(newUser);
        binding.setLifecycleOwner(this);

        newUser.isSigned.observe(binding.getLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean signed) {
                //если пользователь только что зарегистрировался, то он продолжает работу
                //если пользователь уже есть в базе данных, значит он не продолжит работу с этого активити
                if (signed) {
                    Intent i = new Intent(SignInActivity.this, SplashActivity.class);
                    i.putExtra("name", newUser._name.getValue());
                    startActivity(i);
                    finish();
                }
            }
        });
        binding.signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //если пользователь уже зарегистрирован в системе, значит переходим на активити, гдеможно войти в систему
                Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}