
package com.example.muirsuus.information_database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SectionAndSubsection {
    @Embedded
    public section section;
    @Relation(
            parentColumn = "id",
            entityColumn = "secId")
    public List<subsection> subsection;


    public List<subsection> getSubsection() {
        return subsection;
    }
}