package com.example.muirsuus.information_ui;


import androidx.lifecycle.ViewModel;

public class TthViewModel extends ViewModel {
    private String description;
    private String title;
    private String image;


    public TthViewModel() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
