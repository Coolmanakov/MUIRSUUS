package com.example.muirsuus.registration.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.muirsuus.MainActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private SignUpViewModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        binding.setLifecycleOwner(this);
        user = new SignUpViewModel();
        user.setContext(this);
        binding.setUser(user);

        user.accepted.observe(binding.getLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean accepted) {
                if (accepted) {
                    Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                    i.putExtra("name", user._name.getValue());
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Неверно ввведен пароль или имя", Toast.LENGTH_SHORT).show();
                    user._password.setValue("");
                }
            }
        });
    }


}