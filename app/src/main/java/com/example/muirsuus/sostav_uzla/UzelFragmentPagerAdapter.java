package com.example.muirsuus.sostav_uzla;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class UzelFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 10;
    private String tabTitles[] = new String[] {"ЦКО", "ПРЦ", "ПДРЦ","ТФЦ","ТГЦ","ЦАСУ","ГрМСМП","ГТО","ЦЭС","ПУУС"};
    private Context context;

    public UzelFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return Sostav_tabs.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
