package com.example.muirsuus.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.R;
import com.example.muirsuus.databinding.ListitemLayoutBinding;
import com.example.muirsuus.main_navigation.calculation.toplivo.FindCarFragmnetViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {
    //TODO список из БД
    private final ListItemClickListener clickListener;
    private final List<Pair<String, String>> list = new ArrayList<>();
    private final Context context;

    public GridAdapter(Context context, ListItemClickListener clickListener, ListItemClickListener clickListener1) {
        this.context = context;
        this.clickListener = clickListener1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListitemLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.listitem_layout, parent, false);
        return new MyViewHolder(binding, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //перед тем как привязывать данные проверить их на == null
        try {
            holder.bind(list.get(position).first, list.get(position).second);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface ListItemClickListener {
        void OnItemClickListener(int clickItemIndex);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ListitemLayoutBinding binding;
        private final ListItemClickListener clickListener;

        public MyViewHolder(@NonNull ListitemLayoutBinding binding, ListItemClickListener clickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.clickListener = clickListener;
        }

        public void bind(String name, String image) throws IOException {

            FindCarFragmnetViewModel viewModel = new FindCarFragmnetViewModel(name, image);
            loadImageFromAssets(image, binding.image, context);


        }

        private void loadImageFromAssets(String namePhoto, ImageView imageView1, Context context) throws IOException {
            AssetManager manager = context.getAssets();
            InputStream inputStream = manager.open("images/" + namePhoto + ".jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView1.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            clickListener.OnItemClickListener(clickedPosition);
        }

    }

}
