package com.example.muirsuus.Calculation;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muirsuus.R;
import java.util.ArrayList;
import java.util.List;





public class Razvedka_fragment extends Fragment {

    List<Double> list_of_values = new ArrayList<>();
    private Button next;
    private Button back;
    private Button calculate;
    private EditText count_of_elements;
    private volatile int col = 0;//введенное число
    double[] values;
    private TableLayout tableLayout;
    private boolean mShrink;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reconnaissance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        next = view.findViewById(R.id.next);
        calculate = view.findViewById(R.id.calculate_razvedka);
        count_of_elements = view.findViewById(R.id.counts_of_elements);
        //back = view.findViewById(R.id.back_to_input_razvedka);

        tableLayout = view.findViewById(R.id.linear_table);
        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);

        //получаем количество элементов неоьходимых для дальнейшего расчёта
        count_of_elements.addTextChangedListener(new TextWatcher() {
            // перед тем, как текст будет изменен кнопка "Далее" невидима
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                next.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // недостаток при автоматической вставке строки, программа вылетает, так так есть

                next.setVisibility(View.INVISIBLE);
                calculate.setVisibility(View.INVISIBLE);
                //back.setVisibility(View.INVISIBLE);
                String new_str = ""; //здесь хранится введеная цифра

                //проверяем не была ли введена пустая строка или не удалил ли пользователь введенную ранее введенную строку
                if (!count_of_elements.getText().toString().equals("")) {
                    String str = count_of_elements.getText().toString();
                    new_str = str.substring(str.length() - 1);
                } else {
                    col = 0;
                    tableLayout.removeAllViews();
                }

                if (isInt(new_str)) {
                    col = Integer.parseInt(count_of_elements.getText().toString());//получаем целое значение, которое было введено

                } else {
                    Toast.makeText(getContext(), "Необходимо ввести число", Toast.LENGTH_SHORT).show();
                }
            }

//для того чтобы таблица для ввода не наслаивалась одна на другую, очищаем tableLayout
            @Override
            public void afterTextChanged(Editable s) {
                tableLayout.removeAllViews();
                if(col != 0) {
                    next.setVisibility(View.VISIBLE);
                }
            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (col != 0){
                    values = new double[col];
                    tableLayout.addView(make_standart_input());

                    for(TableRow tableRow : make_input(col)){
                        tableLayout.addView(tableRow);
                    }

                    mShrink = !mShrink;
                    tableLayout.setColumnShrinkable(0, mShrink);
                    next.setVisibility(View.INVISIBLE);
                    calculate.setVisibility(View.VISIBLE);
                    //back.setVisibility(View.VISIBLE);
                    }
                }
        });

        mShrink = tableLayout.isColumnShrinkable(0);
//Возможная проблема заключается в том, что
// вы проверяете TableLayout, но ваши EditText и TextView находятся в TableRow, поэтому вам нужно проверить TableRow childs.


        calculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                boolean input_is_correct = true;
                double P_Bckr = 0;
                double  n_Bckr = 0;
                double n_app = 0;
                int childParts = tableLayout.getChildCount();// определяем сколько детей у tableLayout
                Log.d("mLog", "childParts = " +childParts);
                if (tableLayout != null) {
                    for (int i = 0; i < childParts; i++) {
                        View viewChild = tableLayout.getChildAt(i);

                        if (viewChild instanceof TableRow) {
                            int rowChildParts = ((TableRow) viewChild).getChildCount();//определеяем сколько детей у TableRow
                            Log.d("mLog", "rowChildParts = " +rowChildParts);

                            for (int j = 0; j < rowChildParts; j++) { // проходимся по всем детям TableRow
                                View viewChild2 = ((TableRow) viewChild).getChildAt(j);
                                Log.d("mLog", "viewChild2 = " +viewChild2);

                                if (viewChild2 instanceof EditText) {

                                    String text = ((EditText) viewChild2).getText().toString();
                                    if(isInt(text)){
                                        double n = Double.parseDouble(text);
                                        if(j == 1){
                                            n_Bckr = n;  // получаем введеные число у первого editText
                                        }
                                        else {
                                            n_app = n; // получаем введеные число у второго editText
                                        }

                                    }
                                    else{
                                        Toast.makeText(getContext(), "Необходимо ввести число", Toast.LENGTH_SHORT).show();
                                        input_is_correct = false;
                                    }
                                }
                            }

                            if( n_Bckr != 0 ||  n_app != 0) {
                                P_Bckr = 1 - Math.exp(-n_Bckr / n_app);
                                list_of_values.add(P_Bckr);
                            }

                            for(int k = 0; list_of_values.size() > k; k++){
                                values[k] = list_of_values.get(k);
                            }
                            Log.d("mLog", "P_Bckr = " + P_Bckr);
                        }
                    }
                    if(input_is_correct){
                    Bundle bundle = new Bundle();
                    bundle.putDoubleArray("Double values",values);
                    navController = Navigation.findNavController(view);
                    navController.navigate(R.id.tableView, bundle);}
                }

            }
        });






    }




    @SuppressLint("SetTextI18n")
    private TableRow make_standart_input() {

        TableRow row_const_tables = new TableRow(getContext());
        TableRow.LayoutParams input_param = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1);//ширина высота
        

        TextView elements = new TextView(getContext());
        elements.setText("Элементы");
        elements.setTypeface(Typeface.DEFAULT_BOLD);
        elements.setTextSize(18);
        elements.setLayoutParams(input_param);

        TextView count_open = new TextView(getContext());
        count_open.setText("N.вскр.апп");
        count_open.setTypeface(Typeface.DEFAULT_BOLD);
        count_open.setTextSize(18);
        count_open.setLayoutParams(input_param);

        TextView count_all = new TextView(getContext());
        count_all.setText("N.апп      ");
        count_all.setTypeface(Typeface.DEFAULT_BOLD);
        count_all.setTextSize(18);
        count_all.setLayoutParams(input_param);


        row_const_tables.addView(elements);
        row_const_tables.addView(count_open);
        row_const_tables.addView(count_all);

        return row_const_tables;

    }

    private List<TableRow> make_input(int count_of_rows) {
        int step = 0;
        List<TableRow> rows = new ArrayList<>();

        while (step < col) {

            step += 1;

            TableRow dynamic_data = new TableRow(getContext());



            //TableRow.LayoutParams LayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);//ширина высота
            //TableRow.LayoutParams Param = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);

            TextView element = new TextView(getContext());
            element.setText("Элемент № " + String.valueOf(step));
            element.setTypeface(Typeface.SERIF);
            element.setTextSize(15);

            EditText editText = new EditText(getContext());
            editText.setMaxEms(1);
            editText.setHint("Введите число");





            EditText editText1 = new EditText(getContext());
            editText1.setMaxEms(1);
            editText1.setHint("Введите число");

            dynamic_data.addView(element);
            dynamic_data.addView(editText);
            dynamic_data.addView(editText1);
            rows.add(dynamic_data);
        }
        return rows;
    }
    /**
     * метод, который возвращает true если введено значение int
     * false если введено все что угодно, кроме int


     */
    private boolean isInt(String input){
        boolean isInt = false;
        for(int j = 0; j< input.length(); j++){

            if(input.charAt(j) > '0' && input.charAt(j) <= '9' ){
                isInt = true;
            }
            else isInt = false;
        }
        return isInt;
    }

}

