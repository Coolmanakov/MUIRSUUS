package com.example.muirsuus.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.muirsuus.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * List adapter for spinner
 * Only one item can be selected
 */
public class CommonSpinnerAdapter extends ArrayAdapter<String> {
    public final List<String> itemList;
    private final Context context;
    private final String spinnerTitle;

    private static class CommonViewHolder {
        private final TextView mTextView;

        private CommonViewHolder(TextView textView) {
            mTextView = textView;
        }
    }

    public CommonSpinnerAdapter(Context context, List<String> itemList) {
        this(context, itemList, "Выбрать");
    }

    public CommonSpinnerAdapter(Context context, List<String> itemList, String spinnerTitle) {
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
        final CommonViewHolder commonViewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.common_spinner_item, parent, false);
            commonViewHolder = new CommonViewHolder(convertView.findViewById(R.id.common_item_text));
            convertView.setTag(commonViewHolder);
        } else {
            commonViewHolder = (CommonViewHolder) convertView.getTag();
        }

        if (position == 0) {
            if (isDroppedDown)
                commonViewHolder.mTextView.setVisibility(View.GONE);
            else
                commonViewHolder.mTextView.setText(spinnerTitle);
        } else {
            String currentItem = itemList.get(position - 1);

            commonViewHolder.mTextView.setVisibility(View.VISIBLE);
            commonViewHolder.mTextView.setText(currentItem);
        }

        if (!isDroppedDown) {
//            commonViewHolder.mTextView.setBackgroundResource(R.color.colorPrimary);
            commonViewHolder.mTextView.setTextColor(Color.BLACK);
            commonViewHolder.mTextView.setGravity(Gravity.CENTER);
        }

        return convertView;
    }

    @Override
    public String getItem(int position) {
        if (position < 1) return null;
        return itemList.get(position - 1);
    }

    @Override
    public int getCount() {
        if (itemList == null) return 0;
        return itemList.size() + 1;
    }
}
