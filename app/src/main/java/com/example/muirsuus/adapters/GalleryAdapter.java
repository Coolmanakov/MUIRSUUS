package com.example.muirsuus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.IRecyclerViewClickListener;
import com.example.muirsuus.R;

import java.util.List;

import static com.example.muirsuus.classes.IndependentMethods.loadImageFromData;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private List<String> mLinks;
    private Context mContext;
    IRecyclerViewClickListener clickListener;

    public GalleryAdapter(List<String> mLinks, Context mContext) {
        this.mLinks = mLinks;
        this.mContext = mContext;
        //this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.single_item_photo,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        loadImageFromData(mLinks.get(position),holder.galleryImage);

    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView galleryImage;
        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            galleryImage= (ImageView)itemView.findViewById(R.id.All_Image);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v,getAdapterPosition());
        }
    }
}
