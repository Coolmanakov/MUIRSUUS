package com.example.muirsuus.classes;


public class Army {

    private int id;
    private String title;
    private String subtitle;
    private String image;
    private String description;
    private String allImage;
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAllImage() {
        return allImage;
    }

    public void setAllImage(String allImage) {
        this.allImage = allImage;
    }

    public Army(){
    }

    public Army(String title, String subtitle, String image) {
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
    }
    public Army(String title, String subtitle, String image, String description, String allImage) {
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
        this.description = description;
        this.allImage = allImage;
    }
    public Army(String title, String subtitle, String image, String description, String allImage,String groupName) {
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
        this.description = description;
        this.allImage = allImage;
        this.groupName = groupName;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }
}