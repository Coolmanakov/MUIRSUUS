package com.example.muirsuus.information_database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subsection")
public class subsection {
    @PrimaryKey(autoGenerate = true)
    Integer subId;

    Integer secId;
    String subsection;
    String sub_photo;
    String sub_description;
    //String sub_gallery;

    public subsection(Integer secId, Integer subId, String subsection, String sub_photo, String sub_description) {
        this.secId = secId;
        this.subId = subId;
        this.subsection = subsection;
        this.sub_photo = sub_photo;
        this.sub_description = sub_description;
        //this.sub_gallery = sub_gallery;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public Integer getSecId() {
        return secId;
    }

    public void setSecId(Integer secId) {
        this.secId = secId;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getSub_photo() {
        return sub_photo;
    }

    public void setSub_photo(String sub_photo) {
        this.sub_photo = sub_photo;
    }

    public String getSub_description() {
        return sub_description;
    }

    public void setSub_description(String sub_description) {
        this.sub_description = sub_description;
    }

    /*public String getSub_gallery() {
        return sub_gallery;
    }

    public void setSub_gallery(String sub_gallery) {
        this.sub_gallery = sub_gallery;
    }*/
}
