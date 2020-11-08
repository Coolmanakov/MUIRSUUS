package com.example.muirsuus.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_ex")
public class table_ex {
    @PrimaryKey(autoGenerate = true)
    int _id;

    @ColumnInfo(name = "section")
    String section;

    @ColumnInfo(name = "subsection")
    String subsection;

    @ColumnInfo(name = "point")
    String point;

    @ColumnInfo(name = "photo_ids")
    String photo_ids;

    @ColumnInfo(name = "tth")
    String tth;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "subsection_photo")
    String subsection_photo;

    @ColumnInfo(name = "point_photo")
    String point_photo;


    public table_ex(int id, String section, String subsection, String point, String photo_ids, String tth, String description, String subsection_photo, String point_photo) {
        this._id = id;
        this.section = section;
        this.subsection = subsection;
        this.point = point;
        this.photo_ids = photo_ids;
        this.tth = tth;
        this.description = description;
        this.subsection_photo = subsection_photo;
        this.point_photo = point_photo;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPhoto_ids() {
        return photo_ids;
    }

    public void setPhoto_ids(String photo_ids) {
        this.photo_ids = photo_ids;
    }

    public String getTth() {
        return tth;
    }

    public void setTth(String tth) {
        this.tth = tth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubsection_photo() {
        return subsection_photo;
    }

    public void setSubsection_photo(String subsection_photo) {
        this.subsection_photo = subsection_photo;
    }

    public String getPoint_photo() {
        return point_photo;
    }

    public void setPoint_photo(String point_photo) {
        this.point_photo = point_photo;
    }
}