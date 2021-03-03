package com.example.muirsuus.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.R;
import com.example.muirsuus.classes.IRecyclerViewClickListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private final List<String> mLinks;
    private final Context context;
    IRecyclerViewClickListener clickListener;

    public GalleryAdapter(List<String> mLinks, IRecyclerViewClickListener clickListener, Context context) {
        this.mLinks = mLinks;
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.single_item_photo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public static void loadImageFromAssets(String namePhoto, ImageView imageView1, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream inputStream = manager.open("images/" + namePhoto + ".jpg");
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        imageView1.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView galleryImage;
        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            galleryImage = (ImageView) itemView.findViewById(R.id.All_Image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            loadImageFromAssets(mLinks.get(position), holder.galleryImage, context);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
