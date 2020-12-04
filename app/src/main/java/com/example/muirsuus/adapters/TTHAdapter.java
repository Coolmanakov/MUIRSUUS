package com.example.muirsuus.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.R;
import com.example.muirsuus.databinding.ItemTthBinding;
import com.example.muirsuus.information_ui.TthViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TTHAdapter extends RecyclerView.Adapter<TTHAdapter.MyViewHolder> {


    private final ListItemClickListener clickListener;
    private final Context context;
    private List<String> titles;
    private List<String> descriptions;
    private List<String> images;
    private static final String LOG_TAG = "mLog " + TTHAdapter.class.getCanonicalName();


    public TTHAdapter(Context context, ListItemClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }



    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public static void loadImageFromAssets(String namePhoto, ImageView imageView1, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream inputStream = manager.open("images/" + namePhoto + ".png");
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        imageView1.setImageBitmap(bitmap);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTthBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_tth, parent, false);

        return new MyViewHolder(binding, clickListener, context);

    }


    @Override
    public int getItemCount() {
        return titles.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            holder.bind(titles.get(position), descriptions.get(position), images.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface ListItemClickListener {
        void OnItemClickListener(int clickItemIndex);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemTthBinding binding;
        private final ListItemClickListener clickListener;
        private final Context context;


        public MyViewHolder(@NonNull ItemTthBinding binding, ListItemClickListener clickListener, Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.clickListener = clickListener;
            binding.getRoot().setOnClickListener(this);
            this.context = context;
        }

        private void bind(String title, String description, String image) throws IOException {
            // put title into tthviewmodel
            TthViewModel tthViewModel = new TthViewModel();
            if (title != null) {
                tthViewModel.setTitle(title);
            } else {
                Log.d(LOG_TAG, "title is null");
            }

            if (description != null) {
                tthViewModel.setDescription(description);
            } else {
                Log.d(LOG_TAG, "description is null");
            }

            if (image != null) {
                loadImageFromAssets(image, binding.tthImage, context);
            } else {
                Log.d(LOG_TAG, "image is null");
            }

            binding.setData(tthViewModel); // set tthviewmodel into layout
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            clickListener.OnItemClickListener(clickedPosition);
        }
    }


}
