package com.petbox.shop.CustomView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import com.petbox.shop.R;

import java.util.ArrayList;

/**
 * Created by petbox on 2015-10-03.
 */
public class SortDialog extends Dialog {

    ArrayList<String> mItemList;
    ListView listView;

    public SortDialog(Context context) {
        super(context);
    }

    public SortDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SortDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public SortDialog(Context context, ArrayList<String> itemList){
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_sort);

    }

}
