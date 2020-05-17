package com.example.muirsuus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.muirsuus.R;
import com.example.muirsuus.classes.HackyViewPager;

import static com.example.muirsuus.classes.IndependentMethods.loadImageFromData;

public class FullSizeAdapter extends PagerAdapter {

    Context context;
    String[] images;
    LayoutInflater layoutInflater;

    public FullSizeAdapter(Context context, String[] images){
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.full_item, null);

        ImageView imageView = (ImageView)v.findViewById(R.id.img);
        //Glide.with(context).load(images[position]).apply(new RequestOptions().centerInside()).into(imageView);
        loadImageFromData(images[position],imageView);
        HackyViewPager vp = (HackyViewPager)container;
        vp.addView(v,0);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        HackyViewPager viewPager = (HackyViewPager)container;
        View v = (View)object;
        viewPager.removeView(v);
    }
}