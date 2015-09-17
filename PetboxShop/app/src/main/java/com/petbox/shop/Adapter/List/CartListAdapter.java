package com.petbox.shop.Adapter.List;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.petbox.shop.CustomView.CustomNumberPicker;
import com.petbox.shop.Item.CartItemInfo;
import com.petbox.shop.R;

import java.util.ArrayList;

import static com.petbox.shop.R.*;

/**
 * Created by petbox on 2015-09-17.
 */
public class CartListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<CartItemInfo> mItemList;
    LayoutInflater inflater;


    public CartListAdapter(){}

    public CartListAdapter(Context context){
        mContext = context;
    }

    public CartListAdapter(Context context, ArrayList<CartItemInfo> itemList){
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

            convertView = inflater.inflate(layout.list_style_cart_item, parent, false);
            holder = new ViewHolder();
            holder.chk = (CheckBox) convertView.findViewById(R.id.chk_cart_item);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_cart_item_image);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_cart_item_name);
            holder.np = (CustomNumberPicker) convertView.findViewById(R.id.np_cart_item);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_cart_item_price);
            holder.ibtn_delete = (ImageButton) convertView.findViewById(R.id.ibtn_cart_item_delete);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        CartItemInfo item = mItemList.get(position);

        //holder.iv_image.setImageResource(R.drawable.no_image);

        final int pos = position;

        holder.chk.setText(item.name);
        //holder.iv_image.setImageResource(drawable.no_image);
        holder.tv_name.setText(item.detail_name);
        holder.np.setMax(item.max);
        holder.tv_price.setText(item.price + "원");

        holder.ibtn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, pos+"번째, 항목 삭제", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

      /*
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.ibtn_recent_search_delete:
                Toast.makeText(mContext, "", )
        }

    }
    */

    public class ViewHolder{
        CheckBox chk;
        ImageView iv_image;
        TextView tv_name;
        CustomNumberPicker np;
        TextView tv_price;
        ImageButton ibtn_delete;
    }

}
