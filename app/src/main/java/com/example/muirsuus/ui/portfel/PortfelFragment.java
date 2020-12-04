package com.example.muirsuus.ui.portfel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.DataBaseHelper;
import com.example.muirsuus.R;
import com.example.muirsuus.adapters.TTHAdapter;
import com.example.muirsuus.classes.Army;
import com.example.muirsuus.classes.CardClass;
import com.example.muirsuus.classes.MyObject;

import java.util.ArrayList;
import java.util.List;

public class PortfelFragment extends Fragment {

    DataBaseHelper dataBaseHelper;
    Army DataBaseInfo;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TTHAdapter adapter;
    final List<CardClass> favorites = new ArrayList<CardClass>();
    private final ArrayList<String> list_of_ids = new ArrayList<>();
    private MyObject myObject;
    private final Army subsection_photo = new Army();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_portfel, container, false);

        return root;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.hidding_menu_with_bin, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
            dataBaseHelper.deleteTable();

            default:
                return super.onOptionsItemSelected(item);

        }
    }



}
