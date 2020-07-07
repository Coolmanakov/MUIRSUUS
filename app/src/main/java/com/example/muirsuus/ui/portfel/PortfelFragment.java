package com.example.muirsuus.ui.portfel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.DataBaseHelper;
import com.example.muirsuus.FirstLevel0fNesting;
import com.example.muirsuus.R;
import com.example.muirsuus.SecondLevelOfNesting;
import com.example.muirsuus.adapters.TTHAdapter;
import com.example.muirsuus.classes.Army;
import com.example.muirsuus.classes.CardClass;
import com.example.muirsuus.classes.MyObject;

import java.util.ArrayList;
import java.util.List;

public class PortfelFragment extends Fragment {

    DataBaseHelper mDBHelper;
    Army DataBaseInfo;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TTHAdapter adapter;
    final List<CardClass> favorites = new ArrayList<CardClass>();
    private List<Integer> list_of_ids = null;
    private MyObject myObject;




    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_portfel,container,false);
        setHasOptionsMenu(true);//включаем режим вывода элементов фрагмента в ActionBar.
        mDBHelper = new DataBaseHelper(getActivity());




        //mDBHelper.deleteTable();
        list_of_ids = mDBHelper.createFavoriteList();
//в БД начинается с 1, а самый первый элемент -КШМ  имеет id 0


        for(int personal_id :  list_of_ids) {

            DataBaseInfo = mDBHelper.exMainList(personal_id);//сюда добавляем id тех элементов, которые были добавлены в закладки
            favorites.add(new CardClass(R.drawable.ikonka_svyaz, DataBaseInfo.getTitle(),personal_id));

            Log.d("mLog"," run with such personal_id " + personal_id);
        }


        //String linksPhoto = DataBaseInfo.getAllImage();
//--------------создание recycler view--------------------
        mRecyclerView = (RecyclerView)root.findViewById(R.id.portfel_recycler);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new TTHAdapter(favorites);

//--------------создание recycler view--------------------
        mRecyclerView.setAdapter(adapter);

        adapter.SetOnLongItemClickListener(new TTHAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(int position) {
                Log.d("mLog","position = " + position);
                int id = adapter.removeById(position);
                mDBHelper.deleteRow(id);

            }
        });
        adapter.SetOnItemClickListener(new TTHAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(PortfelFragment.this.getActivity(), SecondLevelOfNesting.class);
                int id = adapter.findById(position);
                myObject = new MyObject(0,id);
                intent.putExtra(MyObject.class.getCanonicalName(), myObject);
                startActivity(intent);
            }
        });
        return root;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.hidding_menu_with_bin, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
