package com.example.muirsuus.sostav_uzla;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.muirsuus.R;
import com.google.android.material.tabs.TabLayout;

public class Sostav_uzla_svyazi extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sostav_uzla_svyazi, null);

        ViewPager viewPager = (ViewPager)v.findViewById(R.id.viewpager);
        viewPager.setAdapter(
                new UzelFragmentPagerAdapter(getChildFragmentManager(), getActivity())
        );

        TabLayout tabLayout = (TabLayout)v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }
}