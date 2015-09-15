package com.petbox.shop.Adapter.Grid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.petbox.shop.BestGoodInfo;
import com.petbox.shop.R;

import java.util.ArrayList;

/**
 * Created by petbox on 2015-09-15.
 */
public class BestGoodGridAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<BestGoodInfo> mItemList;
    LayoutInflater inflater;

    public BestGoodGridAdapter(Context context){
        mContext = context;
    }

    public BestGoodGridAdapter(){}


    public BestGoodGridAdapter(Context context, ArrayList<BestGoodInfo> itemList){
        mContext = context;
        mItemList = itemList;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){

            convertView = inflater.inflate(R.layout.grid_best_good_item, parent, false);
            holder = new ViewHolder();
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_best_good_grid);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_best_good_grid_name);
            holder.tv_rate = (TextView) convertView.findViewById(R.id.tv_best_good_grid_rate);
            holder.tv_origin_price = (TextView) convertView.findViewById(R.id.tv_best_good_grid_origin_price);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_best_good_grid_price);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            holder.tv_rate_person = (TextView) convertView.findViewById(R.id.tv_best_good_grid_rate_person);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        BestGoodInfo item = mItemList.get(position);

        //holder.iv_image.setImageResource(R.drawable.no_image);
        holder.tv_name.setText(item.name);
        holder.tv_rate.setText(item.rate);
        holder.tv_origin_price.setText(item.origin_price);
        holder.tv_price.setText(item.price);
        holder.ratingBar.setRating(item.rating);
        holder.tv_rate_person.setText("(" + item.rating_person + ")");

        return convertView;
    }

    public class ViewHolder{
        ImageView iv_image; //상품 이미지
        TextView tv_name ; // 상품 명
        TextView tv_rate; // 할인율
        TextView tv_origin_price; //원래 가격
        TextView tv_price; // 할인적용된 실제 판매 가격
        RatingBar ratingBar;    //레이팅바
        TextView tv_rate_person; // 점수준 사람
    }
}
