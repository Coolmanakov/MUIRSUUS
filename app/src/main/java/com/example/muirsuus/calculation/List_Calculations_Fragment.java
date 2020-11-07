package com.example.muirsuus.calculation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.muirsuus.R;


public class List_Calculations_Fragment extends Fragment {
    Button razvedka;
    Button vremya_Vypolneniya;
    Button vremya_Razvertyvaniya;
    Button zhivuchest;
    Button kompas;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list_calculations, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        razvedka= view.findViewById(R.id.reconnaissance);
        vremya_Vypolneniya = view.findViewById(R.id.vremya_Vypolneniya);
        vremya_Razvertyvaniya = view.findViewById(R.id.vremya_Razvertyvaniya);
        zhivuchest = view.findViewById(R.id.zhivuchest);
        kompas = view.findViewById(R.id.kompas);
        Button subscriberNetwork = view.findViewById(R.id.subscriber_network);

        navController = Navigation.findNavController(view);

        razvedka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_choose_calc_to_reconnaissance);
            }
        });

        vremya_Vypolneniya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(R.id.action_choose_calc_to_vremya_Vypolneniya);
            }
        });

        vremya_Razvertyvaniya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_choose_calc_to_vremya_Razvertyvaniya);
            }
        });
        zhivuchest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_choose_calc_to_zhivuchest);
            }
        });


        kompas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_choose_calc_to_kompas);
            }
        });

        subscriberNetwork.setOnClickListener(v ->
                navController.navigate(R.id.choose_to_subscriber_network)
        );
    }
}