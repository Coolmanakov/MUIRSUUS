package com.example.muirsuus.registration.sign_in;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.muirsuus.R;
import com.example.muirsuus.SplashActivity;
import com.example.muirsuus.databinding.MaterialSignInBinding;

public class SignInActivity extends AppCompatActivity {
    private MaterialSignInBinding binding;
    private SignInViewModel newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.material_sign_in);
        newUser = new SignInViewModel();
        newUser.setContext(this);
        binding.setNewUser(newUser);
        binding.setLifecycleOwner(this);

        newUser.isSigned.observe(binding.getLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean signed) {
                //если пользователь только что зарегистрировался, то он продолжает работу
                //если пользователь уже есть в базе данных, значит он не продолжит работу с этого активити
                if(signed != null) {
                    if (signed) {
                        binding.nameTextLayout.setError(null);
                        binding.passwordTextLayout.setError(null);
                        binding.passwordTextLayout2.setError(null);

                        //если пользователь успешно зарегистрирован, то переходим на SplashActivity, передавая имя пользователя
                        Intent intent = new Intent(SignInActivity.this, SplashActivity.class);
                        intent.putExtra("name", newUser.get_name().getValue());
                        startActivity(intent);
                        finish();
                    } else {
                        binding.nameTextLayout.setError("Минимальная длина имени 5");
                        binding.passwordTextLayout.setError("Минимальная длина парля 5");
                        binding.passwordTextLayout2.setError("Минимальная длина парля 5");
                    }
                }
            }
        });




    }
}