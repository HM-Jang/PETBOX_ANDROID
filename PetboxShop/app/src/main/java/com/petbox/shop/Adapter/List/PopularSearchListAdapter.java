package com.petbox.shop.Adapter.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.petbox.shop.Item.BestGoodInfo;
import com.petbox.shop.Item.PopularSearchInfo;
import com.petbox.shop.R;

import java.util.ArrayList;

/**
 * Created by petbox on 2015-09-16.
 */
public class PopularSearchListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<PopularSearchInfo> mItemList;
    LayoutInflater inflater;

    public PopularSearchListAdapter(){}

    public PopularSearchListAdapter(Context context){
        mContext = context;
    }

    public PopularSearchListAdapter(Context context, ArrayList<PopularSearchInfo> itemList){
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

            convertView = inflater.inflate(R.layout.list_style_popular_search, parent, false);
            holder = new ViewHolder();
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_popular_circle);
            holder.tv_rank = (TextView) convertView.findViewById(R.id.tv_popular_rank);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_popular_title);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        PopularSearchInfo item = mItemList.get(position);

        //holder.iv_image.setImageResource(R.drawable.no_image);

        int rank = item.ranking;

        if(rank <= 3){  // 1,2,3 [3순위]
            holder.iv_image.setImageResource(R.drawable.ranking_circle_mint);
        }else{
            holder.iv_image.setImageResource(R.drawable.ranking_circle_gray);
        }

        holder.tv_rank.setText(""+item.ranking);
        holder.tv_title.setText(item.title);

        return convertView;
    }

    public class ViewHolder{
        ImageView iv_image; // 원
        TextView tv_rank; // 순위
        TextView tv_title ; // 타이틀
    }
}
