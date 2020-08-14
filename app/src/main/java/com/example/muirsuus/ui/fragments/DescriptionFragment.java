package com.example.muirsuus.ui.fragments;



import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.muirsuus.DataBaseHelper;
import com.example.muirsuus.R;
import com.example.muirsuus.adapters.GalleryAdapter;
import com.example.muirsuus.classes.Army;

import java.io.IOException;


public class DescriptionFragment extends Fragment{
    TextView title;
    TextView subtitle;
    TextView description_text;
    //TextView textView5;
    RecyclerView recyclerView;
    ImageView imageView;
    ImageView imageView1;
    DataBaseHelper mDBHelper;
    Army DataBaseInfo;

    private GalleryAdapter adapter;
    private SQLiteDatabase mDb;
    private RecyclerView.LayoutManager mLayoutManager;

    String point;
    //создание конструктора класса для получение нужного ID
    public DescriptionFragment(String point){
        this.point = point;
    }
    public DescriptionFragment(){
    }
    Army description = new Army();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_layout_description,container,false);

        title = root.findViewById(R.id.title_description);
        description_text = root.findViewById(R.id.description);




        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());

        try {
            dataBaseHelper.updateDataBase();
            dataBaseHelper.checkAndCopyDatabase();
            dataBaseHelper.openDataBase();
        } catch (SQLiteException | IOException e) {
            e.printStackTrace();
        }

        description = dataBaseHelper.getDescriptionFromDB(point);


        title.setText(point);
        Log.d("mLog","point Description " +  point);
        description_text.setText(description.get_description());





        return root;
    }



}
