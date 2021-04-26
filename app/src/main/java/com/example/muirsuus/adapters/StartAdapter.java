package com.example.muirsuus.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.R;
import com.example.muirsuus.classes.CardClass;
import com.example.muirsuus.databinding.ListitemLayoutBinding;

import java.util.List;


public class StartAdapter extends RecyclerView.Adapter<StartAdapter.ViewHolder> {


    private final List<CardClass> mLinks;
    private final ListItemClickListener clickListener;
    private ListitemLayoutBinding binding;
    private final Activity activity;

    public StartAdapter(List<CardClass> mLinks, ListItemClickListener clickListener, Activity activity) {
        this.mLinks = mLinks;
        this.clickListener = clickListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.listitem_layout, viewGroup, false);

        return new ViewHolder(binding.getRoot(), clickListener);
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        binding.itemTitle.setText(mLinks.get(position).getTitle());
        /*Bitmap bitmapOriginal = BitmapFactory.decodeResource(resources, mLinks.get(position).getImage());
        Bitmap imageBitmap = Bitmap.createScaledBitmap(bitmapOriginal, bitmapOriginal.getWidth()/2, bitmapOriginal.getHeight()/2, false);
        binding.image.setImageBitmap(imageBitmap);*/
        binding.image.setImageResource(mLinks.get(position).getImage());
    }

    public interface ListItemClickListener {
        void OnItemClickListener(int clickItemIndex);
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ListItemClickListener clickListener;

        public ViewHolder(View itemView, ListItemClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            binding.getRoot().setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            clickListener.OnItemClickListener(clickedPosition);
        }

    }


}
