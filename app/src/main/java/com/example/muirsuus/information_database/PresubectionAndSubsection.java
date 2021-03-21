package com.example.muirsuus.information_database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PresubectionAndSubsection {
    @Embedded
    public presubsection presubsection;
    @Relation(
            parentColumn = "preId",
            entityColumn = "secId")
    public List<subsection> subsection;

    public List<subsection> getSubsection() {
        return subsection;
    }
}
