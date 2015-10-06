package com.petbox.shop.Adapter.Pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.petbox.shop.Fragment.Home.PlanningFragment;
import com.petbox.shop.Fragment.Home.PlanningGoodsFragment;

/**
 * Created by petbox on 2015-10-05.
 */
public class IntegrationPlanningPagerAdapter extends FragmentStatePagerAdapter{

    private static final String TAG = "IntegrationPlanningPagerAdapter";

    public IntegrationPlanningPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: // 기획전 리스트
                return PlanningFragment.newInstance("","");

            case 1: // 기획전 상품 리스트
                return PlanningGoodsFragment.newInstance("","");
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
