package com.example.muirsuus.ui.layouts;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.muirsuus.R;



public class TthFragment extends Fragment {
    int a;
    //создание конструктора класса для получение нужного ID
    public TthFragment(int a){
        this.a = a;
    }
    public TthFragment(){
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_layout_tth,container,false);




        TableLayout table = new TableLayout(getContext()); //создаём нашу таблицу

        table.setStretchAllColumns(true); //какие-то функции для таблицы
        table.setShrinkAllColumns(true);

        TableRow rowTitle = new TableRow(getContext());
        rowTitle.setGravity(Gravity.CENTER);

        TableRow rowDayLabels = new TableRow(getContext()); //создаём строки
        TableRow rowHighs = new TableRow(getContext());
        TableRow rowLows = new TableRow(getContext());


        TextView empty = new TextView(getContext());

        // заголовок таблицы
        TextView title = new TextView(getContext());
        title.setText("Тактико-технические характеристики УКШМ Р-149АКШ");

        //параметры заголовка
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);

        //ОПРЕДЕЛЯЕМ количество столбцов
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 4;


        rowTitle.addView(title, params);

        /*while (columns != 0){
            columns-=1;

            TextView textView = new TextView(getContext());
            textView.setText("Day High");
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        }*/

        // первый столбец
        TextView highsLabel = new TextView(getContext());
        highsLabel.setText("Диапазон рабочих частот,МГц");
        highsLabel.setTypeface(Typeface.DEFAULT_BOLD);
        highsLabel.setGravity(Gravity.LEFT);
        highsLabel.setTextSize(10);


        TextView lowsLabel = new TextView(getContext());
        lowsLabel.setText("Шаг сетки частот, кГц");
        lowsLabel.setTypeface(Typeface.DEFAULT_BOLD);
        lowsLabel.setGravity(Gravity.LEFT);
        lowsLabel.setTextSize(10);




        rowDayLabels.addView(empty);
        rowHighs.addView(highsLabel);
        rowLows.addView(lowsLabel);


        // day 1 column
        TextView day1Label = new TextView(getContext());
        day1Label.setText("Р-168-100У-2");
        day1Label.setTypeface(Typeface.SERIF, Typeface.BOLD);
        day1Label.setTextSize(10);

        TextView day1High = new TextView(getContext());
        day1High.setText("30 … 107,975");
        day1High.setGravity(Gravity.CENTER_HORIZONTAL);
        day1High.setTextSize(10);


        TextView day1Low = new TextView(getContext());
        day1Low.setText("25");
        day1Low.setGravity(Gravity.CENTER_HORIZONTAL);
        day1Low.setTextSize(10);



        rowDayLabels.addView(day1Label);
        rowHighs.addView(day1High);
        rowLows.addView(day1Low);


        // day2 column
        TextView day2Label = new TextView(getContext());
        day2Label.setText("Р-168-100КА");
        day2Label.setTypeface(Typeface.SERIF, Typeface.BOLD);
        day2Label.setTextSize(10);

        TextView day2High = new TextView(getContext());
        day2High.setText("1,5…29,9999");
        day2High.setGravity(Gravity.CENTER_HORIZONTAL);
        day2High.setTextSize(10);

        TextView day2Low = new TextView(getContext());
        day2Low.setText("0,1");
        day2Low.setGravity(Gravity.CENTER_HORIZONTAL);
        day2Low.setTextSize(10);



        rowDayLabels.addView(day2Label);
        rowHighs.addView(day2High);
        rowLows.addView(day2Low);


        // day3 column
        TextView day3Label = new TextView(getContext());
        day3Label.setText("Р-168-5УН(1");
        day3Label.setTypeface(Typeface.SERIF, Typeface.BOLD);
        day3Label.setTextSize(10);

        TextView day3High = new TextView(getContext());
        day3High.setText("30-87,975");
        day3High.setGravity(Gravity.CENTER_HORIZONTAL);
        day3High.setTextSize(10);

        TextView day3Low = new TextView(getContext());
        day3Low.setText("25");
        day3Low.setGravity(Gravity.CENTER_HORIZONTAL);
        day3Low.setTextSize(10);



        rowDayLabels.addView(day3Label);
        rowHighs.addView(day3High);
        rowLows.addView(day3Low);




        table.addView(rowTitle);
        table.addView(rowDayLabels);
        table.addView(rowHighs);
        table.addView(rowLows);





        return table;
    }
}
