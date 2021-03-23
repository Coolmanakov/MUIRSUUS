package com.example.muirsuus.information_database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PreSubsectionAndPoint {
    @Embedded
    public presubsection presubsection;
    @Relation(
            parentColumn = "preId",
            entityColumn = "subsectionId")
    public List<point> points;

    public List<point> getPoints() {
        return points;
    }
}
