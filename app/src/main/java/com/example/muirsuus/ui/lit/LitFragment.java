package com.example.muirsuus.ui.lit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.muirsuus.R;

public class LitFragment extends Fragment {

    private LitViewModel mViewModel;

    public static LitFragment newInstance() {
        return new LitFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lit_fragment, container, false);
    }


}