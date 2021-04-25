package com.example.muirsuus.classes.sortedList;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
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

public class SortedAdapter extends RecyclerView.Adapter<SortedAdapter.SortedHolder> {
    private final Context context;
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

    public SortedAdapter(Comparator<books> mComparator, Context context) {
        this.mComparator = mComparator;
        this.context = context;
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

    @NonNull
    @Override
    public SortedAdapter.SortedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PdfItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.pdf_item, parent, false);
        return new SortedHolder(binding, context);
    }

    @Override
    public void onBindViewHolder(@NonNull SortedAdapter.SortedHolder holder, int position) {
        final books book = sortedList.get(position);
        try {
            holder.bind(book);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public static class SortedHolder extends RecyclerView.ViewHolder {
        private final PdfItemBinding binding;
        private Context context;

        public SortedHolder(@NonNull PdfItemBinding binding, Context context) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public static void loadImageFromAssets(String namePhoto, ImageView imageView1, Context context) throws IOException {
            AssetManager manager = context.getAssets();
            InputStream inputStream = manager.open("books/books_photo/" + namePhoto + ".png");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView1.setImageBitmap(bitmap);
        }

        public void bind(books book) throws IOException {
            loadImageFromAssets(book.getBook_photo(), binding.pdfImage, context);
            binding.bookName.setText(book.getBook_name());
        }
    }
}
