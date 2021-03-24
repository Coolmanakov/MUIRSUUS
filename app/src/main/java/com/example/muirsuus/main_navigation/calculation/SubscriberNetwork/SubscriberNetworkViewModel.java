package com.example.muirsuus.main_navigation.calculation.SubscriberNetwork;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.classes.LiveDataList;
import com.example.muirsuus.classes.LiveDataMap;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import kotlin.jvm.Volatile;

public class SubscriberNetworkViewModel extends ViewModel {

    // Value of Division Spinner
    public MutableLiveData<String> divisionType = new MutableLiveData<>(null);

    public void setDivision(String value) {
        if (divisionType.getValue() != null && divisionType.getValue().equals(value)) return;
        divisionType.setValue(value);
        officialType.clear();
        deviceMap.clear();
        deviceRecyclerList.clear();
        deviceRoomList.clear();
    }

    // Value of Official Spinner
    public LiveDataList<String> officialType = new LiveDataList<>();

    // List for RecyclerView in 1 Fragment
    public LiveDataList<SubscriberNetworkFragment1.OfficialInfo> officialRecyclerList = new LiveDataList<>();

    // Map Official -> Used Devices
    public LiveDataMap<String, List<String>> deviceMap = new LiveDataMap<>();

    // List for RecyclerView in 2 Fragment
    public LiveDataList<SubscriberNetworkFragment2.DeviceInfo> deviceRecyclerList = new LiveDataList<>();

    // List for indexed device rooms with connected devices
    public LiveDataList<SubscriberNetworkFragment2.DeviceRoomInfo> deviceRoomList = new LiveDataList<>();

    // Settings of Environment
    public MutableLiveData<Double> layingSpeed = new MutableLiveData<>();
    public MutableLiveData<Integer> personnelNumber = new MutableLiveData<>();
    public MutableLiveData<String> relief = new MutableLiveData<>();
    public MutableLiveData<String> terrain = new MutableLiveData<>();
    public MutableLiveData<String> temperature = new MutableLiveData<>();
    public MutableLiveData<String> snow = new MutableLiveData<>();
    public MutableLiveData<String> wind = new MutableLiveData<>();

    // Database with values for spinners
    @Volatile
    private SubscriberNetworkDatabase databaseInstance;

    public void initDatabase(Context context) {
        if (databaseInstance == null)
            databaseInstance = SubscriberNetworkDatabase.getInstance(context);
    }

    public List<String> getDivisionList() {
        return nonNullListIOThread(() -> databaseInstance.dao().getAllDivisions());
    }

    public List<String> getOfficialListByDivision() {
        return nonNullListIOThread(() -> databaseInstance.dao().getOfficialByDivision(divisionType.getValue()));
    }

    public List<String> getDeviceList() {
        return nonNullListIOThread(() -> databaseInstance.dao().getAllDevices());
    }

    public List<String> getDeviceRoomListWithDevice(String device) {
        return nonNullListIOThread(() -> databaseInstance.dao().getDeviceEntryByDevice(device))
                .stream()
                .map(x -> nullableIOThread(() -> databaseInstance.dao().getDeviceRoomById(x.deviceRoomId)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getDeviceEntryByDeviceRoom(String deviceRoom) {
        return nonNullListIOThread(() -> databaseInstance.dao().getDeviceEntryByDeviceRoom(deviceRoom))
                .stream()
                .map(x -> new AbstractMap.SimpleEntry<>(
                        nullableIOThread(() -> databaseInstance.dao().getDeviceById(x.deviceId)),
                        x.deviceCount)
                )
                .filter(x -> x.getKey() != null && x.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private <T> List<T> nonNullListIOThread(Callable<List<T>> f) {
        List<T> result = nullableIOThread(f);
        if (result == null) return Collections.emptyList();
        return result;
    }

    private <T> T nullableIOThread(Callable<T> f) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            return executor.submit(f).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Clear all LiveData of ViewModel
    public void clear() {
        divisionType = new MutableLiveData<>(null);
        officialType = new LiveDataList<>();
        officialRecyclerList = new LiveDataList<>();
        deviceMap = new LiveDataMap<>();
        deviceRecyclerList = new LiveDataList<>();
        deviceRoomList = new LiveDataList<>();
        layingSpeed = new MutableLiveData<>();
        personnelNumber = new MutableLiveData<>();
        relief = new MutableLiveData<>();
        terrain = new MutableLiveData<>();
        temperature = new MutableLiveData<>();
        snow = new MutableLiveData<>();
        wind = new MutableLiveData<>();
    }
}
