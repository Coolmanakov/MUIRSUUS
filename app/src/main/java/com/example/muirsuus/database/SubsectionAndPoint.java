package com.example.muirsuus.database;

import androidx.room.Embedded;
import androidx.room.Relation;

public class SubsectionAndPoint {
    @Embedded
    public subsection subsection;
    @Relation(
            parentColumn = "subId",
            entityColumn = "subsectionId")
    public point point;


    public point getPoint() {
        return point;
    }
}
