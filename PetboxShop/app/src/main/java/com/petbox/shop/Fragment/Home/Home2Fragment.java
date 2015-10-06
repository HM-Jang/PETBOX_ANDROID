package com.petbox.shop.Fragment.Home;


import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.petbox.shop.Adapter.List.GoodsListAdapter;
import com.petbox.shop.Adapter.Pager.BestGoodPagerAdapter;
import com.petbox.shop.DB.Constants;
import com.petbox.shop.Item.BestGoodInfo;
import com.petbox.shop.R;
import com.petbox.shop.Utility.Utility;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home2Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    PullToRefreshScrollView scView;

    ViewPager viewPager;
    PageIndicator indicator;
    BestGoodPagerAdapter bestGoodPagerAdapter;

    Thread timerThread;
    Handler handler;

    Boolean isRunning = true;

    LinearLayout linear_btn;
    Button btn_dog, btn_cat;

    ListView list_best, list_dc;
    ArrayList<BestGoodInfo> bestItemList;
    ArrayList<BestGoodInfo> dcItemList;
    GoodsListAdapter bestListAdapter;
    GoodsListAdapter dcListAdapter;

    int interval = Constants.AUTO_SLIDE_TIME;

    int mainColor = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Home2Fragment newInstance(String param1, String param2) {
        Home2Fragment fragment = new Home2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Home2Fragment() {
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mainColor = getResources().getColor(R.color.colorPrimary);

        bestItemList = new ArrayList<BestGoodInfo>();
        BestGoodInfo info[] = new BestGoodInfo[10];

        for(int i=0; i<10; i++){
            info[i] = new BestGoodInfo();

            info[i].name = "상품명\n"+i;
            info[i].rate = ""+i;
            info[i].origin_price = (i+1)+ "5,000";
            info[i].price = (i+1)+"0,000";
            info[i].rating = 3;
            info[i].rating_person = i;

            bestItemList.add(info[i]);
        }

        dcItemList = new ArrayList<BestGoodInfo>();
        BestGoodInfo info2[] = new BestGoodInfo[10];

        for(int i=0; i<10; i++){
            info2[i] = new BestGoodInfo();
            info2[i].name = "상품명\n"+i;
            info2[i].rate = ""+i;
            info2[i].origin_price = (i+1)+ "5,000";
            info2[i].price = (i+1)+"0,000";
            info2[i].rating = 3;
            info2[i].rating_person = i;

            dcItemList.add(info2[i]);
        }

        viewPager = (ViewPager)v.findViewById(R.id.pager_best_good);
        bestGoodPagerAdapter = new BestGoodPagerAdapter(getContext());
        viewPager.setAdapter(bestGoodPagerAdapter);

        CirclePageIndicator circlePageIndicator = (CirclePageIndicator)v.findViewById(R.id.indicator_best_good);
        indicator = circlePageIndicator;
        indicator.setViewPager(viewPager);

        circlePageIndicator.setPageColor(0xFF6d6d6d);   // Normal 원 색상
        circlePageIndicator.setFillColor(mainColor);   //선택된 원 색상
        circlePageIndicator.setStrokeColor(0x00000000); //테두리 INVISIBLE

        btn_dog = (Button)v.findViewById(R.id.btn_slide_dog);
        btn_dog.setOnClickListener(this);

        btn_cat = (Button)v.findViewById(R.id.btn_slide_cat);
        btn_cat.setOnClickListener(this);

        scView = (PullToRefreshScrollView) v.findViewById(R.id.sc_home);

        list_best = (ListView) v.findViewById(R.id.list_home_best);
        bestListAdapter = new GoodsListAdapter(getContext(), bestItemList);
        list_best.setAdapter(bestListAdapter);
        Utility.setListViewHeightBasedOnChildren(list_best);

        //int height = (getResources().getDimensionPixelSize(R.dimen.list))

        list_dc = (ListView) v.findViewById(R.id.list_home_dc);
        dcListAdapter = new GoodsListAdapter(getContext(), dcItemList);
        list_dc.setAdapter(dcListAdapter);
        Utility.setListViewHeightBasedOnChildren(list_dc);

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

    public void refreshBestListView(ArrayList<BestGoodInfo> itemList){
        bestItemList.clear();
        bestItemList = itemList;

        bestListAdapter = new GoodsListAdapter(getActivity().getApplicationContext(), bestItemList);
        list_best.setAdapter(bestListAdapter);
    }

    public void refreshDcListView(ArrayList<BestGoodInfo> itemList){
        dcItemList.clear();
        dcItemList = itemList;

        dcListAdapter = new GoodsListAdapter(getActivity().getApplicationContext(), dcItemList);
        list_dc.setAdapter(dcListAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.btn_slide_dog:
                Toast.makeText(getContext(), "강아지", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_slide_cat:
                Toast.makeText(getContext(), "고양이", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
