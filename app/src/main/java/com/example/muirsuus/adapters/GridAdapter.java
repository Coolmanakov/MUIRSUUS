package com.example.muirsuus.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.R;
import com.example.muirsuus.databinding.PdfItemBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {
    //TODO список из БД
    private final ListItemClickListener clickListener;
    private List<String> list = new ArrayList<>();
    private final Context context;

    public GridAdapter(Context context, ListItemClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PdfItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.pdf_item, parent, false);
        return new MyViewHolder(binding, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //перед тем как привязывать данные проверить их на == null
        StringBuffer buffer = new StringBuffer(list.get(position));
        buffer.delete(list.get(position).length() - 4, list.get(position).length()); //удаляем ".pdf" из исходной строки
        String name = buffer.substring(0); //получаем конечное название книги
        /* ------------------------------------------------------------------------------------------------------------------------------------*/
        String[] arr = name.split(" ", 2);//разбиваем строку, чтобы получить первое слово из названия книги
        arr[0] = arr[0].toLowerCase();
        if (arr[0].equals("боевое")) {
            holder.binding.pdfImage.setImageResource(R.drawable.boevoe_primenenie);
        } else if (arr[0].equals("каблирование")) {
            holder.binding.pdfImage.setImageResource(R.drawable.kablirovanie);
        } else if (arr[0].equals("основы")) {
            holder.binding.pdfImage.setImageResource(R.drawable.osnov_postroenia);
        } else if (arr[0].equals("техническая")) {
            holder.binding.pdfImage.setImageResource(R.drawable.technoc_podgotovka);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ListItemClickListener {
        void OnItemClickListener(int clickItemIndex);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final PdfItemBinding binding;
        private final ListItemClickListener clickListener;

        public MyViewHolder(@NonNull PdfItemBinding binding, ListItemClickListener clickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.clickListener = clickListener;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(String name, String image) throws IOException {

            //FindCarFragmnetViewModel viewModel = new FindCarFragmnetViewModel(name, image);
            //loadImageFromAssets(image, binding.image, context);


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
