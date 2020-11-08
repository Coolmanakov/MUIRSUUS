package com.example.muirsuus.information_ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.muirsuus.information_ui.point.PointViewModel;
import com.example.muirsuus.information_ui.section.SectionViewModel;
import com.example.muirsuus.information_ui.subsection.SubsectionViewModel;

import java.io.IOException;

/*
    Фабрика ViewModel, которая отвественна за то, что будет получена верная ViewModel
*/
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context context;
    private String subsection;
    private String section;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SectionViewModel.class)) {
            try {
                return (T) new SectionViewModel(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (modelClass.isAssignableFrom(SubsectionViewModel.class)) {
            try {
                return (T) new SubsectionViewModel(context, section);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (modelClass.isAssignableFrom(PointViewModel.class)) {
            try {
                return (T) new PointViewModel(context, section);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
