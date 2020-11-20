package com.example.muirsuus.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "point")
public class point {
    @PrimaryKey(autoGenerate = true)
    Integer pointId;

    Integer subsectionId;
    String point;
    String point_photo;
    String point_description;
    //String point_gallery;

    public point(Integer pointId, Integer subsectionId, String point, String point_photo, String point_description) {
        this.pointId = pointId;
        this.subsectionId = subsectionId;
        this.point = point;
        this.point_photo = point_photo;
        this.point_description = point_description;
        //this.point_gallery = point_gallery;
    }

    public Integer getPointId() {
        return pointId;
    }

    public Integer getSubsectionId() {
        return subsectionId;
    }

    public String getPoint() {
        return point;
    }

    public String getPoint_photo() {
        return point_photo;
    }

    public String getPoint_description() {
        return point_description;
    }


}
