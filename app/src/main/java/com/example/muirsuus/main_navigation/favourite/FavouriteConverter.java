package com.example.muirsuus.main_navigation.favourite;

import android.util.Log;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FavouriteConverter {
    @TypeConverter
    public String toFavourite(List<String> favourites) {
        String data = "";
        if(favourites != null){
             data = favourites.stream().collect(Collectors.joining(","));
        }
        Log.d("mLog", "data to Favourite " + data);
        return  data;
    }

    @TypeConverter
    public List<String> fromFavourite(String data) {
        Log.d("mLog", "data from Favourite" + Arrays.asList(data.split(",")));
        return Arrays.asList(data.split(","));
    }
}
