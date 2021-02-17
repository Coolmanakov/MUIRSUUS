
package com.example.muirsuus.information_database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SubsectionAndPoint {
    @Embedded
    public subsection subsection;
    @Relation(
            parentColumn = "subId",
            entityColumn = "subsectionId")
    public List<point> point;


    public List<point> getPoint() {
        return point;
    }
}