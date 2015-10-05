package com.petbox.shop.Fragment.Category;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.petbox.shop.Adapter.List.ChanceDealListAdapter;
import com.petbox.shop.Adapter.Pager.BestGoodPagerAdapter;
import com.petbox.shop.CustomView.SortDialog;
import com.petbox.shop.DB.Constants;
import com.petbox.shop.Delegate.CategoryDelegate;
import com.petbox.shop.Item.BestGoodInfo;
import com.petbox.shop.R;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryGoodsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryGoodsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryGoodsFragment.
     */

    int mainColor = 0;

    private Spinner spin_main, spin_sub;
    private ArrayAdapter<String> adapter;

    private ImageButton btn_sort;

    private ViewPager viewPager;
    PageIndicator mIndicator;
    BestGoodPagerAdapter bestGoodPagerAdapter;

    private PullToRefreshListView listView;
    ChanceDealListAdapter listAdapter;
    ArrayList<BestGoodInfo> mItemList;

    CategoryDelegate delegate;

    public int interval = Constants.AUTO_SLIDE_TIME;

    Thread  timerThread;
    Handler handler;

    Boolean isRunning = true;

    LinearLayout linear_slide_btn;  //헤더의 강아지 고양이 버튼묶음

    SortDialog  dialog;

    // TODO: Rename and change types and number of parameters
    public static CategoryGoodsFragment newInstance(CategoryDelegate _delegate, String param2) {
        CategoryGoodsFragment fragment = new CategoryGoodsFragment();
        Bundle args = new Bundle();

        fragment.delegate = _delegate;
        //args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CategoryGoodsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category_goods, container, false);

        dialog = new SortDialog(getContext());

        spin_main = (Spinner)v.findViewById(R.id.spin_category_goods_main);

        String[] first_category = getResources().getStringArray(R.array.first_category);

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, first_category);
        spin_main.setAdapter(adapter);

        spin_sub = (Spinner)v.findViewById(R.id.spin_category_goods_sub);
        btn_sort = (ImageButton)v.findViewById(R.id.btn_category_goods_sort);
        btn_sort.setOnClickListener(this);

        mainColor = getResources().getColor(R.color.colorPrimary);

        View headerView = inflater.inflate(R.layout.custom_slide_image, null);
        viewPager = (ViewPager)headerView.findViewById(R.id.pager_best_good);

        bestGoodPagerAdapter = new BestGoodPagerAdapter(getContext());
        viewPager.setAdapter(bestGoodPagerAdapter);

        CirclePageIndicator circlePageIndicator = (CirclePageIndicator)headerView.findViewById(R.id.indicator_best_good);
        mIndicator = circlePageIndicator;
        mIndicator.setViewPager(viewPager);

        circlePageIndicator.setPageColor(0xFF6d6d6d);   // Normal 원 색상
        circlePageIndicator.setFillColor(mainColor);   //선택된 원 색상
        circlePageIndicator.setStrokeColor(0x00000000); //테두리 INVISIBLE

        linear_slide_btn = (LinearLayout) headerView.findViewById(R.id.linear_slide_btn);
        linear_slide_btn.setVisibility(View.GONE);

        mItemList = new ArrayList<BestGoodInfo>();

        BestGoodInfo info[] = new BestGoodInfo[20];

        for(int i=0; i<20; i++){
            info[i] = new BestGoodInfo();

            info[i].name = "상품명\n"+i;
            info[i].rate = ""+i;
            info[i].origin_price = (i+1)+ "5,000";
            info[i].price = (i+1)+"0,000";
            info[i].rating = 3;
            info[i].rating_person = i;

            mItemList.add(info[i]);
        }

        listView = (PullToRefreshListView)v.findViewById(R.id.list_category_goods);
        listAdapter = new ChanceDealListAdapter(getActivity().getApplicationContext(), mItemList);
        listView.addHeaderView(headerView);
        listView.setAdapter(listAdapter);

        handler = new Handler(){
            public void handleMessage(Message msg){
                if (viewPager.getCurrentItem() == bestGoodPagerAdapter.getImages() - 1) {
                    viewPager.setCurrentItem(0, true);
                }else{
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
                }
            }
        };


        timerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isRunning){
                    try{
                        timerThread.sleep(3000);

                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    //isRunning = false;

                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                }
            }
        });

        timerThread.start();

        return v;
    }

    public void initViewPager(){
        viewPager.setCurrentItem(0);
    }

    public void refreshLIstView(ArrayList<BestGoodInfo> itemList){
        mItemList.clear();
        mItemList = itemList;

        listAdapter = new ChanceDealListAdapter(getActivity().getApplicationContext(), mItemList);
        listView.setAdapter(listAdapter);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.btn_category_goods_sort:

                if(!dialog.isShowing())
                    dialog.show();
                else
                    dialog.dismiss();



                //Toast.makeText(getContext(), "정렬 버튼", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
