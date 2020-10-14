package com.example.muirsuus.classes;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class MyObject implements Parcelable {



    public int s;
    public int i;

    // обычный конструктор
    public MyObject(int _s, int _i) {
        s = _s;
        i = _i;
    }

    // не знаю зачем он
    public int describeContents() {
        return 0;
    }

    // упаковываем объект в Parcel
    //В методе writeToParcel мы получаем на вход Parcel и упаковываем в него наш объект.
    // Т.е., в нашем случае, помещаем туда переменные s и i.  flags не используем.
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(s);
        parcel.writeInt(i);
    }

    //CREATOR типа Parcelable.Creator<MyObject> используется для создания экземпляра нашего MyObject и заполнения его данными из Parcel.
    //Для этого используется его метод createFromParcel, который мы должны реализовать. На вход нам дается Parcel, а вернуть мы должны готовый MyObject.
    // В нашем примере мы используем здесь конструктор MyObject(Parcel parcel), который реализован чуть дальше.
    public static final Parcelable.Creator<MyObject> CREATOR = new Parcelable.Creator<MyObject>() {
        // распаковываем объект из Parcel
        public MyObject createFromParcel(Parcel in) {
            return new MyObject(in);
        }
        //Смысл метода newArray остался для меня непонятен
        public MyObject[] newArray(int size) {
            return new MyObject[size];
        }
    };

    // конструктор, считывающий данные из Parcel
    //Конструктор MyObject(Parcel parcel) принимает на вход Parcel и заполняет объект данными из него.
    // Этот метод использовался нами чуть ранее в CREATOR.createFromParcel.
    private MyObject(Parcel parcel) {
        s = parcel.readInt();
        i = parcel.readInt();
    }

}