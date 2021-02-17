package com.example.muirsuus.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.example.muirsuus.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * List adapter for spinner
 * Only one item can be selected
 */
public class CommonSpinnerAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> itemList;
    @StringRes
    private final int spinnerTitle;

    private static class ViewHolder {
        private final TextView mTextView;

        private ViewHolder(TextView textView) {
            mTextView = textView;
        }
    }

    public CommonSpinnerAdapter(Context context, List<String> itemList) {
        this(context, itemList, R.string.spinner_header);
    }

    public CommonSpinnerAdapter(Context context, List<String> itemList, @StringRes int spinnerTitle) {
        super(context, 0, itemList);
        this.context = context;
        this.itemList = itemList;
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
        final CommonSpinnerAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.common_spinner_item,
                    parent,
                    false);
            viewHolder = new CommonSpinnerAdapter.ViewHolder(convertView.findViewById(R.id.text));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommonSpinnerAdapter.ViewHolder) convertView.getTag();
        }

        if (position < 1) {
            if (isDroppedDown) {
                viewHolder.mTextView.setVisibility(View.GONE);
            } else {
                viewHolder.mTextView.setText(spinnerTitle);
                viewHolder.mTextView.setTextColor(Color.GRAY);
            }
        } else {
            String currentItem = itemList.get(position - 1);

            viewHolder.mTextView.setVisibility(View.VISIBLE);
            viewHolder.mTextView.setText(currentItem);
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
