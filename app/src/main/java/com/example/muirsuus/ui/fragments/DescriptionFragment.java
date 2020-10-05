package com.example.muirsuus.ui.fragments;



import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.muirsuus.DataBaseHelper;
import com.example.muirsuus.FullScreenActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.adapters.GalleryAdapter;
import com.example.muirsuus.classes.Army;
import com.example.muirsuus.classes.IRecyclerViewClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.muirsuus.classes.IndependentMethods.get_list_images;


public class DescriptionFragment extends Fragment{
    TextView title;

    TextView description_text;


    String point;
    //создание конструктора класса для получение нужного ID
    public DescriptionFragment(String point){
        this.point = point;
    }
    public DescriptionFragment(){
    }
    Army description = new Army();
    private List<String> photo_list = new ArrayList<>();
    private RecyclerView photo_recycler;
    private  GalleryAdapter adapter;
    private  LinearLayoutManager layoutManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_layout_description,container,false);

        title = root.findViewById(R.id.title_description);
        description_text = root.findViewById(R.id.description);




        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());

        description = dataBaseHelper.getDescriptionFromDB(point);


        //title.setText(point);
        Log.d("mLog","point Description " +  point);
        description_text.setText(description.get_description());
        /*photo_list = get_list_images(dataBaseHelper.get_list_photo(point));

        photo_recycler = (RecyclerView) root.findViewById(R.id.photo_recycler_description);
        photo_recycler.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        photo_recycler.setLayoutManager(layoutManager);


        final IRecyclerViewClickListener listener = new IRecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(getActivity(),"click",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), FullScreenActivity.class);
                i.putExtra("IMAGES",photo_list.toArray(new String[0]));
                Log.d("mLog", "photo List = " +photo_list.toArray(new String[0]));
                i.putExtra("POSITION",position);
                startActivity(i);
            }

        };
        adapter = new GalleryAdapter(photo_list,listener);
        photo_recycler.setAdapter(adapter);*/
        return root;
    }



}
