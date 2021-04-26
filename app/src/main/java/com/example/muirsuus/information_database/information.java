package com.example.muirsuus.information_database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class information {
    @PrimaryKey(autoGenerate = true)
    Integer informId;


    String description;
    String tth;
    String gallery;
    String spendWorkDay;
    String spendWorkNight;
    String workers;
    String timeOpenAndConect;
    String kabels;
    String apparatura;

    public Integer getInformId() {
        return informId;
    }

    public void setInformId(Integer informId) {
        this.informId = informId;
    }

    public String getSpendWorkDay() {
        return spendWorkDay;
    }

    public void setSpendWorkDay(String spendWorkDay) {
        this.spendWorkDay = spendWorkDay;
    }

    public String getSpendWorkNight() {
        return spendWorkNight;
    }

    public void setSpendWorkNight(String spendWorkNight) {
        this.spendWorkNight = spendWorkNight;
    }

    public String getWorkers() {
        return workers;
    }

    public void setWorkers(String workers) {
        this.workers = workers;
    }

    public String getTimeOpenAndConect() {
        return timeOpenAndConect;
    }

    public void setTimeOpenAndConect(String timeOpenAndConect) {
        this.timeOpenAndConect = timeOpenAndConect;
    }

    public String getKabels() {
        return kabels;
    }

    public void setKabels(String kabels) {
        this.kabels = kabels;
    }

    public String getApparatura() {
        return apparatura;
    }

    public void setApparatura(String apparatura) {
        this.apparatura = apparatura;
    }

    public information(Integer informId, String description, String tth, String gallery) {
        this.informId = informId;
        this.description = description;
        this.tth = tth;
        this.gallery = gallery;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTth() {
        return tth;
    }

    public void setTth(String tth) {
        this.tth = tth;
    }

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }
}
