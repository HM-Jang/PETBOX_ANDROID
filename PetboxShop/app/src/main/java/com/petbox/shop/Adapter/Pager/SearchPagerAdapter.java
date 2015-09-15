package com.petbox.shop.Adapter.Pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.petbox.shop.Fragment.Search.PopularSearchFragment;
import com.petbox.shop.Fragment.Search.RecentSearchFragment;


import java.util.Locale;

/**
 * Created by petbox on 2015-09-14.
 */
public class SearchPagerAdapter extends FragmentStatePagerAdapter
{
    public SearchPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position){

            case 0: //인기 검색어
                return PopularSearchFragment.newInstance("", "");

            case 1: //최근 검색어
                return RecentSearchFragment.newInstance("", "");

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
                return "인기검색어";
            case 1:
                return "최근검색어";

        }
        return null;
    }

}
