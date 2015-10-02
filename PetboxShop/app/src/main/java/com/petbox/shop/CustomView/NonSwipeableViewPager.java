package com.petbox.shop.CustomView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by petbox on 2015-10-02.
 */
public class NonSwipeableViewPager extends ViewPager {

    boolean enabled;

    public NonSwipeableViewPager(Context context) {
        super(context);
        enabled = true;
    }

    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        enabled = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages

        if(enabled){
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        if(enabled){
            return super.onTouchEvent(event);
        }

        return false;
    }

    public void setSwipeEnabled(boolean enabled){
        this.enabled = enabled;
    }

}
