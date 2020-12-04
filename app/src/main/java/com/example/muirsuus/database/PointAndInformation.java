package com.example.muirsuus.database;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PointAndInformation {
    @Embedded
    public point point;
    @Relation(
            parentColumn = "pointId",
            entityColumn = "informId")
    public information information;

    public information getInformation() {
        return information;
    }
}
