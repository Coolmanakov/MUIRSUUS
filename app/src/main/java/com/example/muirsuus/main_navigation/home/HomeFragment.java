package com.example.muirsuus.main_navigation.home;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private static final MutableLiveData<String> _name = new MutableLiveData<>();
    private static final LiveData<String> name = _name;
    private static FragmentHomeBinding binding;
    private static final String[] titles = new String[]{"Литература", "Настройки", "Руководство по эксплуатации", "Что-то ещё", "Что-то ещё", "Что-то ещё", "Что-то ещё"};
    public static final String MAIN_TOKEN = "token";
    private static StartAdapter adapter;
    private final List<CardClass> cards = new ArrayList<>();
    private StartAdapter.ListItemClickListener listener;
    public static final String MAIN_URL = "main_url";
    private static final int[] images = new int[]{R.drawable.icon_literature, R.drawable.icon_settings, R.drawable.icon_favourite, R.drawable.ikonka_svyaz, R.drawable.kompyuter, R.drawable.lopata, R.drawable.palatka};
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private SharedPreferences savedToken;
    private SharedPreferences savedUrl;


    @SuppressLint("CommitPrefEdits")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Главная");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setLifecycleOwner(HomeFragment.this);

        _name.setValue(MainActivity.getName());

        savedToken = getContext().getSharedPreferences(MAIN_TOKEN, Context.MODE_PRIVATE);
        savedUrl = getContext().getSharedPreferences(MAIN_URL, Context.MODE_PRIVATE);

        if (savedToken.contains(MAIN_TOKEN)) {
            if (savedUrl.contains(MAIN_URL)) {
                String url = savedUrl.getString(MAIN_URL, "");
                String token = savedToken.getString(MAIN_TOKEN, "");
                HomeFragmentViewModel user = new HomeFragmentViewModel(url, token);
                binding.setUser(user);
                user.loadUserInfo();
            }
            //загружаем с сервера данные пользователя
        }


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).resetActionBar(false,
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        /*binding.changePersonalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ChangePersonalData.class);
                i.putExtra("name", name.getValue());
                startActivity(i);
            }
        });*/
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
        adapter = new StartAdapter(cards, listener, getActivity());
        binding.recycler.setAdapter(adapter);
       /*binding.personalPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else{
                        pickImageFromGallery();

                    }
                }
            }
        });*/

    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                }
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.personalPhoto.setImageURI(data.getData());
        }
    }


}
