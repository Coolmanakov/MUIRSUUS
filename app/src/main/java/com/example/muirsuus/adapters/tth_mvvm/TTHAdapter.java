package com.example.muirsuus.adapters.tth_mvvm;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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
import com.example.muirsuus.classes.CardClass;
import com.example.muirsuus.databinding.ItemTthBinding;

import java.util.List;

public class TTHAdapter extends RecyclerView.Adapter<TTHAdapter.MyViewHolder> {

    private List<CardClass> mLinks;
    private  OnItemClickListener mListener; //обычный слушатель нажатия
    private  OnLongItemClickListener onLongClickListener;//слушатель долгого нажатия







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
        
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTthBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_tth, parent,false);

        return new MyViewHolder(binding,mListener,onLongClickListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TthViewModel tthViewModel = new TthViewModel(mLinks.get(position).title, mLinks.get(position).title);
        holder.binding.setAdapter(tthViewModel);

// проверяем есть ли фото в БД для данного элемента recyclerview
        if( mLinks.get(position).getPhoto() != null ){
            loadImageFromData(mLinks.get(position).getPhoto(), holder.binding.tthImage);
            Log.d("mLog", "BIND VIEW HOLDER in TTH ADAPTER: photo for   " + mLinks.get(position).getTitle() + " is  " + mLinks.get(position).getPhoto());
        }
        else {
            holder.binding.tthImage.setImageResource(mLinks.get(position).getImage());
            Log.d("mLog", "BIND VIEW HOLDER in TTH ADAPTER: no photo for    " + mLinks.get(position).getTitle());
        }
    }




    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder  {
        ItemTthBinding binding;




        public MyViewHolder(@NonNull ItemTthBinding binding, final OnItemClickListener listener, final OnLongItemClickListener longClickListener) {
            super(binding.getRoot());
            this.binding = binding;


            binding.view.setOnClickListener(new View.OnClickListener() {
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
            binding.view.setOnLongClickListener(new View.OnLongClickListener() {
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
        Log.d("mLog", "TTHADAPTER removeByID:   ");
        return  id;
    }
    public int findById(int position) {
        int id = mLinks.get(position).getId();
        return  id;
    }

    public static void loadImageFromData(String namePhoto, ImageView imageView1) {
        /*String path = Environment.getExternalStorageState();
        String imagePath = path + "/AudioArmy/PhotoForDB/"+namePhoto+".jpg";
        imageView1.setImageURI(Uri.parse(imagePath));*/
    }


}
