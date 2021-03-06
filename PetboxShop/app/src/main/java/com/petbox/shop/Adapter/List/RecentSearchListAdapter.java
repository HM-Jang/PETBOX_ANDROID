package com.petbox.shop.Adapter.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.petbox.shop.DB.DBConnector;
import com.petbox.shop.Delegate.RecentSearchDelegate;
import com.petbox.shop.Item.RecentSearchInfo;
import com.petbox.shop.R;

import java.util.ArrayList;

/**
 * Created by petbox on 2015-09-16.
 */
public class RecentSearchListAdapter extends BaseAdapter  {

    Context mContext;
    ArrayList<RecentSearchInfo> mItemList;
    LayoutInflater inflater;

    RecentSearchDelegate delegate;

    public RecentSearchListAdapter(){}

    public RecentSearchListAdapter(Context context){
        mContext = context;
    }

    public RecentSearchListAdapter(Context context, ArrayList<RecentSearchInfo> itemList, RecentSearchDelegate _delegate){
        mContext = context;
        mItemList = itemList;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        delegate = _delegate;
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

            convertView = inflater.inflate(R.layout.list_style_recent_search, parent, false);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_recent_search_title);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_recent_search_date);
            holder.ibtn_delete = (ImageButton) convertView.findViewById(R.id.ibtn_recent_search_delete);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        RecentSearchInfo item = mItemList.get(position);

        //holder.iv_image.setImageResource(R.drawable.no_image);

        holder.tv_title.setText(item.title);
        holder.tv_date.setText(item.date);

        final int pos = position;

        holder.ibtn_delete.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {

                //mItemList.remove(pos);
                delegate.deleteNRefresh(mItemList.get(pos).rowId);
                //Toast.makeText(mContext, pos + "번째, 검색어 삭제(rowId : " + mItemList.get(pos).rowId + ")", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }



    public class ViewHolder{
        TextView tv_title ; // 타이틀
        TextView tv_date; // 검색날짜
        ImageButton ibtn_delete; // 삭제버튼
    }
}
