package com.petbox.shop.Adapter.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.petbox.shop.Item.BestGoodInfo;
import com.petbox.shop.R;

import java.util.ArrayList;

/**
 * Created by petbox on 2015-10-11.
 */
public class RecommendListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<BestGoodInfo> mItemList;
    LayoutInflater inflater;

    public RecommendListAdapter(){}

    public RecommendListAdapter(Context context){ mContext = context; }

    public RecommendListAdapter(Context context, ArrayList<BestGoodInfo> itemList){
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
            convertView = inflater.inflate(R.layout.list_style_recommend_item, parent, false);
            holder = new ViewHolder();

            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_list_recommend);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_list_recommend_name);
            holder.tv_rate = (TextView) convertView.findViewById(R.id.tv_list_recommend_rate);
            holder.tv_origin_price = (TextView) convertView.findViewById(R.id.tv_list_recommend_orgin_price);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_list_recommend_price);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        BestGoodInfo item = mItemList.get(position);

        holder.tv_name.setText(item.name);
        holder.tv_rate.setText(item.rate);
        holder.tv_origin_price.setText(item.origin_price + "원");
        holder.tv_price.setText(item.price+"원");

        return convertView;
    }

    class ViewHolder{
        ImageView iv_image;
        TextView tv_name;
        TextView tv_rate;
        TextView tv_origin_price;
        TextView tv_price;
    }
}
