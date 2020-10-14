package com.example.muirsuus.ui.lit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.muirsuus.R;

public class LitFragment extends Fragment {

    private LitViewModel litViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

         View root = inflater.inflate(R.layout.fragment_portfel,container,false);

         return root;
    }
}
