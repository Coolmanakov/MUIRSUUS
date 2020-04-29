package com.example.muirsuus.ui.layouts;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.muirsuus.R;
import com.example.muirsuus.ui.lit.LitViewModel;


public class DescriptionFragment extends Fragment {


    private DescriptionViewModel DescriViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DescriViewModel =
                ViewModelProviders.of(this).get(DescriptionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_layout_description,container,false);
        final TextView textView = root.findViewById(R.id.text_descri);
        DescriViewModel.getText().observe(getViewLifecycleOwner(),new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
