package com.example.muirsuus.information_ui.point;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.muirsuus.MainActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.adapters.TTHAdapter;
import com.example.muirsuus.databinding.PointsFragmentBinding;
import com.example.muirsuus.information_database.PreSubsectionAndPoint;
import com.example.muirsuus.information_database.SubsectionAndPoint;
import com.example.muirsuus.information_ui.ViewModelFactory;
import com.example.muirsuus.main_navigation.favourite.FavouriteViewModel;

import java.util.ArrayList;
import java.util.List;

public class PointsFragment extends Fragment {
    private static final String LOG_TAG = "mLog" + PointsFragment.class.getCanonicalName();
    private PointsFragmentBinding binding;
    private TTHAdapter adapter;
    private String subsection;
    private ViewModelFactory viewModelFactory;
    private TTHAdapter.ListItemClickListener listener;
    private NavController navController;
    private List<String> titles;
    private List<String> descriptions;
    private List<String> images;
    private FavouriteViewModel favouriteViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.points_fragment, container, false);
        subsection = getArguments().getString("subsection");

        binding.setLifecycleOwner(this);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //set Title for fragment
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(subsection);
        listener = new TTHAdapter.ListItemClickListener() {
            @Override
            public void OnItemClickListener(int clickItemIndex) {
                Bundle bundle = new Bundle();
                bundle.putString("point", adapter.getTitles().get(clickItemIndex));
                NavController navController = NavHostFragment.findNavController(PointsFragment.this);
                navController.navigate(R.id.action_pointsFragment_to_informationFragment, bundle);
            }
        };
        adapter = new TTHAdapter(getContext(), listener);
        setupViewModel();
    }

    private void setupViewModel() {
        viewModelFactory = new ViewModelFactory(getContext());
        viewModelFactory.setSubsection(subsection);
        favouriteViewModel = new FavouriteViewModel(getContext(), MainActivity.getName(), binding.getLifecycleOwner());
        adapter.setFavouriteViewModel(favouriteViewModel);

        PointViewModel pointViewModel = new PointViewModel(getContext(), subsection);
        pointViewModel.getPoints().observe(binding.getLifecycleOwner(), new Observer<SubsectionAndPoint>() {
            @Override
            public void onChanged(SubsectionAndPoint points) {
                if (points != null) {
                    titles = new ArrayList<>();
                    images = new ArrayList<>();
                    for (int i = 0; i < points.getPoint().size(); i++) {
                        titles.add(points.getPoint().get(i).getPoint());
                        images.add(points.getPoint().get(i).getPoint_photo());
                    }

                    //adapter.setIsFavBtnVisible(true);
                    adapter.setTitles(titles);
                    adapter.setImages(images);
                    binding.recycler.setAdapter(adapter);
                    Log.d(LOG_TAG, "Set points to the RecyclerView");
                }
            }
        });

        pointViewModel.getPoints_pre().observe(binding.getLifecycleOwner(), new Observer<PreSubsectionAndPoint>() {
            @Override
            public void onChanged(PreSubsectionAndPoint points) {
                if (points != null) {
                    titles = new ArrayList<>();
                    images = new ArrayList<>();

                    for (int i = 0; i < points.getPoints().size(); i++) {
                        titles.add(points.getPoints().get(i).getPoint());
                        images.add(points.getPoints().get(i).getPoint_photo());
                    }

                    adapter.setIsFavBtnVisible(true);
                    adapter.setTitles(titles);
                    adapter.setImages(images);
                    binding.recycler.setAdapter(adapter);
                    Log.d(LOG_TAG, "Set points to the RecyclerView");
                }

            }
        });

    }
}