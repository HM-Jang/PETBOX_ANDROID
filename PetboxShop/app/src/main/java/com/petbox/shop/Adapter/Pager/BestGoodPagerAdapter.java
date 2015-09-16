package com.petbox.shop.Adapter.Pager;

import android.content.Context;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.petbox.shop.R;

/**
 * Created by petbox on 2015-09-15.
 */
public class BestGoodPagerAdapter extends PagerAdapter {

    Context mContext;

    private int[] images = new int[]{
            R.drawable.test1,
            R.drawable.test2,
            R.drawable.test3,
            R.drawable.test4,
            R.drawable.test5,
            R.drawable.test6
    };

    public BestGoodPagerAdapter(){}

    public BestGoodPagerAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){

        ImageView imageView = new ImageView(mContext);

        /*
        int padding = mContext.getResources().getDimensionPixelSize(R.dimen.padding_medium);
        imageView.setPadding(padding, padding, padding, padding);
        */
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(images[position]);

        final int pos = position;

        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, pos + "번째, 이미지 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        ((ViewPager) container).addView(imageView, 0);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        ((ViewPager) container).removeView((ImageView)object);
    }

}
