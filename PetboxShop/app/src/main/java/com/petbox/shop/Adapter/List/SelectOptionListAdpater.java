package com.petbox.shop.Adapter.List;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.petbox.shop.Delegate.GoodInfoDelegate;
import com.petbox.shop.Item.BestGoodInfo;
import com.petbox.shop.R;

import java.util.ArrayList;

/**
 * Created by petbox on 2015-10-12.
 */
public class SelectOptionListAdpater extends BaseAdapter{

    Context mContext;
    ArrayList<String> mItemList;
    LayoutInflater inflater;


    public SelectOptionListAdpater(){}

    public SelectOptionListAdpater(Context context){
        mContext = context;
    }

    public SelectOptionListAdpater(Context context, ArrayList<String> itemList){
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
            convertView = inflater.inflate(R.layout.list_style_good_info_select, parent, false);
            holder = new ViewHolder();

            holder.tv_select = (TextView)convertView.findViewById(R.id.tv_good_info_select);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        String item_name = mItemList.get(position);

        holder.tv_select.setText(item_name);

        return convertView;
    }

    public class ViewHolder{
        TextView tv_select;
    }

}
