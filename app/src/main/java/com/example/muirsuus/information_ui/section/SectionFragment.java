package com.example.muirsuus.information_ui.section;

import android.annotation.SuppressLint;
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
import com.example.muirsuus.databinding.SectionFragmentBinding;
import com.example.muirsuus.information_database.InformationDatabase;
import com.example.muirsuus.information_database.section;
import com.example.muirsuus.information_ui.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class SectionFragment extends Fragment {
    private static final String LOG_TAG = "mLog " + SectionFragment.class.getCanonicalName();
    private SectionFragmentBinding binding;
    private TTHAdapter adapter;
    private ViewModelFactory viewModelFactory;
    private TTHAdapter.ListItemClickListener listener;
    private InformationDatabase informationDatabase;
    private List<String> titles;
    private List<String> descriptions;
    private List<String> images;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.section_fragment, container, false);
        binding.setLifecycleOwner(this);




        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Справочник");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);
        ((MainActivity) getActivity()).resetActionBar(false,
                DrawerLayout.LOCK_MODE_UNLOCKED);

        listener = new TTHAdapter.ListItemClickListener() {//по клику переходим в SubsectionFragment, передавая туда title, нажатого view
            @Override
            public void OnItemClickListener(int clickItemIndex) {
                Bundle bundle = new Bundle();
                NavController navController = NavHostFragment.findNavController(SectionFragment.this);
                bundle.putString("section", adapter.getTitles().get(clickItemIndex));
                if (adapter.getTitles().get(clickItemIndex).equals("Средства связи")) {
                    navController.navigate(R.id.action_sectionFragment_to_presubsectionFragment, bundle);
                } else {
                    navController.navigate(R.id.action_sectionFragment_to_subsectionFragment, bundle);
                }

            }
        };
        adapter = new TTHAdapter(getContext(), listener);

        setupViewModel();
    }

    @SuppressLint("FragmentLiveDataObserve")
    private void setupViewModel() {
        ViewModelFactory viewModelFactory = new ViewModelFactory(getContext()); //создали фабрику viewModel
        SectionViewModel sectionViewModel = new ViewModelProvider(this, viewModelFactory).get(SectionViewModel.class);//получили экземпляр класса sectionViewModel
        //binding.setSectionViewModel(sectionViewModel);
        sectionViewModel.getSections().observe(binding.getLifecycleOwner(), new Observer<List<section>>() {
            @Override
            public void onChanged(List<section> section) {
                titles = new ArrayList<>();
                descriptions = new ArrayList<>();
                images = new ArrayList<>();
                //получаем из списка section, нужные нам списки
                for (int i = 0; i < section.size(); i++) {

                    titles.add(section.get(i).getSection());
                    descriptions.add(section.get(i).getSectionDescription());
                    images.add(section.get(i).getSectionPhoto());
                }
                adapter.setTitles(titles);
                adapter.setImages(images);
                binding.recycler.setAdapter(adapter);
                Log.d(LOG_TAG, "Set sections to the RecyclerView");
            }
        });
    }


}