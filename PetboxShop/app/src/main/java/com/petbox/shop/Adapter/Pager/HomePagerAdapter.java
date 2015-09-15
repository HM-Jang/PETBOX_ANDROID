package com.petbox.shop.Adapter.Pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.petbox.shop.Fragment.Home.BestGoodFragment;
import com.petbox.shop.Fragment.Home.ChanceDealFragment;
import com.petbox.shop.Fragment.Home.EventFragment;
import com.petbox.shop.Fragment.Home.PlanningFragment;
import com.petbox.shop.Fragment.Home.PrimiumFragment;
import com.petbox.shop.Fragment.Home.RegularShippingFragment;


import java.util.Locale;

/**
 * Created by petbox on 2015-09-14.
 */
public class HomePagerAdapter extends FragmentStatePagerAdapter {

    public HomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

    @Override
    public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position){

            case 0: //베스트상품
                return BestGoodFragment.newInstance("","");

            case 1: //찬스딜
                return ChanceDealFragment.newInstance("","");

            case 2: //기획전
                return PlanningFragment.newInstance("","");

            case 3: //프리미엄몰
                return PrimiumFragment.newInstance("","");

            case 4: //정기배송
                return RegularShippingFragment.newInstance("","");

            case 5: // 이벤트
                return EventFragment.newInstance("","");
        }

        return null;

        //  return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "베스트상품";
            case 1:
                return "찬스딜";
            case 2:
                return "기획전";
            case 3:
                return "프리미엄몰";
            case 4:
                return "정기배송";
            case 5:
                return "이벤트";
        }
        return null;
    }
}
