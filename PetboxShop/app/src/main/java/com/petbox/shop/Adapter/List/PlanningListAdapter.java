package com.petbox.shop.Adapter.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.petbox.shop.PlanningItemInfo;
import com.petbox.shop.R;

import java.util.ArrayList;

/**
 * Created by petbox on 2015-09-16.
 */

//기획전 페이지
public class PlanningListAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    ArrayList<PlanningItemInfo> mItemList;

    public PlanningListAdapter(){}

    public PlanningListAdapter(Context context){mContext = context;}

    public PlanningListAdapter(Context context, ArrayList<PlanningItemInfo> itemList){
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

            convertView = inflater.inflate(R.layout.list_style_planning, parent, false);
            holder = new ViewHolder();
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_list_image);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_list_name);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

       PlanningItemInfo item = mItemList.get(position);

        //holder.iv_image.setImageResource(R.drawable.no_image);
        holder.tv_name.setText(item.name);

        return convertView;
    }

    public class ViewHolder{
        ImageView iv_image; //기획전 이미지
        TextView tv_name ; // 기획전 명
    }
}
