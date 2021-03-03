package com.example.muirsuus.registration;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.muirsuus.main_navigation.favourite.FavouriteConverter;

import java.util.List;

@Entity
public class privateInfo {
    public String name;
    @PrimaryKey(autoGenerate = true)
    Integer id;
    String password;

    String nickname;
    String surname;
    String patronymic;
    String rank;
    String appointment;
    String workPlace;

    @TypeConverters({FavouriteConverter.class})
    List<String> listFavourite;

    public List<String> getListFavourite() {
        return listFavourite;
    }

    public void setListFavourite(List<String> listFavourite) {
        this.listFavourite = listFavourite;
    }

    public privateInfo(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Ignore
    public privateInfo(String name, String password, String nickname, String surname, String patronymic, String rank, String appointment, String workPlace) {
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.surname = surname;
        this.patronymic = patronymic;
        this.rank = rank;
        this.appointment = appointment;
        this.workPlace = workPlace;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
