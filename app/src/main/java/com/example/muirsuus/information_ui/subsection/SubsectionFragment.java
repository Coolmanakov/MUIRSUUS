package com.example.muirsuus.information_ui.subsection;

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
import com.example.muirsuus.databinding.SubsectionFragmentBinding;
import com.example.muirsuus.information_ui.ViewModelFactory;

public class SubsectionFragment extends Fragment {

    private static final String LOG_TAG = "mLog";
    private SubsectionFragmentBinding binding;
    private TTHAdapter adapter;
    private ViewModelFactory viewModelFactory;
    private String section;
    private TTHAdapter.ListItemClickListener listener;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.subsection_fragment, container, false);
        navController = Navigation.findNavController(binding.getRoot());
        section = getArguments().getString("section");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupViewModel();
        //по клику переходим в PointFragment, передавая туда title, нажатого view
        listener = new TTHAdapter.ListItemClickListener() {
            @Override
            public void OnItemClickListener(int clickItemIndex) {
                Bundle bundle = new Bundle();
                bundle.putString("subsection", adapter.getEntries().getValue().get(clickItemIndex));
                navController.navigate(R.id.action_subsectionFragment_to_pointsFragment, bundle);
            }
        };
        adapter = new TTHAdapter(getContext(), listener);
        binding.recycler.setAdapter(adapter);

    }

    // in this method, we are getting instance of subsectionViewModel class passing into it section( title of pressed view)
    private void setupViewModel() {

        viewModelFactory = new ViewModelFactory(getContext());
        viewModelFactory.setSection(section);
        SubsectionViewModel subsectionViewModel = new ViewModelProvider(this, viewModelFactory).get(SubsectionViewModel.class);
        adapter.setEntries(subsectionViewModel.getSubsections());
        Log.d(LOG_TAG, "Set list of sections to the RecyclerView");
    }
}