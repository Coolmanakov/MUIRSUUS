package com.example.muirsuus.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.example.muirsuus.R;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * List adapter for spinner
 * Multiple items can be selected
 * Requires second list for checked items
 */
public class CheckableSpinnerAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> itemList;
    @StringRes private final int spinnerTitle;
    private final List<String> checkedItemList;

    private static class ViewHolder {
        private final TextView mTextView;
        private final CheckBox mCheckBox;

        private ViewHolder(TextView textView, CheckBox checkBox) {
            mTextView = textView;
            mCheckBox = checkBox;
        }
    }

    public CheckableSpinnerAdapter(Context context, List<String> itemList, List<String> checkedItemList) {
        this(context, itemList, checkedItemList, R.string.spinner_header);
    }

    public CheckableSpinnerAdapter(Context context, List<String> itemList, List<String> checkedItemList, @StringRes int spinnerTitle) {
        super(context, 0, itemList);
        this.context = context;
        this.itemList = itemList;
        this.checkedItemList = checkedItemList;
        this.spinnerTitle = spinnerTitle;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
        return getViewWithPositionCheck(position, convertView, parent, true);
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        return getViewWithPositionCheck(position, convertView, parent, false);
    }

    private View getViewWithPositionCheck(int position, View convertView, @NotNull ViewGroup parent, boolean isDroppedDown) {
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
            if (isDroppedDown)
                viewHolder.mTextView.setVisibility(View.GONE);
            else {
                viewHolder.mTextView.setText(spinnerTitle);
                viewHolder.mTextView.setTextColor(Color.GRAY);
            }
        } else {
            String currentItem = itemList.get(position - 1);

            viewHolder.mTextView.setVisibility(View.VISIBLE);
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
