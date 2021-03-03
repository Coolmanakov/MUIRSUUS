package com.example.muirsuus.main_navigation.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.muirsuus.MainActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.databinding.FragmentWebBinding;

@SuppressLint("StaticFieldLeak")
public class WebFragment extends Fragment {
    FragmentWebBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ((MainActivity)getActivity()).resetActionBar(false,
                DrawerLayout.LOCK_MODE_UNLOCKED);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web, container, false);
        binding.web.setWebViewClient(new WebViewClient());
        binding.web.getSettings().setJavaScriptEnabled(true);
        binding.web.getSettings().setDomStorageEnabled(true);
        binding.web.loadUrl("http://192.168.20.100:80");
        //binding.web.loadUrl("http://91.210.170.162:80");




        return binding.getRoot();
    }


}
