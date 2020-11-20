package com.example.muirsuus.information_ui.information;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.muirsuus.R;
import com.example.muirsuus.ViewPagerAdapter;
import com.example.muirsuus.databinding.InformationFragmentBinding;
import com.example.muirsuus.ui.fragments.DescriptionFragment;
import com.example.muirsuus.ui.fragments.TthFragment;

public class InformationFragment extends Fragment {
    private InformationFragmentBinding binding;
    private String point;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.information_fragment, container, false);
        point = getArguments().getString("point");

        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(point);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), 1);
        viewPagerAdapter.addFragment(new DescriptionFragment(point), "Назначение");
        viewPagerAdapter.addFragment(new TthFragment(point), "TTX");
        binding.textPager.setAdapter(viewPagerAdapter);
        binding.tabButtons.setupWithViewPager(binding.textPager);
    }
}