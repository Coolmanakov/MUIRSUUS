package com.example.muirsuus.ui.layouts;



import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.muirsuus.DataBaseHelper;
import com.example.muirsuus.FullScreenActivity;
import com.example.muirsuus.IRecyclerViewClickListener;
import com.example.muirsuus.R;
import com.example.muirsuus.adapters.GalleryAdapter;
import com.example.muirsuus.classes.Army;

import java.io.IOException;
import java.util.List;
import com.example.muirsuus.SecondLevelOfNesting;


import static com.example.muirsuus.classes.IndependentMethods.GetLinkImages;
import static com.example.muirsuus.classes.IndependentMethods.loadImageFromData;
import static com.example.muirsuus.classes.Particular.UUID_INT;


public class DescriptionFragment extends Fragment{
    SecondLevelOfNesting sln = new SecondLevelOfNesting();
    TextView title;
    TextView subtitle;
    TextView description;
    //TextView textView5;
    RecyclerView recyclerView;
    ImageView imageView;
    ImageView imageView1;
    DataBaseHelper mDBHelper;
    Army DataBaseInfo;

    private GalleryAdapter adapter;
    private SQLiteDatabase mDb;
    private RecyclerView.LayoutManager mLayoutManager;

    int a;
    //создание конструктора класса для получение нужного ID
    public DescriptionFragment(int a){
        this.a = a;
    }
    public DescriptionFragment(){
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_layout_description,container,false);





        title = root.findViewById(R.id.title);
        subtitle = root.findViewById(R.id.subtitle);
        description = root.findViewById(R.id.description);

        //imageView = root.findViewById(R.id.khm_table);
        //imageView1 = root.findViewById(R.id.khm_table1);

        recyclerView = root.findViewById(R.id.photo_recycler);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);



        populaterecyclerView(a);

        return root;
    }


    public void populaterecyclerView(int id){
        mDBHelper = new DataBaseHelper(getActivity());

        try {
            mDBHelper.checkAndCopyDatabase();
            mDBHelper.openDataBase();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        mDBHelper.close();


        DataBaseInfo = mDBHelper.exMainList(id);

        title.setText(DataBaseInfo.getTitle());
        subtitle.setText(DataBaseInfo.getSubtitle());
        description.setText(DataBaseInfo.getDescription());
        //loadImageFromData(DataBaseInfo.getImage(),imageView);
        //loadImageFromData(DataBaseInfo.getGroupName(),imageView1);


        String linksPhoto = DataBaseInfo.getAllImage();
        List<String> photoLinks = GetLinkImages(linksPhoto);
        //final String photoLinksStr[] = photoLinks.toArray(new String[0]);
/*//-----------------------------------------------------------------------------
        final IRecyclerViewClickListener listener = new IRecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(getActivity(),"click",Toast.LENGTH_SHORT).show();


                Intent i = new Intent(getActivity(), FullScreenActivity.class);
                i.putExtra("IMAGES",photoLinksStr);
                i.putExtra("POSITION",position);
                startActivity(i);
            }
        };
//-----------------------------------------------------------------------------*/
        adapter = new GalleryAdapter(photoLinks, getActivity());
        recyclerView.setAdapter(adapter);

        mDBHelper.close();

    }

}
