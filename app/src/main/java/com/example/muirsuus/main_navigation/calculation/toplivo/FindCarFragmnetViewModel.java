package com.example.muirsuus.main_navigation.calculation.toplivo;

import androidx.lifecycle.ViewModel;

public class FindCarFragmnetViewModel extends ViewModel {
    public String carName;
    public String imageName;

    public FindCarFragmnetViewModel(String carName, String imageName) {
        this.carName = carName;
        this.imageName = imageName;
    }
}