package com.example.muirsuus.information_ui.subsection;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.muirsuus.MainActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.adapters.TTHAdapter;
import com.example.muirsuus.databinding.SubsectionFragmentBinding;
import com.example.muirsuus.information_database.PresubectionAndSubsection;
import com.example.muirsuus.information_database.SectionAndSubsection;
import com.example.muirsuus.information_ui.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class SubsectionFragment extends Fragment {

    private static final String LOG_TAG = "mLog" + SubsectionFragment.class.getCanonicalName();
    private SubsectionFragmentBinding binding;
    private TTHAdapter adapter;
    private ViewModelFactory viewModelFactory;
    private String section;
    private TTHAdapter.ListItemClickListener listener;
    private List<String> titles;
    private List<String> descriptions;
    private List<String> images;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.subsection_fragment, container, false);
        section = getArguments().getString("section");


        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //set Title for fragment
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(section);
        //set Home Up Btn and block the drawerLayout
        ((MainActivity)getActivity()).resetActionBar(true,
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


        //по клику переходим в PointFragment, передавая туда title, нажатого view
        listener = new TTHAdapter.ListItemClickListener() {
            @Override
            public void OnItemClickListener(int clickItemIndex) {
                Bundle bundle = new Bundle();
                bundle.putString("subsection", adapter.getTitles().get(clickItemIndex));
                NavController navController = NavHostFragment.findNavController(SubsectionFragment.this);
                navController.navigate(R.id.action_subsectionFragment_to_pointsFragment, bundle);
            }
        };
        adapter = new TTHAdapter(getContext(), listener);

        setupViewModel();


    }

    // in this method, we are getting instance of subsectionViewModel class passing into it section( title of pressed view)
    private void setupViewModel() {

        viewModelFactory = new ViewModelFactory(getContext());
        viewModelFactory.setSection(section);
        SubsectionViewModel subsectionViewModel = new ViewModelProvider(this, viewModelFactory).get(SubsectionViewModel.class);
        subsectionViewModel.getSubsections().observe(binding.getLifecycleOwner(), new Observer<SectionAndSubsection>() {
            @Override
            public void onChanged(SectionAndSubsection subsections) {
                if (subsections != null) {
                    titles = new ArrayList<>();
                    images = new ArrayList<>();
                    for (int i = 0; i < subsections.getSubsection().size(); i++) {
                        titles.add(subsections.getSubsection().get(i).getSubsection());
                        images.add(subsections.getSubsection().get(i).getSub_photo());
                    }
                    adapter.setTitles(titles);
                    adapter.setImages(images);
                    binding.recycler.setAdapter(adapter);
                    Log.d(LOG_TAG, "Set subsections to the RecyclerView ");
                }
            }
        });

        subsectionViewModel.getSubsections_pre().observe(binding.getLifecycleOwner(), new Observer<PresubectionAndSubsection>() {
            @Override
            public void onChanged(PresubectionAndSubsection subsections) {
                if (subsections != null) {
                    titles = new ArrayList<>();
                    images = new ArrayList<>();

                    for (int i = 0; i < subsections.getSubsection().size(); i++) {
                        titles.add(subsections.getSubsection().get(i).getSubsection());
                        images.add(subsections.getSubsection().get(i).getSub_photo());
                    }
                    adapter.setTitles(titles);
                    adapter.setImages(images);
                    binding.recycler.setAdapter(adapter);
                    Log.d(LOG_TAG, "Set subsections to the RecyclerView ");
                }
            }
        });
    }
}
