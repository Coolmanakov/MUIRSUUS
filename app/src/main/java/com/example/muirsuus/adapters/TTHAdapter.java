package com.example.muirsuus.adapters;

import android.graphics.Color;
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
import com.example.muirsuus.R;

import java.util.List;

public class TTHAdapter extends RecyclerView.Adapter<TTHAdapter.MyViewHolder> {

    private List<CardClass> mLinks;
    private  OnTthListener mOnTthListener;
    boolean[] selects = new boolean[20];

    public TTHAdapter(List<CardClass> mLinks, OnTthListener onTthListener){
        this.mLinks = mLinks;
        this.mOnTthListener = onTthListener;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup viewGroup;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_tth,parent,false);
        MyViewHolder vh = new MyViewHolder(v, mOnTthListener);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text.setText(mLinks.get(position).getTitle());
        holder.image.setImageResource(mLinks.get(position).getImage());


        if(selects[position]){
            holder.itemView.setBackgroundColor(Color.LTGRAY);}
        else{
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);}
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView image;
        public TextView text;
        OnTthListener onTthListener;

        public MyViewHolder(@NonNull View itemView, OnTthListener onTthListener) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.tth_image);
            text = (TextView)itemView.findViewById(R.id.tth_text);
            this.onTthListener = onTthListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onTthListener.onTthCLick(getAdapterPosition());
        }
    }

    public interface OnTthListener{
        void onTthCLick(int position);

    }

}
