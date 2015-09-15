package com.petbox.shop.Adapter.Pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.petbox.shop.Fragment.Category.CategoryFragment;


import java.util.Locale;

/**
 * Created by petbox on 2015-09-14.
 */
public class CategoryPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "CategoryPagerAdapter";

    public CategoryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position){

            case 0: //전체 카테고리
                Log.i(TAG, "CategoryFragment");
                return CategoryFragment.newInstance("", "");

        }

        return null;

        //  return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "전체 카테고리";

        }
        return null;
    }

}
