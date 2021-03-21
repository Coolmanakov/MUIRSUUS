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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.muirsuus.MainActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.adapters.StartAdapter;
import com.example.muirsuus.classes.CardClass;
import com.example.muirsuus.databinding.FragmentHomeBinding;
import com.example.muirsuus.registration.change_info.ChangePersonalData;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final MutableLiveData<String> _name = new MutableLiveData<>();
    private static final LiveData<String> name = _name;
    private static FragmentHomeBinding binding;
    private static final String[] titles = new String[]{"Литература", "Настройки", "Что-то ещё", "Что-то ещё", "Что-то ещё", "Что-то ещё", "Что-то ещё"};
    private static final int[] images = new int[]{R.drawable.kniga, R.drawable.gaechny_klyuch, R.drawable.apparatnai, R.drawable.ikonka_svyaz, R.drawable.kompyuter, R.drawable.lopata, R.drawable.palatka};
    private static StartAdapter adapter;
    private final List<CardClass> cards = new ArrayList<>();
    private StartAdapter.ListItemClickListener listener;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Главная");
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
        if (cards.isEmpty()) {
            for (int i = 0; i < 7; i++) {
                cards.add(new CardClass(images[i], titles[i]));
            }
        }


        listener = new StartAdapter.ListItemClickListener() {
            @Override
            public void OnItemClickListener(int clickItemIndex) {
                NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                if (clickItemIndex == 0) {
                    navController.navigate(R.id.action_navigation_home_to_litFragment);
                } else if (clickItemIndex == 1) {
                    navController.navigate(R.id.action_navigation_home_to_settingsFragment);
                }

            }
        };
        adapter = new StartAdapter(cards, listener);
        binding.recycler.setAdapter(adapter);

    }
}
