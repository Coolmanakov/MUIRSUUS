package com.example.muirsuus.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class CardClass implements Parcelable {
    private int image;
    private String title;
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
}