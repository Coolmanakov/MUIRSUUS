package com.example.muirsuus.main_navigation.lit;

import androidx.lifecycle.ViewModel;

public class LitViewModel extends ViewModel {
    private String fileName;
    private String filePath;

    public LitViewModel(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}