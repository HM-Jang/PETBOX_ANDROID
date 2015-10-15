package com.petbox.shop.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.petbox.shop.Delegate.NumberPickerDelegate;
import com.petbox.shop.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by petbox on 2015-09-17.
 */
public class CustomNumberPicker extends LinearLayout implements View.OnClickListener{

    LayoutInflater inflater;
    Context mContext;

    Button btn_minus, btn_plus;
    EditText edit_num;

    int max = 0;
    int min = 0;

    int num = 0;

   HashMap<String, Integer> params; //파라미터

    NumberPickerDelegate delegate;

    public void setDelegate(NumberPickerDelegate delegate){
        this.delegate = delegate;
    }

    public void setMax(int _max){
        max = _max;
    }

    public void setMin(int _min){min = _min;}

    public void setNum(int _num){
        num = _num;
        edit_num.setText(num+"");
    }

    public void setOne(){
        params.put("before_order_count", num);
        num = 1;
        edit_num.setText("1");

        params.put("order_count", 1);

        delegate.setNum(params);
    }

    //position, price, , max
    public void setParam(HashMap<String, Integer> params){
        this.params = params;
    }

    public int getNum(){
        return Integer.parseInt(edit_num.getText().toString());
    }

    public CustomNumberPicker(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CustomNumberPicker(Context context, int _max) {
        super(context);
        mContext = context;
        max = _max;
        init();
    }


    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public CustomNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }


    void init(){
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_numberpicker, this, true);

        btn_minus = (Button)findViewById(R.id.btn_minus);
        btn_minus.setOnClickListener(this);

        btn_plus = (Button)findViewById(R.id.btn_plus);
        btn_plus.setOnClickListener(this);

        edit_num = (EditText)findViewById(R.id.edit_num);
        edit_num.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.btn_minus:
                if(num <= min)
                    num = min;
                else
                    num -= 1;

                edit_num.setText("" + num);

                params.put("order_count", num);

                if(delegate != null)
                    delegate.clickDecrease(params);

                break;

            case R.id.btn_plus:

                if(num >= max)
                    num = max;
                else
                    num += 1;

                edit_num.setText(""+num);

                params.put("order_count", num);

                if(delegate != null)
                    delegate.clickIncrease(params);

                break;

            case R.id.edit_num:
                break;
        }

    }
}
