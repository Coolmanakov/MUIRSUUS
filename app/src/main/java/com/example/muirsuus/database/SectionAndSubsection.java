package com.example.muirsuus.database;

import androidx.room.Embedded;
import androidx.room.Relation;

public class SectionAndSubsection {
    @Embedded
    public section section;
    @Relation(
            parentColumn = "id",
            entityColumn = "secId")
    public subsection subsection;


    public subsection getSubsection() {
        return subsection;
    }
}

