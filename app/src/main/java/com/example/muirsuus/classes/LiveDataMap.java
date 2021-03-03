package com.example.muirsuus.classes;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LiveDataMap<T, E> extends MutableLiveData<HashMap<T, E>> {

    public LiveDataMap() {
        super(new HashMap<>());
    }

    public LiveDataMap(HashMap<T, E> map) {
        super(map);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void put(T key, E value) {
        HashMap<T, E> map = getValue();

        if (map != null) {
            map.put(key, value);
            setValue(map);
        }
    }

    public void remove(T key) {
        HashMap<T, E> map = getValue();

        if (map != null) {
            map.remove(key);
            setValue(map);
        }
    }

    public E get(T key) {
        if (getValue() == null) return null;
        return getValue().get(key);
    }

    public void clear() {
        setValue(new HashMap<>());
    }

    public int size() {
        HashMap<T, E> map = getValue();

        if (map != null) return map.size();
        return 0;
    }
}
