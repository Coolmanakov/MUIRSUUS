package com.example.muirsuus.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder> {

    List<String> equips = new ArrayList<>();

    public static class EquipmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView equip_name;

        public EquipmentViewHolder(@NonNull View itemView) {
            super(itemView);
            equip_name = itemView.findViewById(R.id.user_name_text_view);
        }

        public void bind(String equip){
            equip_name.setText(equip);
        }
    }

    @NonNull
    @Override
    public EquipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.equip_item,parent,false);
        System.out.println("created");
        return new EquipmentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentViewHolder holder, int position) {
        Log.d("my_log", equips.get(position));
        holder.bind(equips.get(position));
    }

    @Override
    public int getItemCount() {
        return equips.size();
    }

    public void setItems(List<String> equips) {
        this.equips.addAll(equips);
        Log.d("myLog", String.valueOf(equips.size()));
    }

    public void clearItems() {
        equips.clear();
        notifyDataSetChanged();
    }

}
