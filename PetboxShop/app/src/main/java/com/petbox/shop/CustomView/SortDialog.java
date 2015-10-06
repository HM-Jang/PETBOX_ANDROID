package com.petbox.shop.CustomView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.petbox.shop.Delegate.SortDelegate;
import com.petbox.shop.Fragment.Category.CategoryGoodsFragment;
import com.petbox.shop.R;

import java.util.ArrayList;

/**
 * Created by petbox on 2015-10-03.
 */
public class SortDialog extends Dialog implements View.OnClickListener {

    ArrayList<String> mItemList;
    ListView listView;

    Context mContext;
    SortDelegate delegate;

    LinearLayout btn_low, btn_high, btn_recent;

    public SortDialog(Context context) {
        super(context);
        mContext = context;
    }

    public SortDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    protected SortDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    public SortDialog(Context context, SortDelegate _delegate){
        super(context);
        mContext = context;
        delegate = _delegate;
    }

    public SortDialog(Context context, ArrayList<String> itemList, SortDelegate _delegate){
        super(context);
        mContext = context;
        mItemList = itemList;
        delegate = _delegate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_sort);

        btn_low = (LinearLayout) findViewById(R.id.linear_dialog_sort_low);
        btn_low.setOnClickListener(this);

        btn_high = (LinearLayout) findViewById(R.id.linear_dialog_sort_high);
        btn_high.setOnClickListener(this);

        btn_recent = (LinearLayout) findViewById(R.id.linear_dialog_sort_recent);
        btn_recent.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.linear_dialog_sort_low:
                delegate.sort(0);
                dismiss();
                break;

            case R.id.linear_dialog_sort_high:
                delegate.sort(1);
                dismiss();
                break;

            case R.id.linear_dialog_sort_recent:
                delegate.sort(2);
                dismiss();
                break;
        }
    }

}
