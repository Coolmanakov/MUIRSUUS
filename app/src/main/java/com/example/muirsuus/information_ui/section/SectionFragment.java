package com.example.muirsuus.information_ui.section;

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

import com.example.muirsuus.R;
import com.example.muirsuus.adapters.TTHAdapter;
import com.example.muirsuus.databinding.SectionFragmentBinding;
import com.example.muirsuus.information_ui.TthViewModel;
import com.example.muirsuus.information_ui.ViewModelFactory;

public class SectionFragment extends Fragment {
    private static final String LOG_TAG = "mLog";
    private SectionFragmentBinding binding;
    private TTHAdapter adapter;
    private ViewModelFactory viewModelFactory;
    private TthViewModel tthViewModel;
    private NavController navController;
    private TTHAdapter.ListItemClickListener listener;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.section_fragment, container, false);
        //navController = Navigation.findNavController(binding.getRoot());
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        listener = new TTHAdapter.ListItemClickListener() {//по клику переходим в SubsectionFragment, передавая туда title, нажатого view
            @Override
            public void OnItemClickListener(int clickItemIndex) {
                Bundle bundle = new Bundle();
                bundle.putString("section", adapter.getEntries().getValue().get(clickItemIndex));
                //navController.navigate(R.id.action_sectionFragment_to_subsectionFragment, bundle);
            }
        };
        adapter = new TTHAdapter(getContext(), listener);
        setupViewModel();
        binding.recycler.setAdapter(adapter);

    }

    private void setupViewModel() {

        viewModelFactory = new ViewModelFactory(getContext()); //создали фабрику viewModel
        SectionViewModel sectionViewModel = new ViewModelProvider(this, viewModelFactory).get(SectionViewModel.class);//получили экземпляр класса sectionViewModel
        adapter.setEntries(sectionViewModel.getSections());//передали в адаптер лист sections
        Log.d("mLog", "" + sectionViewModel);

        Log.d(LOG_TAG, "Set list of sections to the RecyclerView");
    }


}