package com.example.muirsuus.information_ui.point;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.muirsuus.R;
import com.example.muirsuus.adapters.TTHAdapter;
import com.example.muirsuus.databinding.PointsFragmentBinding;
import com.example.muirsuus.information_ui.ViewModelFactory;

public class PointsFragment extends Fragment {
    private static final String LOG_TAG = "mLog";
    private PointsFragmentBinding binding;
    private TTHAdapter adapter;
    private String subsection;
    private ViewModelFactory viewModelFactory;
    private TTHAdapter.ListItemClickListener listener;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.points_fragment, container, false);
        navController = Navigation.findNavController(binding.getRoot());
        subsection = getArguments().getString("subsection");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listener = new TTHAdapter.ListItemClickListener() {
            @Override
            public void OnItemClickListener(int clickItemIndex) {
                Bundle bundle = new Bundle();
                bundle.putString("point", adapter.getEntries().getValue().get(clickItemIndex));
                navController.navigate(R.id.action_pointsFragment_to_informationFragment, bundle);
            }
        };
        adapter = new TTHAdapter(getContext(), listener);
        binding.recycler.setAdapter(adapter);
        setupViewModel();
    }

    private void setupViewModel() {
        viewModelFactory = new ViewModelFactory(getContext());
        viewModelFactory.setSubsection(subsection);
        PointViewModel pointViewModel = new ViewModelProvider(this, viewModelFactory).get(PointViewModel.class);
        Log.d(LOG_TAG, "Set list of sections to the RecyclerView");
        adapter.setEntries(pointViewModel.getPoints());
    }
}