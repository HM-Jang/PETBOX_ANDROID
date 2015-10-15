package com.petbox.shop.Adapter.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.petbox.shop.Item.GoodOptionInfo;
import com.petbox.shop.R;

import java.util.ArrayList;

/**
 * Created by petbox on 2015-10-13.
 */
public class OptionListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<GoodOptionInfo> mItemList;
    LayoutInflater inflater;

    int mode = 0;   // 0: 기본(이름, 가격, 잔여) 1: 이름

    public OptionListAdapter(){}

    public OptionListAdapter(Context context){
        mContext = context;
    }

    public OptionListAdapter(Context context, ArrayList<GoodOptionInfo> itemList){
        mContext = context;
        mItemList = itemList;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setMode(int mode){
        this.mode = mode;
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

        if(mode == 0){
            if(convertView == null){    //기본 모드
                convertView = inflater.inflate(R.layout.list_style_good_info_select_item, parent, false);
                holder = new ViewHolder();

                holder.tv_name = (TextView)convertView.findViewById(R.id.tv_list_good_option_name);
                holder.tv_count = (TextView)convertView.findViewById(R.id.tv_list_good_option_count);
                holder.tv_price = (TextView)convertView.findViewById(R.id.tv_list_good_option_price);
                convertView.setTag(holder);

            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            GoodOptionInfo item = mItemList.get(position);

            holder.tv_name.setText(item.name);
            holder.tv_count.setText("잔여 : " + item.count + "개");

            if(item.dc_price > 0){
                holder.tv_price.setText("(+"+item.dc_price + "원)");
            }else if(item.dc_price < 0){
                holder.tv_price.setText("(-"+item.dc_price + "원)");
            }else{
                holder.tv_price.setText("(가격 동일)");
            }

            return convertView;
        }else if(mode == 1){    //심플 모드
            if(convertView == null){
                convertView = inflater.inflate(R.layout.list_style_good_info_select_item_simple, parent, false);
                holder = new ViewHolder();

                holder.tv_name = (TextView)convertView.findViewById(R.id.tv_list_good_option_simple_name);
                convertView.setTag(holder);

            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            GoodOptionInfo item = mItemList.get(position);

            holder.tv_name.setText(item.name);

            return convertView;
        }else{
            return null;
        }


    }

    public class ViewHolder{
        TextView tv_name;
        TextView tv_count;
        TextView tv_price;
    }
}
