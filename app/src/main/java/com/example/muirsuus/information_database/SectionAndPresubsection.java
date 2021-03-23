package com.example.muirsuus.information_database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SectionAndPresubsection {
    @Embedded
    public section section;
    @Relation(
            parentColumn = "id",
            entityColumn = "secId")
    public List<presubsection> presubsections;

    public List<presubsection> getPresubsections() {
        return presubsections;
    }
}
