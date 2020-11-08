package com.example.muirsuus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.muirsuus.R;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

/**
 * List adapter for spinner
 * Multiple items can be selected
 */
public class CheckableSpinnerAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> itemList;
    private final ArrayList<String> checkedItemList;

    private static class ViewHolder {
        private final TextView mTextView;
        private final CheckBox mCheckBox;

        private ViewHolder(TextView textView, CheckBox checkBox) {
            mTextView = textView;
            mCheckBox = checkBox;
        }
    }

    public CheckableSpinnerAdapter(Context context, ArrayList<String> itemList, ArrayList<String> checkedItemList) {
        super(context, 0, itemList);
        this.context = context;
        this.itemList = new ArrayList<>(itemList);
        this.checkedItemList = checkedItemList;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.checkable_spinner_item,
                    parent,
                    false);
            viewHolder = new ViewHolder(
                    convertView.findViewById(R.id.text),
                    convertView.findViewById(R.id.checkbox));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position < 1) {
            viewHolder.mCheckBox.setVisibility(View.GONE);
            viewHolder.mTextView.setText(R.string.spinner_header);
        } else {
            final int listPosition = position - 1;
            String currentItem = itemList.get(listPosition);

            viewHolder.mTextView.setText(currentItem);
            viewHolder.mCheckBox.setVisibility(View.VISIBLE);
            viewHolder.mCheckBox.setOnCheckedChangeListener(null);
            viewHolder.mCheckBox.setChecked(checkedItemList.contains(currentItem));
            viewHolder.mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    checkedItemList.add(currentItem);
                } else {
                    checkedItemList.remove(currentItem);
                }
            });
            viewHolder.mTextView.setOnClickListener(view -> viewHolder.mCheckBox.toggle());
        }

        return convertView;
    }

    @Override
    public String getItem(int position) {
        if (position < 1) {
            return null;
        } else {
            return itemList.get(position - 1);
        }
    }

    @Override
    public int getCount() {
        return itemList.size() + 1;
    }
}
