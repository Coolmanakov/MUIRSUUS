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
import androidx.recyclerview.widget.SortedList;

import com.example.muirsuus.R;
import com.example.muirsuus.databinding.PdfItemBinding;
import com.example.muirsuus.main_navigation.lit.books;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;


public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {
    private final Context context;
    private final ListItemClickListener clickListener;
    private final Comparator<books> mComparator;
    private final SortedList<books> sortedList = new SortedList<>(books.class, new SortedList.Callback<books>() {
        @Override
        public int compare(books o1, books o2) {
            return mComparator.compare(o1, o2);

        }

        @Override
        public void onChanged(int position, int count) {

            notifyItemChanged(position, count);
        }

        //Цель этого метода - определить, изменилось ли содержимое модели.
        // В SortedListиспользует это , чтобы определить ,
        // нуждается ли вызываться событие изменения
        @Override
        public boolean areContentsTheSame(books oldItem, books newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(books item1, books item2) {
            return item1.getBook_name() == item2.getBook_name();
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);

        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }
    });
    /*-----------------------------------------Поля класса---------------------------------------------------------*/
    private List<books> books;
    /*-----------------------------------------Поля класса---------------------------------------------------------*/

    public BooksAdapter(Context context, ListItemClickListener clickListener, List<books> books, Comparator<books> mComparator) {
        this.context = context;
        this.books = books;
        this.clickListener = clickListener;
        this.mComparator = mComparator;
    }

    public static void loadImageFromAssets(String namePhoto, ImageView imageView1, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream inputStream = manager.open("books/books_photo/" + namePhoto + ".png");
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        imageView1.setImageBitmap(bitmap);
    }

    public void add(books model) {
        sortedList.add(model);
    }

    public void remove(books model) {
        sortedList.remove(model);
    }

    public void add(List<books> models) {
        sortedList.addAll(models);
    }

    public void replaceAll(List<books> models) {
        sortedList.beginBatchedUpdates();
        for (int i = sortedList.size() - 1; i >= 0; i--) {
            final books model = sortedList.get(i);
            if (!models.contains(model)) {
                sortedList.remove(model);
            }
        }
        sortedList.addAll(models);
        sortedList.endBatchedUpdates();
    }

    public void setBooks(List<com.example.muirsuus.main_navigation.lit.books> books) {
        this.books = books;
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
        try {
            holder.bind(position);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public interface ListItemClickListener {
        void OnItemClickListener(int clickItemIndex);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final PdfItemBinding binding;
        private final ListItemClickListener clickListener;

        public MyViewHolder(@NonNull PdfItemBinding binding, ListItemClickListener listener) {
            super(binding.getRoot());
            binding.getRoot().setOnClickListener(this);
            this.binding = binding;
            this.clickListener = listener;
        }

        private void bind(int position) throws IOException {
            loadImageFromAssets(books.get(position).getBook_photo(), binding.pdfImage, context);
            binding.bookName.setText(books.get(position).getBook_name());
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            clickListener.OnItemClickListener(clickedPosition);
        }
    }
}
