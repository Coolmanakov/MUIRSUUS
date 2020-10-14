package com.example.muirsuus.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class CardClass implements Parcelable, Comparable<CardClass> {
    private int image;
    private String photo;
    public String title;
    private int id;




    public CardClass(int image, String title,int id) {
        this.image = image;
        this.title = title;
        this.id = id;
    }
    public CardClass(int image, String title) {
        this.image = image;
        this.title = title;
    }
    public CardClass(String photo, String title) {
        this.photo = photo;
        this.title = title;
    }



    protected CardClass(Parcel in) {
        image = in.readInt();
        title = in.readString();
    }

    public static final Creator<CardClass> CREATOR = new Creator<CardClass>() {
        @Override
        public CardClass createFromParcel(Parcel in) {
            return new CardClass(in);
        }

        @Override
        public CardClass[] newArray(int size) {
            return new CardClass[size];
        }
    };

    public void setImage(int image){
        this.image = image;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public int getImage() {
        return image;
    }
    public String getTitle() {
        return title;
    }

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image);
        dest.writeString(title);
    }
    public int getId(){
        return id;
    }


    @Override
    public int compareTo(CardClass o) {
        return 0;
    }
    public int compare(CardClass o1, CardClass o2) {
        return o1.toString().compareTo(o2.toString());
    }
}