package com.example.muirsuus.classes;


public class Army {

    private int id;
    private CharSequence section;
    private String subsection;
    private String point;
    private String photo_ids;
    private String description;
    private String tth;










    public Army(){
    }

    public Army(CharSequence section, String subsection, String point) {
        this.section = section;
        this.subsection = subsection;
        this.point = point;
    }
    public Army(String title, String subsection, String description, String tth) {
        this.section = title;
        this.subsection = subsection;
        this.description = description;
        this.tth = tth;
    }




    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String get_description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public CharSequence getSection() {
        return section;
    }

    public void setSection(String section) {

        this.section = section;
    }

    public String get_photo_subsection() {
        return photo_ids;
    }

    public void set_photo_subsection(String photo_ids) {
        this.photo_ids = photo_ids;
    }

    public String get_photo_point() {
        return point;
    }

    public void set_photo_point(String point) {
        this.point = point;
    }

    public void setTTH(String tth) {
        this.tth = tth;
    }

    public String getTTH() {
        return tth;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPoint() {
        return point;
    }




}