package com.example.muirsuus.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class section {
    @PrimaryKey(autoGenerate = true)
    Integer id;

    String section;
    String sec_photo;
    String sec_description;
    // String sec_gallery;

    public section(Integer id, String section, String sec_photo, String sec_description) {
        this.id = id;
        this.section = section;
        this.sec_photo = sec_photo;
        this.sec_description = sec_description;
        //this.sec_gallery = sec_gallery;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSectionPhoto() {
        return sec_photo;
    }

    public void setSectionPhoto(String sec_photo) {
        this.sec_photo = sec_photo;
    }

    public String getSectionDescription() {
        return sec_description;
    }

    public void setSectionDescription(String sec_description) {
        this.sec_description = sec_description;
    }

   /* public String getSec_gallery() {
        return sec_gallery;
    }

    public void setSec_gallery(String sec_gallery) {
        this.sec_gallery = sec_gallery;
    }*/
}
