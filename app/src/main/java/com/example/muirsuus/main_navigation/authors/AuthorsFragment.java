package com.example.muirsuus.main_navigation.authors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.muirsuus.MainActivity;
import com.example.muirsuus.R;

public class AuthorsFragment extends Fragment {

    private AuthorsViewModel mViewModel;

    public static AuthorsFragment newInstance() {
        return new AuthorsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.authors_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AuthorsViewModel.class);
        getActivity().setTitle("Авторы");
        //set Home Up Btn and block the drawerLayout
        ((MainActivity) getActivity()).resetActionBar(true,
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

}