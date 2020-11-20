package com.example.muirsuus.information_ui;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.muirsuus.information_ui.information.InformationViewModel;
import com.example.muirsuus.information_ui.point.PointViewModel;
import com.example.muirsuus.information_ui.section.SectionViewModel;
import com.example.muirsuus.information_ui.subsection.SubsectionViewModel;

/*
    Фабрика ViewModel, которая отвественна за то, что будет получена верная ViewModel
*/
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context context;
    private String subsection;
    private String section;
    private final String LOG_TAG = "mLog " + ViewModelFactory.class.getCanonicalName();
    private String point;


    public ViewModelFactory(Context context) {
        this.context = context;

    }


    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SectionViewModel.class)) {
            Log.d(LOG_TAG, "getting model SectionViewModel");
            return (T) new SectionViewModel(context);

        } else if (modelClass.isAssignableFrom(SubsectionViewModel.class)) {
            Log.d(LOG_TAG, "getting model  SubsectionViewModel");
            return (T) new SubsectionViewModel(context, section);

        } else if (modelClass.isAssignableFrom(PointViewModel.class)) {
            Log.d(LOG_TAG, "getting model PointViewModel");
            return (T) new PointViewModel(context, subsection);

        } else if (modelClass.isAssignableFrom(InformationViewModel.class)) {
            Log.d(LOG_TAG, "getting model InformationViewModel");
            return (T) new InformationViewModel(context, point);
        }
        return null;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
