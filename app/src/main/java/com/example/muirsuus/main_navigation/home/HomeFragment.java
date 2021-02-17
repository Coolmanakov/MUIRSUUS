package com.example.muirsuus.main_navigation.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.muirsuus.MainActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.databinding.FragmentHomeBinding;
import com.example.muirsuus.registration.change_info.ChangePersonalData;

public class HomeFragment extends Fragment {

    private static final MutableLiveData<String> _name = new MutableLiveData<>();
    private static final LiveData<String> name = _name;
    private static FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setLifecycleOwner(HomeFragment.this);

        _name.setValue(MainActivity.getName());

        HomeFragmentViewModel user = new HomeFragmentViewModel();
        user.setContext(getContext());
        user.setName(name.getValue());
        user.setUsersInfo();


        binding.setUser(user);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).resetActionBar(false,
                DrawerLayout.LOCK_MODE_UNLOCKED);
        binding.changePersonalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ChangePersonalData.class);
                i.putExtra("name", name.getValue());
                startActivity(i);
            }
        });

    }
}
