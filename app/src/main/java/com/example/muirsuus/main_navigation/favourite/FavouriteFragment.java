package com.example.muirsuus.main_navigation.favourite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.muirsuus.MainActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.adapters.TTHAdapter;
import com.example.muirsuus.databinding.FragmentFavouriteBinding;
import com.example.muirsuus.information_database.point;

import java.util.ArrayList;
import java.util.List;

public class  FavouriteFragment extends Fragment {
    private static final String LOG_TAG = "mLog" + FavouriteFragment.class.getCanonicalName();
    private FragmentFavouriteBinding binding;
    private TTHAdapter.ListItemClickListener listener;
    private TTHAdapter adapter;
    private List<String> titles;
    private List<String> descriptions;
    private List<String> images;
    private String userName = "";
    private FavouriteViewModel favouriteViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite, container,false);

        userName = getArguments().getString("userName");

        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).resetActionBar(false,
                DrawerLayout.LOCK_MODE_UNLOCKED);

        listener = new TTHAdapter.ListItemClickListener() {//по клику переходим в SubsectionFragment, передавая туда title, нажатого view
            @Override
            public void OnItemClickListener(int clickItemIndex) {
                Bundle bundle = new Bundle();
                bundle.putString("point", adapter.getTitles().get(clickItemIndex));
                NavController navController = NavHostFragment.findNavController(FavouriteFragment.this);
                navController.navigate(R.id.action_favouriteFragment_to_informationFragment, bundle);
            }
        };
        adapter = new TTHAdapter(getContext(), listener);


        // создаём вью модел, которая передаст в адаптер необходимые данные
        setupViewModel();
    }

    @SuppressLint("FragmentLiveDataObserve")
    private void setupViewModel() {
        FavouriteViewModel favouriteViewModel = new FavouriteViewModel(getContext(), userName,binding.getLifecycleOwner());
        adapter.setFavouriteViewModel(favouriteViewModel);
        favouriteViewModel.alreadyFavourite = true;
        List<point> points = favouriteViewModel.getPoints();
        if(points != null){
            titles = new ArrayList<>();
            descriptions = new ArrayList<>();
            images = new ArrayList<>();

            for (int i = 0; i < points.size(); i++) {

                titles.add(points.get(i).getPoint());
                descriptions.add(points.get(i).getPoint_description());
                images.add(points.get(i).getPoint_photo());
            }

            adapter.setIsFavBtnVisible(true);
            adapter.setTitles(titles);
            adapter.setDescriptions(descriptions);
            adapter.setImages(images);
            binding.recycler.setAdapter(adapter);
            Log.d(LOG_TAG, "Set favourite points to the RecyclerView");
        }
    }



}
