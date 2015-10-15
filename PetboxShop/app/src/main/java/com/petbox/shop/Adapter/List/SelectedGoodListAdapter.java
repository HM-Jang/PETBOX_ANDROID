package com.petbox.shop.Adapter.List;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.petbox.shop.CustomView.CustomNumberPicker;
import com.petbox.shop.Delegate.GoodInfoDelegate;
import com.petbox.shop.Delegate.NumberPickerDelegate;
import com.petbox.shop.Item.GoodOptionInfo;
import com.petbox.shop.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by petbox on 2015-10-13.
 */
public class SelectedGoodListAdapter extends BaseAdapter implements NumberPickerDelegate, View.OnClickListener {

    Context mContext;
    ArrayList<GoodOptionInfo> mItemList;
    LayoutInflater inflater;

    NumberPickerDelegate delegate;
    //GoodInfoDelegate delegate;

    int mode = 0; // 0: 기본, 1: '0'번 인덱스 삭제 버튼 GONE

    public SelectedGoodListAdapter(){}

    public SelectedGoodListAdapter(Context context){
        mContext = context;
    }

    public SelectedGoodListAdapter(Context context, ArrayList<GoodOptionInfo> itemList, NumberPickerDelegate delegate){
        mContext = context;
        mItemList = itemList;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.delegate = delegate;
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

        GoodOptionInfo item = mItemList.get(position);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_style_good_info_select_good, parent, false);
            holder = new ViewHolder();

            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_list_select_good_name);
            holder.tv_count = (TextView)convertView.findViewById(R.id.tv_list_select_good_count);
            holder.picker_count = (CustomNumberPicker)convertView.findViewById(R.id.picker_list_select_good);
            holder.picker_count.setDelegate(this);

            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_list_select_good_price);
            holder.ibtn_delete = (ImageButton) convertView.findViewById(R.id.ibtn_list_select_good_delete);

            if(mode == 0){
                holder.ibtn_delete.setOnClickListener(this);
            }else if(mode == 1){
                if(position == 0 ){
                    /*
                    HashMap<String, Integer> hash = new HashMap<String, Integer>();
                    hash.put("price", item.price);
                    hash.put("position", 0);
                    holder.picker_count.setParam(hash);

                    holder.picker_count.setMin(1);
                    holder.picker_count.setOne();
                    */
                    holder.picker_count.setMin(1);
                    holder.ibtn_delete.setVisibility(View.GONE);

                }else{
                    holder.ibtn_delete.setOnClickListener(this);
                }

            }
            holder.ibtn_delete.setTag(position);


            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(item.name);
        holder.tv_count.setText("잔여 : " + (item.count - item.order_count) + "개");
        holder.tv_price.setText((item.order_count * item.price) + "원");
        holder.picker_count.setMax(item.count - item.order_count);
        holder.picker_count.setNum(item.order_count);

        HashMap<String, Integer> hash = new HashMap<String, Integer>();
        hash.put("price", item.price);
        hash.put("position", position);
        holder.picker_count.setParam(hash);



        return convertView;
    }

    @Override
    public void onClick(View v) {
       int position = (Integer)v.getTag();

        GoodOptionInfo item = mItemList.get(position);

        delegate.deleteItem(item.price * item.order_count);
        mItemList.get(position).order_count = 0;
        mItemList.remove(position);

        this.notifyDataSetChanged();
    }

    /* NumberPicker Delegate */
    @Override
    public void clickIncrease(HashMap<String, Integer> params) {
        int position = params.get("position");
        int order_count = params.get("order_count");

        mItemList.get(position).order_count = order_count;

        this.notifyDataSetChanged();

        delegate.clickIncrease(params);
        //delegate.refreshAllPrice();
    }

    @Override
    public void clickDecrease(HashMap<String, Integer> params) {
        int position = params.get("position");
        int order_count = params.get("order_count");

        mItemList.get(position).order_count = order_count;

        this.notifyDataSetChanged();
        delegate.clickDecrease(params);
       // delegate.refreshAllPrice();
    }

    @Override
    public void deleteItem(int price) {

    }

    @Override
    public void setNum(HashMap<String, Integer> params) {
        int position = params.get("position");
        int order_count = params.get("order_count");

        mItemList.get(position).order_count = order_count;

        this.notifyDataSetChanged();

        delegate.setNum(params);
    }


    class ViewHolder{
        TextView tv_name;
        TextView tv_count;
        CustomNumberPicker picker_count;
        TextView tv_price;
        ImageButton ibtn_delete;
    }
}

