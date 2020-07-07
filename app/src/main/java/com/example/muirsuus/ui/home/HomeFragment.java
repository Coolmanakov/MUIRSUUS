package com.example.muirsuus.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.CalculationActivity;
import com.example.muirsuus.DataBaseHelper;
import com.example.muirsuus.classes.CardClass;
import com.example.muirsuus.R;
import com.example.muirsuus.TTHActivity;
import com.example.muirsuus.adapters.StartAdapter;
import com.example.muirsuus.WebActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private StartAdapter adapter;
    private Button inf_btn, calc_btn, manage_btn;
    private EditText findText;
    SimpleCursorAdapter userAdapter;
    DataBaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    ListView userList;

    final public List<CardClass> SCHEMES = new ArrayList<CardClass>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, mRecyclerView , false);

        SCHEMES.add(new CardClass(R.drawable.ikonka_svyaz,"Средства связи"));
        SCHEMES.add(new CardClass(R.drawable.apparatura,"Аппаратные"));
        SCHEMES.add(new CardClass(R.drawable.papka_s_dokumentami,"Документы ОТС"));
        SCHEMES.add(new CardClass(R.drawable.rukopis,"История"));


        mRecyclerView = (RecyclerView)root.findViewById(R.id.news_list);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new StartAdapter(SCHEMES);
        mRecyclerView.setAdapter(adapter);





        manage_btn = (Button)root.findViewById(R.id.manage_btn); //кнопка
        manage_btn.setBackgroundResource(R.drawable.button_const); //изменение цвета кнопки при нажатии

        View.OnClickListener onMBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) { //метод,который реализует нажатие на кнопку
                Intent intent = new Intent(getContext(), WebActivity.class); //переход по кнопке с помощью intent
                startActivity(intent);

            }
        };

        manage_btn.setOnClickListener(onMBtn);


        inf_btn = (Button)root.findViewById(R.id.inf_btn); //кнопка
        inf_btn.setBackgroundResource(R.drawable.button_const); //изменение цвета кнопки при нажатии

        View.OnClickListener onInfBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TTHActivity.class);
                startActivity(intent);
            }
        };
        inf_btn.setOnClickListener(onInfBtn);




        calc_btn = (Button) root.findViewById(R.id.calc_btn);
        calc_btn.setBackgroundResource(R.drawable.button_const);

        View.OnClickListener onCalcBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CalculationActivity.class);
                startActivity(intent);
            }
        };
        calc_btn.setOnClickListener(onCalcBtn);

        return root;
    }
}
