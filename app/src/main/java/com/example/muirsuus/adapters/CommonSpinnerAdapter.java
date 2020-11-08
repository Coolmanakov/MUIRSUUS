package com.example.muirsuus.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.List;


/**
 * List adapter for spinner
 * Only one item can be selected
 */
public class CommonSpinnerAdapter extends ArrayAdapter<String> {
    public CommonSpinnerAdapter(Context context, List<String> itemList) {
        super(context, android.R.layout.simple_spinner_item, itemList);

        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}
