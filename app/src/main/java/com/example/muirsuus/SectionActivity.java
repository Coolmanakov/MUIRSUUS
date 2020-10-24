package com.example.muirsuus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.muirsuus.adapters.tth_mvvm.TTHAdapter;
import com.example.muirsuus.classes.CardClass;
import com.example.muirsuus.databinding.ActivitySectionBinding;

import java.util.ArrayList;
import java.util.List;

public class SectionActivity extends AppCompatActivity   {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TTHAdapter adapter;
    private final List<CardClass> SCHEMES = new ArrayList<CardClass>();
    NavController navController;
    private ActivitySectionBinding binding;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_section);

        SCHEMES.add(new CardClass(R.drawable.tank,"АВ и БТ техника"));
        SCHEMES.add(new CardClass(R.drawable.kalash,"Вооружение"));
        SCHEMES.add(new CardClass(R.drawable.lopata,"Инженерные средства"));
        SCHEMES.add(new CardClass(R.drawable.kniga,"Нормативно-правовая база"));
        SCHEMES.add(new CardClass(R.drawable.gaechny_klyuch,"Оперативно-техническая служба"));
        SCHEMES.add(new CardClass(R.drawable.palatka,"Подготовка"));
        SCHEMES.add(new CardClass(R.drawable.kompyuter,"Сетевой инженер"));
        SCHEMES.add(new CardClass(R.drawable.ratsia,"Средства связи"));
        SCHEMES.add(new CardClass(R.drawable.radiatsia,"Средства РХБЗ"));






        binding.sectionRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        adapter = new TTHAdapter(SCHEMES);



        adapter.SetOnItemClickListener(new TTHAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SCHEMES.get(position);

                Intent intent = new Intent(SectionActivity.this, SubsectionActivity.class);
                intent.putExtra("Build_subsection", SCHEMES.get(position).getTitle());//передаём название нажатоо view
                startActivity(intent);
            }
        });
        binding.sectionRecycler.setAdapter(adapter);


    }



}

