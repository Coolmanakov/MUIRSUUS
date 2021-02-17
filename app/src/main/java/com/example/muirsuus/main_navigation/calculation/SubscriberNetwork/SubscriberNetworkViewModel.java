package com.example.muirsuus.main_navigation.calculation.SubscriberNetwork;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SubscriberNetworkViewModel extends ViewModel {

    public static MutableLiveData<String> mDivisionType = new MutableLiveData<>("");
    public static MutableLiveData<String> mControlPointType = new MutableLiveData<>("");
    public static MutableLiveData<List<String>> mOfficialType = new MutableLiveData<>(Collections.emptyList());
    public static MutableLiveData<HashMap<String, ArrayList<String>>> mDeviceType = new MutableLiveData<>(new HashMap<>());
    public static MutableLiveData<HashMap<String, Integer>> mDeviceRoomType = new MutableLiveData<>(new HashMap<>());
    public static MutableLiveData<HashMap<String, String>> mCableType = new MutableLiveData<>(new HashMap<>());
    public static MutableLiveData<HashMap<String, Integer>> mCableLength = new MutableLiveData<>(new HashMap<>());

    public LiveData<String> getDivisionType() {
        return mDivisionType;
    }

    public LiveData<String> getControlPointType() {
        return mControlPointType;
    }

    public LiveData<List<String>> getOfficialType() {
        return mOfficialType;
    }

    public LiveData<HashMap<String, ArrayList<String>>> getDeviceType() {
        return mDeviceType;
    }

    public LiveData<HashMap<String, ArrayList<String>>> getDeviceRoomType() {
        return mDeviceType;
    }

    public LiveData<HashMap<String, ArrayList<String>>> getCableType() {
        return mDeviceType;
    }

    public LiveData<HashMap<String, ArrayList<String>>> getCableLength() {
        return mDeviceType;
    }

    public void setDivisionType(String divisionType) {
        mDivisionType.setValue(divisionType);
    }

    public void setControlPointType(String controlPointType) {
        mControlPointType.setValue(controlPointType);
    }

    public void setOfficialType(List<String> officialType) {
        mOfficialType.setValue(officialType);
    }

    public void setDeviceType(HashMap<String, ArrayList<String>> deviceType) {
        mDeviceType.setValue(deviceType);
    }

    public void setDeviceRoomType(HashMap<String, Integer> deviceRoomType) {
        mDeviceRoomType.setValue(deviceRoomType);
    }

    public void setCableType(HashMap<String, String> cableType) {
        mCableType.setValue(cableType);
    }

    public void setCableLength(HashMap<String, Integer> cableLength) {
        mCableLength.setValue(cableLength);
    }
}
