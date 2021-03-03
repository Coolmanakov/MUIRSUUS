package com.example.muirsuus.registration.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.muirsuus.R;
import com.example.muirsuus.SplashActivity;
import com.example.muirsuus.databinding.MaterialSignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private MaterialSignUpBinding binding;
    private SignUpViewModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.material_sign_up);
        binding.setLifecycleOwner(this);
        user = new SignUpViewModel();
        user.setContext(this);
        binding.setUser(user);

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
        });
    }


}