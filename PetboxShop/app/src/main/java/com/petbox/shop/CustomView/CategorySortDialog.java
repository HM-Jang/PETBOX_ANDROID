package com.petbox.shop.CustomView;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by petbox on 2015-10-03.
 */
public class CategorySortDialog extends Dialog {
    public CategorySortDialog(Context context) {
        super(context);
    }

    public CategorySortDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CategorySortDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }



}
