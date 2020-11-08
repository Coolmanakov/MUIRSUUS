package com.example.muirsuus.calculation;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.muirsuus.R;
import com.example.muirsuus.sostav_uzla.Sostav_uzla_svyazi;


public class Vremya_Vypolneniya extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_vremya__vypolneniya, null);

        Button bez = (Button)v.findViewById(R.id.bez);
        Button s = (Button)v.findViewById(R.id.s);
        Button kolvo = (Button)v.findViewById(R.id.kolvo);

        bez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(v).navigate(R.id.vremyaVypolneniya_step2_1);
            }
        });

        return v;
    }
}