package com.example.muirsuus.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class information {
    @PrimaryKey(autoGenerate = true)
    Integer informId;


    String description;
    String tth;
    String gallery;

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
