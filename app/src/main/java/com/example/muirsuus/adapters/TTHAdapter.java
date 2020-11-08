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
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.R;
import com.example.muirsuus.databinding.ItemTthBinding;
import com.example.muirsuus.information_ui.TthViewModel;

import java.util.List;

public class TTHAdapter extends RecyclerView.Adapter<TTHAdapter.MyViewHolder> {


    private static TthViewModel tthViewModel;
    private final ListItemClickListener clickListener;
    private LiveData<List<String>> entries;
    private Context context;


    public TTHAdapter(Context context, ListItemClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public static void loadImageFromData(String namePhoto, ImageView imageView1) {
        String path = Environment.getExternalStorageState();
        String imagePath = path + "/AudioArmy/PhotoForDB/" + namePhoto + ".jpg";
        imageView1.setImageURI(Uri.parse(imagePath));
    }

    public LiveData<List<String>> getEntries() {
        return entries;
    }

    public void setEntries(LiveData<List<String>> entries) {
        this.entries = entries;
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
        //TODO getting data from list mLinks by position
        holder.bind(entries.getValue().get(position));
    }


    @Override
    public int getItemCount() {
        return entries.getValue().size();
    }

    public interface ListItemClickListener {
        void OnItemClickListener(int clickItemIndex);
    }


    /*public int removeById(int position) {
        int id = mLinks.get(position).getId();
        mLinks.remove(position);
        notifyDataSetChanged();
        Log.d("mLog", "TTHADAPTER removeByID:   ");
        return  id;
    }*/
    /*public int findById(int position) {
        int id = mLinks.get(position).getId();
        return  id;
    }*/

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemTthBinding binding;
        private ListItemClickListener clickListener;


        public MyViewHolder(@NonNull ItemTthBinding binding, ListItemClickListener clickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.clickListener = clickListener;
            binding.getRoot().setOnClickListener(this);


        }

        private void bind(String title) {
            tthViewModel.setTitle(title);// put title into tthviewmodel
            binding.setData(tthViewModel); // set tthviewmodel into layout
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            clickListener.OnItemClickListener(clickedPosition);
        }
    }


}
