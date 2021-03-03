package com.example.muirsuus.calculation.SubscriberNetwork;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.classes.LiveDataList;
import com.example.muirsuus.classes.LiveDataMap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class SubscriberNetworkViewModel extends ViewModel {
    public MutableLiveData<String> divisionType = new MutableLiveData<>(null);
    public LiveDataList<String> officialType = new LiveDataList<>();
    public LiveDataList<SubscriberNetworkFragment1.OfficialInfo> officialRecyclerList =
            new LiveDataList<>();

    public LiveDataMap<String, List<String>> deviceMap = new LiveDataMap<>();
    public LiveDataList<SubscriberNetworkFragment2.DeviceInfo> deviceRecyclerList =
            new LiveDataList<>();
    public LiveDataList<SubscriberNetworkFragment2.DeviceRoomInfo> deviceRoomList =
            new LiveDataList<>();

    MutableLiveData<Double> layingSpeed = new MutableLiveData<>();
    MutableLiveData<Integer> personnelNumber = new MutableLiveData<>();
    MutableLiveData<String> relief = new MutableLiveData<>();
    MutableLiveData<String> terrain = new MutableLiveData<>();
    MutableLiveData<String> temperature = new MutableLiveData<>();
    MutableLiveData<String> snow = new MutableLiveData<>();
    MutableLiveData<String> wind = new MutableLiveData<>();

    public String getDivisionType() {
        return divisionType.getValue();
    }
}
