package com.petbox.shop.Adapter.Pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.petbox.shop.Delegate.CategoryDelegate;
import com.petbox.shop.Fragment.Category.CategoryFragment;
import com.petbox.shop.Fragment.Category.CategoryGoodsFragment;


import java.util.Locale;

/**
 * Created by petbox on 2015-09-14.
 */
public class CategoryPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "CategoryPagerAdapter";
    private CategoryDelegate delegate;


    //int mode = 0; // 0: 카테고리 메뉴 , 1: 애견,애묘 카테고리 상품리스트

    /*
    public void setMode(int _mode){
        mode = _mode;
    }
    */
    public CategoryPagerAdapter(FragmentManager fm) {
        super(fm);
    }




    public CategoryPagerAdapter(FragmentManager fm, CategoryDelegate _delegate) {
        super(fm);
        delegate = _delegate;
    }


    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position){

            case 0: //전체 카테고리
                //Log.i(TAG, "CategoryFragment");
                return CategoryFragment.newInstance(delegate, "");

            case 1: //카테고리 상품 화면
                return CategoryGoodsFragment.newInstance(delegate, "");
                /*
                if(mode == 0)
                    return CategoryFragment.newInstance(delegate, "");
                else
                    return CategoryGoodsFragment.newInstance(delegate, "");
                */
        }
        return null;
        //  return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "전체 카테고리";
            case 1:
                return "카테고리 상품 화면";

        }
        return null;
    }

}
