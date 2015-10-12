package com.petbox.shop;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.petbox.shop.Adapter.List.RecommendListAdapter;
import com.petbox.shop.Item.BestGoodInfo;
import com.petbox.shop.Utility.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class GoodInfoActivity extends AppCompatActivity implements View.OnClickListener{

    /* 상단 탭 */
    ImageButton ibtn_back,ibtn_search, ibtn_cart;

    ScrollView sc_main;

    /* 컨텐츠(상) */
    ImageView iv_good, iv_icon1, iv_icon2, iv_icon3; // 상품이미지, 상품특성 아이콘
    ImageButton ibtn_top, ibtn_zoom;    // 맨위로, 확대
    TextView tv_name, tv_rate, tv_price_dc, tv_price; //상품명, 할인율, 할인가격, 상품가격
    ImageButton ibtn_share; // 공유

    //타임할인
    FrameLayout frame_time; //타임할인 Section
    TextView tv_time;   // 남은 시간
    //String str_finished_time = "1444600000";
    long finished_time = 1444580000;    //타임할인 종료 시각
    Handler timerHandler;
    TimerThread timerThread;
    Boolean isRunning = true;

    //리뷰
    RatingBar rating;   // 별
    TextView tv_rating, tv_review_count;    // 89/100, 리뷰 개수
    int review_count = 400;

    /* 컨텐츠(중) */
    Button btn_intro, btn_review, btn_ship; // 상품소개, 리뷰보기, 상품/배송정보
    FrameLayout frame_middle_content;       // 컨텐츠
    ImageView iv_intro;

    /* 컨텐츠(하) */
    ListView listView; // 추천상품리스트
    RecommendListAdapter listAdapter;
    ArrayList<BestGoodInfo> mItemList;


    /* 하단탭 */

    //구매하기, 찜
    RelativeLayout relative_bottom;
    ImageButton ibtn_wish;
    Button btn_buy;
    Boolean isWish = false;
    ImageView iv_wish;
    WishImageThread wishThread;
    Handler wishHandler;

    //바로 구매하기, 장바구니 담기
    LinearLayout linear_bottom2;
    Button btn_buy_ok, btn_cart_ok;
    TextView tv_submenu_onoff; // 서브메뉴 on/off
    ListView list_select;       //상품 선택 및 추가옵션
    ListView list_select_good;  //선택된 상품

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_info);

        ibtn_back = (ImageButton) findViewById(R.id.ibtn_good_info_back);
        ibtn_back.setOnClickListener(this);

        ibtn_search = (ImageButton) findViewById(R.id.ibtn_good_info_search);
        ibtn_search.setOnClickListener(this);

        ibtn_cart = (ImageButton) findViewById(R.id.ibtn_good_info_cart);
        ibtn_cart.setOnClickListener(this);

        sc_main = (ScrollView) findViewById(R.id.sc_good_info);

        iv_good = (ImageView) findViewById(R.id.iv_good_info);
        iv_icon1 = (ImageView) findViewById(R.id.iv_good_info_icon1);
        iv_icon2 = (ImageView) findViewById(R.id.iv_good_info_icon2);
        iv_icon3 = (ImageView) findViewById(R.id.iv_good_info_icon3);

        ibtn_top = (ImageButton) findViewById(R.id.ibtn_good_info_top);
        ibtn_top.setOnClickListener(this);

        ibtn_zoom = (ImageButton) findViewById(R.id.ibtn_good_info_zoom);
        ibtn_zoom.setOnClickListener(this);

        tv_name = (TextView) findViewById(R.id.tv_good_info_name);
        tv_rate = (TextView)findViewById(R.id.tv_good_info_rate);
        tv_price_dc = (TextView)findViewById(R.id.tv_good_info_price_dc);
        tv_price = (TextView)findViewById(R.id.tv_good_info_price);
        ibtn_share = (ImageButton)findViewById(R.id.ibtn_good_info_share);
        ibtn_share.setOnClickListener(this);

        frame_time = (FrameLayout)findViewById(R.id.frame_good_info_top_time);
        tv_time = (TextView)findViewById(R.id.tv_good_info_time);
        timerHandler = new Handler();
        timerThread = new TimerThread();
        timerThread.start();

        rating = (RatingBar) findViewById(R.id.rating_good_info);
        tv_rating = (TextView)findViewById(R.id.tv_good_info_rating);
        tv_review_count = (TextView)findViewById(R.id.tv_good_info_review_count);
        tv_review_count.setText(review_count+"");



        btn_intro = (Button)findViewById(R.id.btn_good_info_intro);
        btn_intro.setOnClickListener(this);

        btn_review = (Button)findViewById(R.id.btn_good_info_review);
        btn_review.setOnClickListener(this);
        btn_review.setText("고객리뷰("+review_count+")");

        btn_ship = (Button)findViewById(R.id.btn_good_info_ship);
        btn_ship.setOnClickListener(this);

        frame_middle_content = (FrameLayout)findViewById(R.id.frame_good_info_middle_content);
        iv_intro = (ImageView)findViewById(R.id.iv_good_info_intro);

        mItemList = new ArrayList<BestGoodInfo>();

        for(int i=0; i<5; i++){
            BestGoodInfo item  = new BestGoodInfo();

            item.name = "상품명\n"+i;
            item.rate = ""+i;
            item.origin_price = (i+1)+ "5,000";
            item.price = (i+1)+"0,000";
            item.rating = 3;
            item.rating_person = i;

            mItemList.add(item);
        }

        listView = (ListView)findViewById(R.id.list_good_info_recommend);
        listAdapter = new RecommendListAdapter(this, mItemList);
        listView.setAdapter(listAdapter);

        Utility.setListViewHeightBasedOnChildren(listView);

        relative_bottom = (RelativeLayout) findViewById(R.id.relative_good_info_bottom);
        linear_bottom2 = (LinearLayout) findViewById(R.id.linear_good_info_bottom2);

        ibtn_wish = (ImageButton) findViewById(R.id.ibtn_good_info_wish);
        ibtn_wish.setOnClickListener(this);

        iv_wish = (ImageView)findViewById(R.id.iv_good_info_wish);
        wishHandler = new Handler();
        wishThread = new WishImageThread();

        if(isWish){ //찜한 상품일 시,
            ibtn_wish.setImageResource(R.drawable.info_wish_off);
            //iv_wish.setVisibility(View.VISIBLE);
        }

        btn_buy = (Button) findViewById(R.id.btn_good_info_buy);
        btn_buy.setOnClickListener(this);

        btn_buy_ok = (Button) findViewById(R.id.btn_good_info_buy_ok);
        btn_buy_ok.setOnClickListener(this);

        btn_cart_ok = (Button) findViewById(R.id.btn_good_info_cart_ok);
        btn_cart_ok.setOnClickListener(this);

        tv_submenu_onoff = (TextView) findViewById(R.id.tv_good_info_submenu_onoff);
        tv_submenu_onoff.setOnClickListener(this);

        list_select = (ListView) findViewById(R.id.list_good_info_select);
        list_select_good = (ListView) findViewById(R.id.list_good_info_select_good);


    }

    public void setFinishedTime(){
        //Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat sdfow = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
        String time = sdfow.format(new Date(System.currentTimeMillis()));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_good_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.ibtn_good_info_back:  // 뒤로
                Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ibtn_good_info_search:    // 검색
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ibtn_good_info_cart:  // 장바구니
                Toast.makeText(this, "cart", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ibtn_good_info_top:   // 맨위로
                sc_main.fullScroll(View.FOCUS_UP);
                //Toast.makeText(this, "top", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ibtn_good_info_zoom:  //확대보기
                Toast.makeText(this, "zoom", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ibtn_good_info_share: //공유하기
                Toast.makeText(this, System.currentTimeMillis()+"", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_good_info_intro:  //상품소개
                Toast.makeText(this, "intro", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_good_info_review: //
                Toast.makeText(this, "review", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_good_info_ship:
                Toast.makeText(this, "ship", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ibtn_good_info_wish:

                if(isWish){ // 찜한상품 등록되어있을 떄, 클릭시(찜취소)
                    isWish = false;
                    iv_wish.setImageResource(R.drawable.info_wish_cancle_image);
                    ibtn_wish.setImageResource(R.drawable.info_wish_off);

                }else{  //찜등록
                    isWish = true;
                    iv_wish.setImageResource(R.drawable.info_wish_image);
                    ibtn_wish.setImageResource(R.drawable.info_wish_on);
                }
                iv_wish.setVisibility(View.VISIBLE);
                wishThread.startThread();
                //Toast.makeText(this, "wish", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_good_info_buy:
                linear_bottom2.setVisibility(View.VISIBLE);
                relative_bottom.setVisibility(View.INVISIBLE);
                //Toast.makeText(this, "buy", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_good_info_buy_ok:
                Toast.makeText(this, "buy_ok", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_good_info_cart_ok:
                Toast.makeText(this, "cart_ok", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_good_info_submenu_onoff:
                linear_bottom2.setVisibility(View.GONE);
                relative_bottom.setVisibility(View.VISIBLE);
                break;

        }
    }

    // 찜 등록 이미지 3초 타이머
    class WishImageThread extends Thread{
        Boolean isRunning = false;
        int times = 0;

        public WishImageThread(){}

        public void startThread(){
            if(!isRunning){

                if(!this.isAlive()){
                    this.start();
                }

                isRunning = true;
            }
        }

        public void stopThread(){
            isRunning = false;
            //this.interrupt();
            times = 0;
            //this.stop();
        }

        public void run(){
            super.run();
            while(isRunning){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                wishHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        times++;

                        if(times>=2){
                            iv_wish.setVisibility(View.GONE);
                            stopThread();

                        }
                    }
                });
            }

        }

    }

    //타임할인 타이머
    class TimerThread extends Thread{

        Boolean isRunning = true;

        public TimerThread(){}

        public void stopThread(){
            isRunning = false;
        }

        public void run(){
            super.run();
            while(isRunning){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                timerHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        long remendTime = finished_time - (System.currentTimeMillis()/1000);

                        System.out.println("remendTime : " + remendTime + "\ncurrentTimeMillis : " + (System.currentTimeMillis()/1000));
                        String timeTxt = "";

                        if(remendTime > 0 ){    // 시간 남아 있을때 (1초까지)
                            int day = (int) (remendTime/60/60/24);
                            int hour = (int) (remendTime/60/60);
                            int min = (int) (remendTime/60)%60;
                            int sec = (int) (remendTime%60);

                            timeTxt = "남은 시간 : ";

                            if(day >0)
                                timeTxt += day + "일 ";

                            if(hour>0)
                                timeTxt += hour + "시간 ";

                            if(min>0)
                                timeTxt += min +"분 ";

                            timeTxt += sec +"초";

                        }else if(remendTime <= 0){  //시간 종료 됬을 때.
                            timeTxt = "종료되었습니다";
                            tv_time.setTextColor(0xffff0000);
                            isRunning = false;
                        }
                        tv_time.setText(timeTxt);
                    }
                });

            }
        }

    }

}
