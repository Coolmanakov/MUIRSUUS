package com.example.muirsuus.information_ui.presubsection;

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
import com.example.muirsuus.databinding.PresubsectionFragmentBinding;
import com.example.muirsuus.information_database.SectionAndPresubsection;
import com.example.muirsuus.information_ui.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class PresubsectionFragment extends Fragment {

    private static final String LOG_TAG = "mLog" + PresubsectionFragment.class.getCanonicalName();
    private PresubsectionViewModel mViewModel;
    private PresubsectionFragmentBinding binding;
    private String section;
    private TTHAdapter.ListItemClickListener listener;
    private TTHAdapter adapter;
    private ViewModelFactory viewModelFactory;
    private List<String> titles;
    private List<String> images;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.presubsection_fragment, container, false);

        section = getArguments().getString("section");

        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //set Title for fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(section);
        //set Home Up Btn and block the drawerLayout
        ((MainActivity) getActivity()).resetActionBar(true,
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        listener = new TTHAdapter.ListItemClickListener() {
            @Override
            public void OnItemClickListener(int clickItemIndex) {
                Bundle bundle = new Bundle();
                NavController navController = NavHostFragment.findNavController(PresubsectionFragment.this);

                if (adapter.getTitles().get(clickItemIndex).equals("Станции электроснабжения")) {

                    bundle.putString("subsection", adapter.getTitles().get(clickItemIndex));
                    navController.navigate(R.id.action_presubsectionFragment_to_pointsFragment, bundle);
                } else {
                    bundle.putString("section", adapter.getTitles().get(clickItemIndex));
                    navController.navigate(R.id.action_presubsectionFragment_to_subsectionFragment, bundle);
                }


            }
        };
        adapter = new TTHAdapter(getContext(), listener);

        setupViewModel();
    }

    private void setupViewModel() {
        viewModelFactory = new ViewModelFactory(getContext());
        viewModelFactory.setSection(section);
        PresubsectionViewModel presubsectionViewModel = new ViewModelProvider(this, viewModelFactory).get(PresubsectionViewModel.class);
        presubsectionViewModel.getPresubsection().observe(binding.getLifecycleOwner(), new Observer<SectionAndPresubsection>() {
            @Override
            public void onChanged(SectionAndPresubsection sectionAndPresubsection) {
                titles = new ArrayList<>();
                images = new ArrayList<>();
                for (int i = 0; i < sectionAndPresubsection.getPresubsections().size(); i++) {
                    titles.add(sectionAndPresubsection.getPresubsections().get(i).getPresubsection());
                    images.add(sectionAndPresubsection.getPresubsections().get(i).getPresub_photo());
                }
                adapter.setTitles(titles);
                adapter.setImages(images);
                binding.recycler.setAdapter(adapter);
                Log.d(LOG_TAG, "Set presubsection to the RecyclerView ");
            }
        });
    }
}