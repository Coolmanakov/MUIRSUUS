package com.example.muirsuus.adapters;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.DataBaseHelper;
import com.example.muirsuus.classes.CardClass;
import com.example.muirsuus.R;

import java.util.List;

public class TTHAdapter extends RecyclerView.Adapter<TTHAdapter.MyViewHolder> {

    private List<CardClass> mLinks;
    private  OnItemClickListener mListener; //обычный слушатель нажатия
    private  OnLongItemClickListener onLongClickListener;//слушатель долгого нажатия
    private int id;
    DataBaseHelper dataBaseHelper;




    public TTHAdapter(List<CardClass> mLinks){
        this.mLinks = mLinks;


    }
    //-------------------методы и интерфейсы слушателей--------------
    public  interface  OnItemClickListener{
        void onItemClick(int position);
    }

    public  interface  OnLongItemClickListener{
        void onLongItemClick(int position);
    }
    public void  SetOnItemClickListener(OnItemClickListener listener){

        mListener = listener;
    }
    public void  SetOnLongItemClickListener(OnLongItemClickListener listener){

        onLongClickListener = listener;
    }



    //-------------------методы и интерфейсы слушателей--------------



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup viewGroup;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_tth,parent,false);
        MyViewHolder vh = new MyViewHolder(v,mListener,onLongClickListener);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text.setText(mLinks.get(position).getTitle());
        holder.image.setImageResource(mLinks.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder  {
        public ImageView image;
        public TextView text;


        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener, final OnLongItemClickListener longClickListener) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.tth_image);
            text = (TextView)itemView.findViewById(R.id.tth_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(longClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            longClickListener.onLongItemClick(position);
                        }

                    }
                    return true;
                }
            });

        }
    }
    public int removeById(int position) {
        int id = mLinks.get(position).getId();
        mLinks.remove(position);
        notifyDataSetChanged();
        Log.d("mLog", "deleted item with id = " + id);
        return  id;
    }
    public int findById(int position) {
        int id = mLinks.get(position).getId();
        return  id;
    }

}
