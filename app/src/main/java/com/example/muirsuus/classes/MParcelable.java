package com.example.muirsuus.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MParcelable implements Parcelable {

    private List<String> arrayList = new ArrayList<String>(); //для передачи points из БД

    public MParcelable(List arrayList) {
        this.arrayList = arrayList;
    }


    public void setList(List<String> list) {
        arrayList = list;
    }

    public List<String> getList() {
        return arrayList;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    public MParcelable(Parcel in) {
        in.readStringList(arrayList);//для приема данных
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(arrayList);//записать в parcel
    }

    public static final Parcelable.Creator<MParcelable> CREATOR = new Parcelable.Creator<MParcelable>() {

        @Override
        public MParcelable createFromParcel(Parcel source) {
            return new MParcelable(source);
        }

        @Override
        public MParcelable[] newArray(int size) {
            return new MParcelable[size];
        }
    };

}
