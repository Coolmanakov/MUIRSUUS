package com.example.muirsuus.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.DataBaseHelper;
import com.example.muirsuus.R;
import com.example.muirsuus.WebActivity;
import com.example.muirsuus.adapters.StartAdapter;
import com.example.muirsuus.calculation.CalculationActivity;
import com.example.muirsuus.classes.CardClass;
import com.example.muirsuus.information_ui.MainInformationActivity;

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
    NavController navController;

    final public List<CardClass> SCHEMES = new ArrayList<CardClass>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, mRecyclerView , false);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SCHEMES.add(new CardClass(R.drawable.ikonka_svyaz,"Средства связи"));
        SCHEMES.add(new CardClass(R.drawable.apparatura,"Аппаратные"));
        SCHEMES.add(new CardClass(R.drawable.papka_s_dokumentami,"Документы ОТС"));
        SCHEMES.add(new CardClass(R.drawable.rukopis,"История"));


        mRecyclerView = (RecyclerView)view.findViewById(R.id.news_list);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new StartAdapter(SCHEMES);
        mRecyclerView.setAdapter(adapter);

        inf_btn = (Button)view.findViewById(R.id.inf_btn); //кнопка

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());

        /*if(dataBaseHelper.checkDataBase()){
            Log.d("mLog","DataBaseHelper: checkDataBase: database already exist");
        }
        else {
            try {
                dataBaseHelper.checkAndCopyDatabase();
                dataBaseHelper.openDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
            dataBaseHelper.close();

            Log.d("mLog", "База данных не найдена");
            //Toast.makeText(getContext(),"База данных не найдена",Toast.LENGTH_SHORT).show();
            inf_btn.setClickable(false);
        }*/





        manage_btn = (Button)view.findViewById(R.id.manage_btn); //кнопка

        View.OnClickListener onMBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) { //метод,который реализует нажатие на кнопку
                Intent intent = new Intent(getContext(), WebActivity.class); //переход по кнопке с помощью intent
                startActivity(intent);

            }
        };

        manage_btn.setOnClickListener(onMBtn);




        View.OnClickListener onInfBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainInformationActivity.class);
                startActivity(intent);
            }
        };
        inf_btn.setOnClickListener(onInfBtn);




        calc_btn = (Button) view.findViewById(R.id.calc_btn);


        View.OnClickListener onCalcBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CalculationActivity.class);
                startActivity(intent);
            }
        };
        calc_btn.setOnClickListener(onCalcBtn);



    }
}
