package com.example.muirsuus.registration.change_info;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.muirsuus.R;
import com.example.muirsuus.databinding.ActivityChangePersonalDataBinding;

public class ChangePersonalData extends AppCompatActivity {
    private ActivityChangePersonalDataBinding binding;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_personal_data);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        Log.d("mLog", "ChangePersonalData name " + name);
        ChangePersonalDataViewModel user = new ChangePersonalDataViewModel();
        user.setContext(this);
        user.setName(name);
        user.setUsersInfo();
        binding.setUser(user);


    }
}