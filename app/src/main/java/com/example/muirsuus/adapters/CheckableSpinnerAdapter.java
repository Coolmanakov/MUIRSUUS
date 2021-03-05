package com.example.muirsuus.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.muirsuus.R;
import com.example.muirsuus.classes.LiveDataList;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CheckableSpinnerAdapter extends ArrayAdapter<String> {
    public List<String> itemList;
    public LiveDataList<String> checkedItemList;
    private final Context context;
    private final String spinnerTitle;

    public CheckableSpinnerAdapter(
            Context context,
            List<String> itemList,
            LiveDataList<String> checkedItemList
    ) {
        this(context, itemList, checkedItemList, "Выбрать");
    }

    public CheckableSpinnerAdapter(
            Context context,
            List<String> itemList,
            LiveDataList<String> checkedItemList,
            String spinnerTitle
    ) {
        super(context, 0, itemList);
        this.context = context;
        this.spinnerTitle = spinnerTitle;
        this.itemList = itemList;
        this.checkedItemList = checkedItemList;
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

    private View getViewWithPositionCheck(
            int position,
            View convertView,
            @NotNull ViewGroup parent,
            boolean isDroppedDown
    ) {
        final CheckboxViewHolder checkboxViewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.checkable_spinner_item, parent, false);
            checkboxViewHolder = new CheckboxViewHolder(
                    convertView.findViewById(R.id.checkable_item_text),
                    convertView.findViewById(R.id.checkable_item_checkbox));

            convertView.setTag(checkboxViewHolder);
        } else
            checkboxViewHolder = (CheckboxViewHolder) convertView.getTag();

        if (position == 0) {
            checkboxViewHolder.mCheckBox.setVisibility(View.GONE);
            if (isDroppedDown)
                checkboxViewHolder.mTextView.setVisibility(View.GONE);
            else {
                checkboxViewHolder.mTextView.setText(spinnerTitle);
//                checkboxViewHolder.mTextView.setBackgroundResource(R.color.colorPrimary);
                checkboxViewHolder.mTextView.setGravity(Gravity.CENTER);
            }
        } else {
            String currentItem = itemList.get(position - 1);

            checkboxViewHolder.mTextView.setVisibility(View.VISIBLE);
            checkboxViewHolder.mTextView.setText(currentItem);
            checkboxViewHolder.mTextView.setOnClickListener(unusedView ->
                    checkboxViewHolder.mCheckBox.toggle());

            checkboxViewHolder.mCheckBox.setVisibility(View.VISIBLE);
            checkboxViewHolder.mCheckBox.setOnCheckedChangeListener(null);
            checkboxViewHolder.mCheckBox.setChecked(
                    checkedItemList.getValue().contains(currentItem));
            checkboxViewHolder.mCheckBox.setOnCheckedChangeListener((unusedView, isChecked) -> {
                if (checkedItemList.getValue() != null) {
                    if (isChecked)
                        checkedItemList.add(currentItem);
                    else
                        checkedItemList.remove(currentItem);
                }
            });
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

    private static class CheckboxViewHolder {
        private final TextView mTextView;
        private final CheckBox mCheckBox;

        private CheckboxViewHolder(TextView textView, CheckBox checkBox) {
            mTextView = textView;
            mCheckBox = checkBox;
        }
    }
}
