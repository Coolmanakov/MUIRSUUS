package com.example.muirsuus.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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

import java.util.List;

public class TTHAdapter extends RecyclerView.Adapter<TTHAdapter.MyViewHolder> {


    private final ListItemClickListener clickListener;
    private final Context context;
    private List<String> titles;
    private List<String> descriptions;
    private List<String> images;


    public TTHAdapter(Context context, ListItemClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public static void loadImageFromData(String namePhoto, ImageView imageView1) {
        String path = Environment.getExternalStorageState();
        String imagePath = path + "/AudioArmy/PhotoForDB/" + namePhoto + ".jpg";
        imageView1.setImageURI(Uri.parse(imagePath));
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

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTthBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_tth, parent, false);

        return new MyViewHolder(binding, clickListener);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.bind(titles.get(position), descriptions.get(position), images.get(position));
    }


    @Override
    public int getItemCount() {
        return titles.size();
    }

    public interface ListItemClickListener {
        void OnItemClickListener(int clickItemIndex);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemTthBinding binding;
        private final ListItemClickListener clickListener;


        public MyViewHolder(@NonNull ItemTthBinding binding, ListItemClickListener clickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.clickListener = clickListener;
            binding.getRoot().setOnClickListener(this);
        }

        private void bind(String title, String description, String image) {
            TthViewModel tthViewModel = new TthViewModel();
            tthViewModel.setTitle(title);// put title into tthviewmodel
            tthViewModel.setDescription(description);
            //loadImageFromData(image, binding.tthImage);

            //TODO realize method loadImageFromData()

            binding.setData(tthViewModel); // set tthviewmodel into layout
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            clickListener.OnItemClickListener(clickedPosition);
        }
    }


}
