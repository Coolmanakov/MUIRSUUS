package com.example.muirsuus.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.classes.CardClass;
import com.example.muirsuus.IRecyclerViewClickListener;
import com.example.muirsuus.R;

import java.util.List;




public class StartAdapter extends RecyclerView.Adapter<StartAdapter.ViewHolder> {


    private List<CardClass> mLinks;
    IRecyclerViewClickListener clickListener;

    public StartAdapter(List<CardClass> mLinks){
        this.mLinks = mLinks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.listitem_layout,viewGroup,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(mLinks.get(position).getTitle());
        holder.image.setImageResource(mLinks.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView image;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.image);
            text = (TextView)itemView.findViewById(R.id.item_title);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            clickListener.onClick(v,getAdapterPosition());
        }

    }


}
