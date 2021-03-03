package com.example.muirsuus.classes;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class LiveDataList<T> extends MutableLiveData<List<T>> {

    public LiveDataList() {
        super(new LinkedList<>());
    }

    public LiveDataList(List<T> list) {
        super(list);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void add(T item) {
        add(size(), item);
    }

    public void add(int index, T item) {
        List<T> list = getValue();

        if (list != null) {
            list.add(index, item);
            setValue(list);
        }
    }

    public void remove(int index) {
        List<T> list = getValue();

        if (list != null) {
            list.remove(index);
            setValue(list);
        }
    }

    public void remove(T item) {
        List<T> list = getValue();

        if (list != null) {
            list.remove(item);
            setValue(list);
        }
    }

    public void clear() {
        setValue(new LinkedList<>());
    }

    public T get(int pos) {
        if (getValue() == null) return null;
        return getValue().get(pos);
    }

    public int size() {
        List<T> list = getValue();

        if (list != null) return list.size();
        return 0;
    }
}
