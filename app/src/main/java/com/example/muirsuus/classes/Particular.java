package com.example.muirsuus.classes;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.muirsuus.R;
import com.example.muirsuus.ui.fragments.DescriptionFragment;


public class Particular extends AppCompatActivity {


    //Глобальная переменная , в которую приходит значение Id(uuid)
    public static int UUID_INT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inform_layout);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_managed_by_buttons);

        if(fragment == null){
            fragment = new DescriptionFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_managed_by_buttons,fragment)
                    .commit();
        }
    }

}