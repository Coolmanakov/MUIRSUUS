package com.example.muirsuus.information_database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "presubsection")
public class presubsection {
    @PrimaryKey(autoGenerate = true)
    Integer preId;

    Integer secId;
    String presubsection;
    String presub_photo;

    public presubsection(Integer secId, String presubsection, String presub_photo) {
        this.secId = secId;
        this.presubsection = presubsection;
        this.presub_photo = presub_photo;
    }

    public Integer getSecId() {
        return secId;
    }

    public void setSecId(Integer secId) {
        this.secId = secId;
    }

    public String getPresubsection() {
        return presubsection;
    }

    public void setPresubsection(String presubsection) {
        this.presubsection = presubsection;
    }

    public String getPresub_photo() {
        return presub_photo;
    }

    public void setPresub_photo(String presub_photo) {
        this.presub_photo = presub_photo;
    }
}
