package com.example.muirsuus.information_ui.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.muirsuus.FullScreenActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.adapters.GalleryAdapter;
import com.example.muirsuus.adapters.ViewPagerAdapter;
import com.example.muirsuus.classes.IRecyclerViewClickListener;
import com.example.muirsuus.databinding.InformationFragmentBinding;
import com.example.muirsuus.information_database.PointAndInformation;
import com.example.muirsuus.information_ui.ViewModelFactory;
import com.example.muirsuus.main_navigation.information_fragmnets.DescriptionFragment;
import com.example.muirsuus.main_navigation.information_fragmnets.TthFragment;

import java.util.List;

import static com.example.muirsuus.classes.IndependentMethods.get_list_images;


public class InformationFragment extends Fragment {
    private InformationFragmentBinding binding;
    private String point;
    private final String LOG_TAG = "mLog";
    private GalleryAdapter adapter;
    private InformationViewModel viewModel;
    private String photoList;
    private List<String> photoLinks;
    private String[] photoLinksStr;
    private ViewModelFactory viewModelFactory;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.information_fragment, container, false);
        point = getArguments().getString("point");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(point);
        viewModel = new InformationViewModel(getContext(), point);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), 1);
        setUpViewModel();
    }

    private void setUpViewModel() {
        viewModelFactory = new ViewModelFactory(getContext());
        viewModelFactory.setPoint(point);
        InformationViewModel informationViewModel = new ViewModelProvider(this, viewModelFactory).get(InformationViewModel.class);
        informationViewModel.getInformation().observe(binding.getLifecycleOwner(), new Observer<PointAndInformation>() {
            @Override
            public void onChanged(PointAndInformation information) {
                if (information.getInformation() != null) {
                    photoList = information.getInformation().getGallery();
                    if (photoList != null) {
                        photoLinks = get_list_images(photoList);
                        photoLinksStr = photoLinks.toArray(new String[0]);

                        final IRecyclerViewClickListener listener = new IRecyclerViewClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                Intent i = new Intent(getContext(), FullScreenActivity.class);
                                i.putExtra("IMAGES", photoLinksStr);
                                i.putExtra("POSITION", position);
                                startActivity(i);
                            }
                        };
                        adapter = new GalleryAdapter(photoLinks, listener, getContext());
                        binding.photoRecycler.setAdapter(adapter);
                    }
                    if(information.getInformation().getDescription() != null) {
                        viewPagerAdapter.addFragment(new DescriptionFragment(point), "Назначение");
                    }
                    if(information.getInformation().getTth() != null) {
                        viewPagerAdapter.addFragment(new TthFragment(point), "TTX");
                    }
                }
                else {
                    viewPagerAdapter.addFragment(new DescriptionFragment(point), "Назначение");
                    viewPagerAdapter.addFragment(new TthFragment(point), "TTX");
                }
                    binding.textPager.setAdapter(viewPagerAdapter);
                    binding.tabButtons.setupWithViewPager(binding.textPager);

            }




        });
    }
}