package com.example.muirsuus.sostav_uzla;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.muirsuus.CalculationActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.adapters.EquipmentAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Sostav_tabs extends Fragment {
    private static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private Button next;
    private NavController navController;


    private static final String[] names = new String[]{"",
                                                 "Центр каналообразования",
                                                 "Приёмный радиоцентр",
                                                 "Передфющий радиоцентр",
                                                 "Телефонный центр",
                                                 "Телеграфный центр",
                                                 "Центр АСУ",
                                                 "Группа мобильных средств прямой связи",
                                                 "ГТО",
                                                 "Центр электропитающей системы",
                                                 "Пункт управления узлом связи",
                                                 "",
                                                 };




    public static Sostav_tabs newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Sostav_tabs fragment = new Sostav_tabs();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sostav_tab, container, false);
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        textView.setText(names[mPage]);




        List<String> equip1 = new ArrayList<String>(Arrays.asList("Р-441УВ",
                "Р-419П1",
                "Р-423-АМ",
                "П-266-К",
                "Р-416ГМ",
                "Р-431-АМ",
                "П-260-Т")) ;


        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.equipment_rececler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        EquipmentAdapter equipmentAdapter = new EquipmentAdapter();
        equipmentAdapter.setItems(equip1);
        recyclerView.setAdapter(equipmentAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        next = (Button)view.findViewById(R.id.from_sostav);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.recogn);
            }
        });
    }
}