package com.example.muirsuus.ui.fragments;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.muirsuus.DataBaseHelper;
import com.example.muirsuus.R;
import com.example.muirsuus.classes.Army;

import java.io.IOException;


public class TthFragment extends Fragment {
    String point;
    public TthFragment(String point){
        this.point = point;
    }
    public TthFragment(){

    }




    TextView title;
    TextView subtitle;
    TextView tth_text;
    private Army tth = new Army();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_layout_tth,container,false);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        title = root.findViewById(R.id.title_tth);
        tth_text = root.findViewById(R.id.tth_text);
        try {
            dataBaseHelper.updateDataBase();
            dataBaseHelper.checkAndCopyDatabase();
            dataBaseHelper.openDataBase();
        } catch (SQLiteException | IOException e) {
            e.printStackTrace();
        }




        if(point != null) {
            title.setText(point);
            tth = dataBaseHelper.getTTHFromDB(point);
            Log.d("mLog", "point TTH " + point);
            tth_text.setText(tth.getTTH());
        }


        return root;
    }
}
