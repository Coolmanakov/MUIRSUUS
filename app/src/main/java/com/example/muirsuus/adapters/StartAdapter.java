package com.example.muirsuus.adapters;

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

    public StartAdapter(List<CardClass> mLinks, ListItemClickListener clickListener) {
        this.mLinks = mLinks;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.listitem_layout, viewGroup, false);

        return new ViewHolder(binding.getRoot(), clickListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        binding.itemTitle.setText(mLinks.get(position).getTitle());
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
