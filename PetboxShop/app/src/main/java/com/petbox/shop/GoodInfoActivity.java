package com.petbox.shop;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import com.petbox.shop.Adapter.List.OptionListAdapter;
import com.petbox.shop.Adapter.List.RecommendListAdapter;
import com.petbox.shop.Adapter.List.SelectOptionListAdpater;
import com.petbox.shop.Adapter.List.SelectedGoodListAdapter;
import com.petbox.shop.DB.Constants;
import com.petbox.shop.Delegate.GoodInfoDelegate;
import com.petbox.shop.Delegate.HttpPostDelegate;
import com.petbox.shop.Delegate.NumberPickerDelegate;
import com.petbox.shop.Http.HttpPostManager;
import com.petbox.shop.Item.AddOptionInfo;
import com.petbox.shop.Item.BestGoodInfo;
import com.petbox.shop.Item.GoodOptionInfo;
import com.petbox.shop.Item.OptionInfo;
import com.petbox.shop.Utility.Utility;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;


public class GoodInfoActivity extends AppCompatActivity implements View.OnClickListener, NumberPickerDelegate, HttpPostDelegate{

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
    long finished_time = 1444740000;    //타임할인 종료 시각
    Handler timerHandler;
    TimerThread timerThread;
    Boolean isRunning = true;

    //리뷰
    RatingBar rating;   // 별
    FrameLayout frame_review; // X개의 리뷰가 있습니다.
    TextView tv_rating, tv_review_count;    // 89/100, 리뷰 개수
    int review_count = 400;

    /* 컨텐츠(중) */
    Button btn_intro, btn_review, btn_ship; // 상품소개, 리뷰보기, 상품/배송정보
    FrameLayout frame_middle_content;       // 컨텐츠
    //ImageView iv_intro;
    //WebView webView;

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

    LinearLayout linear_list1;    // 구매, 추가옵션
    LinearLayout linear_list_default, linear_list_add;
    ListView list_default;       //구매옵션
    ListView list_add; // 추가 옵션
    ListView list_select_good;  //선택했던 상품 정보
    ListView list_select_item; // 선택한 옵션의 리스트

    int option_count = 0;   // 구매옵션 개수
    boolean isDoubleOption = false; // 2단 연계옵션 true
    boolean isNoOption = false; // 아무 옵션 없을 시,
    int add_option_count = 0;   // 추가옵션 개수

    int selected_num = 0;   // 구매옵션 내의 항목
    int selected_add_num = 0; // 추가옵션 내의 항목
    int selected_what = 0; // 0: 구매옵션 선택(최근), 1: 추가옵션 선택(최근)

    ArrayList<String> arrOptionName;    //옵션명
    String optionOriginFirstName = "";
    SelectOptionListAdpater listOptionAdapter;
    ArrayList<String> arrAddOptionName; //추가옵션명
    SelectOptionListAdpater listAddOptionAdapter;

    ArrayList<ArrayList<GoodOptionInfo>> arrOptionList;
    //ArrayList<ArrayList<GoodOptionInfo>> arrAddOptionList;
    OptionListAdapter defaultOptionListAdapter;
    OptionListAdapter addOptionListAdapter;

    ArrayList<GoodOptionInfo> selectedGoodList; //선택된
    SelectedGoodListAdapter selectedGoodListAdapter;



    int order_price = 0;    // 주문금액
    TextView tv_all_price;

    /* Data */
    int goodsno = 0;	// 상품번호
    String goodsnm = "";	//상품명

    /* 상품/배송 정보 */
    String goods_status = "";	//상품상태
    int goodscd = 0;	//제품코드
    String origin = "";	// 원산지
    int reserve = 0;	//적립금
    int point = 0;	//리뷰점수(고객선호도)


    String img_i = "";	// 상품 이미지
    int icon = 0;	//상품 특성아이콘
    String option_name = "";	// 구매옵션 이름들
    String opttype = "";	// 구매 옵션(single : 일체형, double : 분리형)
    String option_value = "";	//구매옵션 value
    String addoptnm = "";	//추가 옵션 이름들
    String usestock = "";	// 재고관리 사용(o : 재고관리사용, "":재고관리미사용)
    int totstock = 0; // 재고 개수
    int min_ea = 0; // 최소 구매수량
    int max_ea = 0; //최대 구매수량
    int open_mobile = 0; // 상품노출여부(O : 노출x, 1: 노출o)

    ArrayList<Integer> iconList;

    int goods_price = 0; //상품판매가
    int goods_consumer = 0; // 상품 소비자가

    int use_option = 0; // 옵션 사용여부
    int review_cnt = 0; // 리뷰 개수

    int use_goods_discount = 0; //타임할인 할인율
    int ts_amount = 0; // 타임할인 할인 금액

    long discount_time = 0; // 타임할인 종료시간

    int optionNamesSize = 0; //ex) 구매옵션 갯수
    ArrayList<String> optionNames = new ArrayList<String>(); // 추가 옵션 스피너 텍스트들
    ArrayList<String> option1List = new ArrayList<String>();    // 1차 구매옵션
    ArrayList<ArrayList<OptionInfo>> arrOption2List = new ArrayList<ArrayList<OptionInfo>>();   // 2차 구매옵션

    int addOptionNamesSize = 0; // 추가 옵션갯수
    ArrayList<String> addOptionNames = new ArrayList<String>(); // 추가 옵션 스피너 텍스트들
    ArrayList<ArrayList<AddOptionInfo>>  arrAddOptionList = new ArrayList<ArrayList<AddOptionInfo>>();// 추가옵션 스피너 누르면 나오는 항목

    int firstOptionSelected = 0;    // 2단 구매옵션 시, 1차 구매옵션 클릭했던 position

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

        rating = (RatingBar) findViewById(R.id.rating_good_info);
        rating.setMax(5);   //별점 최대 5개

        tv_rating = (TextView)findViewById(R.id.tv_good_info_rating);
        frame_review = (FrameLayout)findViewById(R.id.frame_good_info_top_review);
        tv_review_count = (TextView)findViewById(R.id.tv_good_info_review_count);
        tv_review_count.setText(review_count+"");

        btn_intro = (Button)findViewById(R.id.btn_good_info_intro);
        btn_intro.setOnClickListener(this);

        btn_review = (Button)findViewById(R.id.btn_good_info_review);
        btn_review.setOnClickListener(this);
        btn_review.setText("고객리뷰(" + review_count + ")");

        btn_ship = (Button)findViewById(R.id.btn_good_info_ship);
        btn_ship.setOnClickListener(this);

        frame_middle_content = (FrameLayout)findViewById(R.id.frame_good_info_middle_content);
        //iv_intro = (ImageView)findViewById(R.id.iv_good_info_intro);

       // webView = (WebView) findViewById(R.id.webview_good_info);
        //webView.getSettings().setJavaScriptEnabled(true);

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

        linear_list1 = (LinearLayout) findViewById(R.id.linear_good_info_list1);

        linear_list_default = (LinearLayout) findViewById(R.id.linear_good_info_list_default);
        list_default = (ListView) findViewById(R.id.list_good_info_default);
        list_default.setOnItemClickListener(new DefaultOptionItemClickListener());

        linear_list_add = (LinearLayout) findViewById(R.id.linear_good_info_list_add);
        list_add = (ListView) findViewById(R.id.list_good_info_add);
        list_add.setOnItemClickListener(new AddOptionItemClickListener());

        list_select_good = (ListView) findViewById(R.id.list_good_info_select_good);
        list_select_good.setOnItemClickListener(new List2ItemClickListener());

        list_select_item = (ListView) findViewById(R.id.list_good_info_select_item);
        list_select_item.setOnItemClickListener(new List3ItemClickListener());

        tv_all_price = (TextView) findViewById(R.id.tv_good_info_all_price);

        arrOptionName = new ArrayList<String>();
        arrAddOptionName = new ArrayList<String>();

        arrOptionList = new ArrayList<ArrayList<GoodOptionInfo>>();
        //arrAddOptionList = new ArrayList<ArrayList<GoodOptionInfo>>();

        selectedGoodList = new ArrayList<GoodOptionInfo>();
        iconList = new ArrayList<Integer>();

        init();
    }



    public void init(){

        boolean bool_option = false;
        boolean bool_add_option = false;

        /* JSON Data 불러와서 세팅, option_count, add_optoin_count */

        JsonParseTask task = new JsonParseTask(Constants.HTTP_URL_GOOD_INFO);
        task.execute();

        //타임할인 쓰레드 초기화
        timerHandler = new Handler();
        timerThread = new TimerThread();
        timerThread.start();

        //추천상품 리스트 초기화
        listAdapter = new RecommendListAdapter(this, mItemList);
        listView.setAdapter(listAdapter);

        Utility.setListViewHeightBasedOnChildren(listView);


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
                //Toast.makeText(this, "buy_ok", Toast.LENGTH_SHORT).show();
                HttpPostManager httpPostManager = new HttpPostManager(this);
                httpPostManager.start();

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

    public void addSelectedGoodList(int position){
        GoodOptionInfo item = new GoodOptionInfo();

        if(selected_what == 0) {    //구매옵션
            if(optionNamesSize == 1){   //구매옵션 한개일때
                OptionInfo optionItem = arrOption2List.get(selected_num).get(position);

                item.sno = optionItem.sno;
                item.name = optionItem.opt1;
                item.count = optionItem.stock;
                item.price = optionItem.price;
                item.order_count = 0;
            }else if(optionNamesSize == 2){

                if(selected_num == 0){  //구매옵션 1차
                    optionNames.remove(0);
                    optionNames.add(0, option1List.get(position));

                    firstOptionSelected = position;

                    listOptionAdapter = new SelectOptionListAdpater(getApplicationContext(), optionNames);  // 구매옵션1 선택한 것으로 텍스트 변경
                    list_default.setAdapter(listOptionAdapter);
                    return;

                }else if(selected_num == 1){    //구매옵션 2차
                    OptionInfo optionItem = arrOption2List.get(firstOptionSelected).get(position);

                    item.sno = optionItem.sno;
                    item.name = optionItem.opt1 + "/" + optionItem.opt2;
                    item.count = optionItem.stock;
                    item.price = optionItem.price;
                    item.order_count = 0;

                }

            }


        }
        else if(selected_what == 1) {   //추가옵션
            System.out.println("추가옵션 클릭");
            AddOptionInfo addOptionItem = arrAddOptionList.get(selected_add_num).get(position);

            item.sno = addOptionItem.sno;
            item.name = addOptionItem.opt;
            item.count = 999;
            item.price = addOptionItem.addprice;
            item.order_count = 0;
            item.isAddOption = true;
            item.optionName = addOptionItem.optionName;
        }

        if(!selectedGoodList.isEmpty()){
            if(isContainItem(selectedGoodList, item)){
                Toast.makeText(this, "아이템 항목이 이미 들어있습니다.", Toast.LENGTH_SHORT).show();
            }else{
                selectedGoodList.add(item);

                selectedGoodListAdapter = new SelectedGoodListAdapter(this, selectedGoodList, this);

                if(optionNamesSize == 0)
                    selectedGoodListAdapter.setMode(1);

                list_select_good.setAdapter(selectedGoodListAdapter);
            }
        }else{

            if(selected_what == 1){
                Toast.makeText(this, "구매옵션 먼저 선택해주세요.", Toast.LENGTH_SHORT).show();
            }else{
                selectedGoodList.add(item);

                selectedGoodListAdapter = new SelectedGoodListAdapter(this, selectedGoodList, this);

                if(optionNamesSize == 0)
                    selectedGoodListAdapter.setMode(1);

                list_select_good.setAdapter(selectedGoodListAdapter);
            }
        }
    }

    public void holdSelectedIGoodList(GoodOptionInfo item){
        selectedGoodList.add(0, item);

        selectedGoodListAdapter = new SelectedGoodListAdapter(this, selectedGoodList, this);
        selectedGoodListAdapter.setMode(1);

        list_select_good.setAdapter(selectedGoodListAdapter);
    }

    public boolean isContainItem(ArrayList<GoodOptionInfo> itemList, GoodOptionInfo item){

        for(int i=0; i< itemList.size(); i++){
            GoodOptionInfo optionItem = itemList.get(i);

            if(optionItem.name.equals(item.name)){
                return true;
            }
        }

        return false;



    }

    @Override
    public void clickIncrease(HashMap<String, Integer> params) {
        //Toast.makeText(getApplicationContext(), "+버튼", Toast.LENGTH_SHORT).show();
        int price = params.get("price");
        order_price += price;

        tv_all_price.setText("총상품가 : " + order_price + "원");
    }

    @Override
    public void clickDecrease(HashMap<String, Integer> params) {
        //Toast.makeText(getApplicationContext(), "-버튼", Toast.LENGTH_SHORT).show();
        int price = params.get("price");
        order_price -= price;

        tv_all_price.setText("총상품가 : " + order_price + "원");
    }

    @Override
    public void deleteItem(int price) {
        order_price -= price;
        tv_all_price.setText("총상품가 : " + order_price + "원");
    }

    @Override
    public void setNum(HashMap<String, Integer> params) {

        int price = params.get("price");
        int before_order_count = params.get("before_order_count");
        int order_count = params.get("order_count");

        if(before_order_count > order_count){  // 5 -3 = 2*price를 all_price에서 뺌

            int minus_price = before_order_count - order_count;
            order_price -= minus_price * price;
        }else if(before_order_count > order_count){  //
            int plus_price = order_count - before_order_count;
            order_price += plus_price * price;
        }else if(before_order_count == order_count){

        }

        tv_all_price.setText("총상품가 : " + order_price + "원");
    }

    @Override
    public void prevRunningHttpPost() {

    }

    @Override
    public String getPostUrl() {
        return Constants.HTTP_URL_GO_CART;
    }

    @Override
    public List<NameValuePair> getNameValuePairs() {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        /* 고정값 */

        nameValuePairs.add(new BasicNameValuePair("mode", "addItem"));
        nameValuePairs.add(new BasicNameValuePair("goodsno", Integer.toString(goodsno)));
        //nameValuePairs.add(new BasicNameValuePair("goodsno", "2755"));
        nameValuePairs.add(new BasicNameValuePair("goodsCoupon", "0"));

        /*
        nameValuePairs.add(new BasicNameValuePair("min_ea", "1"));
        nameValuePairs.add(new BasicNameValuePair("max_ea", "200"));

        nameValuePairs.add(new BasicNameValuePair("realprice", ""+order_price));
        nameValuePairs.add(new BasicNameValuePair("ea", "1"));

        System.out.println("realprice : "+ order_price);
        */

        int count = 0;

        for(int i=0; i<selectedGoodList.size(); i++) {
            GoodOptionInfo item = selectedGoodList.get(i);

            if (item.order_count == 0)   // 주문 갯수 0개 패스
                continue;

            System.out.println("goodsno : " + goodsno);

            if (!item.isAddOption) {  //구매옵션일경우
                /*
                nameValuePairs.add(new BasicNameValuePair("multi_ea[0]", "1"));
                nameValuePairs.add(new BasicNameValuePair("multi_opt[0][]", "나우스몰브리드퍼피2.72kg"));
                */
                nameValuePairs.add(new BasicNameValuePair("multi_ea[" + count + "]", Integer.toString(item.order_count)));
                nameValuePairs.add(new BasicNameValuePair("multi_opt[" + count + "][]", item.name));

                System.out.println("multi_ea[" + count + "]" + " : " + item.order_count);
                System.out.println("multi_opt[" + count + "][]" + " : " + item.name);
                count++;
            } else {
                String param = item.sno + "^" + item.optionName + "^" + item.name;

                if (item.order_count == 1)
                    param += "^" + item.price;
                else
                    param += "#" + item.order_count + "^" + item.price;
                nameValuePairs.add(new BasicNameValuePair("multi_addopt[0][]", param));

                System.out.println("multi_addopt[0][]" + " : " + param);
            }
        }
        return nameValuePairs;
    }

    @Override
    public void runningHttpPost() {

    }

    @Override
    public void afterRunningHttpPost(int responseCode) {
        System.out.println("장바구니 요청[responseCode] : " + responseCode);
    }

    class JsonParseTask extends AsyncTask<String, Void, String>{

        InputStream is = null;
        String url = "";

        public JsonParseTask(String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } catch (IOException e) {
                e.printStackTrace();
            }

            StringBuilder sb = new StringBuilder();

            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line = null;

                while((line = reader.readLine()) != null){
                    sb.append(line);
                }

            }catch(Exception e){
                e.printStackTrace();
            }

            return sb.toString();
        }

        @Override
        protected void onPostExecute(String result) {


            try{
                System.out.println(result);
                /*
                JSONObject mainObject = new JSONObject(result);
                JSONObject jsonObject = mainObject.getJSONObject("");
                */

                JSONArray jsonArray = new JSONArray(result);
                JSONObject main = jsonArray.getJSONObject(0);
                JSONObject data = main.getJSONObject("data");
                //JSONArray arr_data = obj.getJSONArray("data");

                /*
                for(int i=0; i<arr_data.length(); i++){
                    System.out.println("체크 : " + arr_data.getString(i) + "\n");
                }
                */
                goodsno = Integer.parseInt(data.getString("goodsno"));  //상품번호
                goodsnm = data.getString("goodsnm"); //상품명
                goods_status = data.getString("goods_status");   // 상품상태 ex) 신상품
                goodscd = Integer.parseInt(data.getString("goodscd"));  //제품코드
                origin = data.getString("origin");   // 원산지
                reserve = Integer.parseInt(data.getString("reserve"));// 적립금
                point = Integer.parseInt(data.getString("point"));   // 리뷰점수 (고객선호도)

                String longdesc = data.getString("longdesc");

                //webView.loadData(longdesc, "text/html", "UTF-8");
                System.out.println("longdesc : " + longdesc);

                img_i = data.getString("img_i");// 상품 이미지
                int icon = Integer.parseInt(data.getString("icon"));    //상품 특성 아이콘
                iconList = parseValidBinary(icon);

                option_name = data.getString("option_name"); // 구매옵션 이름들
                opttype = data.getString("opttype"); // 구매 옵션(single : 일체형, double : 분리형)
                option_value = data.getString("option_value");   // 옵션 Value
                addoptnm = data.getString("addoptnm");   // 추가 옵션 이름들
                usestock = data.getString("usestock");   // 재고관리 사용 (o : 재고관리사용, "": 재고관리미사용)
                totstock = Integer.parseInt(data.getString("totstock")); //  재고 갯수
                min_ea = Integer.parseInt(data.getString("min_ea"));    // 최소 구매수량
                max_ea = Integer.parseInt(data.getString("max_ea"));    // 최대 구매수량
                open_mobile = Integer.parseInt(data.getString("open_mobile"));  // 상품 노출 여부(0 : 노출x, 1: : 노출o)

                goods_price = Integer.parseInt(data.getString("goods_price"));  // 상품 판매가
                goods_consumer = Integer.parseInt(data.getString("goods_consumer"));    //상품 소비자가

                use_option = Integer.parseInt(data.getString("use_option"));    //옵션 사용여부
                use_goods_discount = Integer.parseInt(data.getString("use_goods_discount"));  // 해당 옵션 상품 할인
                ts_amount = Integer.parseInt(data.getString("ts_amount"));  // 타임할인 할인 금액

                review_cnt = Integer.parseInt(data.getString("review_cnt"));

                String str_discount_time = data.getString("discount_time");

                if(!str_discount_time.isEmpty())
                    discount_time = Long.parseLong(str_discount_time);

                String[] temp_optionNames = option_name.split("[|]");

                if(temp_optionNames[0].isEmpty())
                    optionNamesSize = 0;
                else
                    optionNamesSize = temp_optionNames.length;    // 방석선택 | 색상/사이즈

                String optionName = ""; // 구매옵션 1개

                if(optionNamesSize > 0){
                    //JSONObject opt = jsonObject.getJSONObject("opt");
                    //JSONObject opt = main.getJSONObject("opt");

                    //JSONArray jsonArray = new JSONArray(result);
                    //JSONObject main = jsonArray.getJSONObject(0);
                    //JSONObject data = main.getJSONObject("data");

                    for(int i=0; i<optionNamesSize; i++){
                        optionNames.add(temp_optionNames[i]);
                    }

                    JSONArray jsonArr_opt = main.getJSONArray("opt");

                    String[] temp_option1List = option_value.split("[|]")[0].split(",");   // 구매옵션1에 들어간 항목(0번 인덱스) 파싱

                    //파싱해서 나온 1차 구매옵션 리스트 설정
                    for(int i=0; i < temp_option1List.length; i++){

                        option1List.add(temp_option1List[i].trim());   // string[] -> ArrayList
                        //System.out.println("파싱 : " + "[" + i + "]" + temp_option1List[i]);

                        ArrayList<OptionInfo> itemList = new ArrayList<OptionInfo>();
                        arrOption2List.add(itemList);
                    }


                    //구매옵션 0개 혹은 1개
                    if(optionNamesSize == 0 || optionNamesSize == 1){
                        for(int i=0; i<jsonArr_opt.length(); i++){
                            JSONObject opt = jsonArr_opt.getJSONObject(i);

                            OptionInfo item = new OptionInfo();
                            item.sno = Integer.parseInt(opt.getString("sno"));
                            item.opt1 = opt.getString("opt1");
                            item.opt2 = opt.getString("opt2");
                            item.stock = Integer.parseInt(opt.getString("stock"));
                            item.price = Integer.parseInt(opt.getString("price"));

                            if(optionNamesSize == 1){
                                arrOption2List.get(0).add(item);
                                System.out.println("구매옵션1 : " + item.opt1);
                            }else{
                                int position = -1;

                                for(int j=0; j< temp_option1List.length; j++ ){
                                    if(temp_option1List.equals(item.opt1)){
                                        position = i;
                                        break;
                                    }
                                }

                                if(position > -1) {
                                    arrOption2List.get(position).add(item);
                                    System.out.println("["+position+"]"+"구매옵션 : " + item.opt1);
                                }else{
                                    System.out.println("position is -1");
                                }
                            }
                        }
                    }else{//구매옵션 2개

                        optionOriginFirstName = optionNames.get(0);

                        for(int i=0; i<jsonArr_opt.length(); i++){
                            JSONObject opt = jsonArr_opt.getJSONObject(i);

                            OptionInfo item = new OptionInfo();
                            item.sno = Integer.parseInt(opt.getString("sno"));
                            item.opt1 = opt.getString("opt1");
                            item.opt2 = opt.getString("opt2");
                            item.stock = Integer.parseInt(opt.getString("stock"));
                            item.price = Integer.parseInt(opt.getString("price"));

                            int position = -1;

                            for(int j=0; j< option1List.size(); j++ ){
                                //System.out.println("비교 : "+ option1List.get(j) + "!=" + item.opt1);
                                if(option1List.get(j).equals(item.opt1)){

                                    position = j;
                                    break;
                                }
                            }

                            if(position > -1) {
                                arrOption2List.get(position).add(item);
                                //System.out.println("["+position +": "+ item.opt1 +"]"+"구매옵션2 : " + item.opt2 + "// 가격 : " + item.price);
                            }else{
                                System.out.println("position is -1 : " + item.opt1 + "// "+ item.opt2);
                            }
                        }
                    }
                }

                String[] temp_addOptionNames = addoptnm.split("[|]");

                if(temp_addOptionNames[0].isEmpty())
                    addOptionNamesSize = 0;
                else
                    addOptionNamesSize = temp_addOptionNames.length;    //추가 옵션갯수

                if(addOptionNamesSize > 0){
                    //JSONObject addopt = jsonArray.getJSONObject(2);
                     //JSONObject addopt = main.getJSONObject("addopt");

                    for(int i=0; i< addOptionNamesSize; i++){

                        String[] str = temp_addOptionNames[i].split("\\^\\^S"); //^^S 파싱
                        //String addOptionItem = temp_addOptionNames[i].split("^^S");
                        //System.out.println("addOptionItem : "+ str[0]);

                        //addOptionNames.add(temp_addOptionNames[i]);
                        addOptionNames.add(str[0]);
                    }

                    JSONArray jsonArr_addopt = main.getJSONArray("addopt");


                    for(int i=0; i < addOptionNamesSize; i++){  //미리 세팅
                        ArrayList<AddOptionInfo> addOptionList = new ArrayList<AddOptionInfo>();
                        arrAddOptionList.add(addOptionList);
                    }

                    for(int i =0; i< jsonArr_addopt.length(); i++){
                        JSONObject addopt = jsonArr_addopt.getJSONObject(i);

                        AddOptionInfo item = new AddOptionInfo();
                        item.sno = Integer.parseInt(addopt.getString("sno"));
                        item.step = Integer.parseInt(addopt.getString("step"));
                        item.opt = addopt.getString("opt");
                        item.addprice = Integer.parseInt(addopt.getString("addprice"));
                        item.optionName = addOptionNames.get(item.step);

                        //addOptionNames.add(item.opt);
                        arrAddOptionList.get(item.step).add(item);
                    }
                }


                for(int i=1; i<=iconList.size(); i++){
                    int id = getResources().getIdentifier("icon"+iconList.get(i-1),"drawable" , "com.petbox.shop");

                    System.out.println("아이콘 세팅");

                    if(i==1){
                        iv_icon1.setImageResource(id);
                        iv_icon1.setVisibility(View.VISIBLE);
                    }else if(i==2){
                        iv_icon2.setImageResource(id);
                        iv_icon2.setVisibility(View.VISIBLE);
                    }else if(i==3){
                        iv_icon3.setImageResource(id);
                        iv_icon3.setVisibility(View.VISIBLE);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            tv_name.setText(goodsnm);   // 상품명 설정

            tv_review_count.setText(review_cnt + "");
            btn_review.setText("고객리뷰(" + review_cnt + ")");
            rating.setRating(point);
            finished_time = discount_time;

            tv_rating.setText((point*20) +"/100");

            if(review_cnt == 0)
                frame_review.setVisibility(View.GONE);

            int output_price = 0;
            double rate = ((double)(goods_consumer-goods_price)/goods_consumer) * 100;
            int dc_rate = (int) Math.ceil(rate) + use_goods_discount;

            if(discount_time == 0){
                output_price = goods_price;
                frame_time.setVisibility(View.GONE);

            }else if(discount_time > 0){
                long isAliveTimeDC = discount_time - (System.currentTimeMillis()/1000); //타임할인 상태 여부

                if(isAliveTimeDC > 0 ) { // 타임할인 상태
                    output_price = goods_price - ts_amount;
                    dc_rate += use_goods_discount;

                }else {  //타임할인 x
                    output_price = goods_price;
                }
            }

            tv_price_dc.setText(output_price+ "원");  //판매가 설정
            tv_price.setText(goods_consumer + "원");   //소비자가

            if (dc_rate == 0) {
                tv_rate.setText("펫박스가");
                tv_price.setVisibility(View.INVISIBLE);
            }else
            tv_rate.setText(dc_rate + "%");

            boolean bool_option = false;
            boolean bool_add_option = false;

            // 구매옵션이 없을 경우, 해당 상품을 리스트2에 추가
            if(optionNamesSize == 0) {
                linear_list_default.setVisibility(View.GONE);
                bool_option = true;

                GoodOptionInfo item = new GoodOptionInfo();

                item.name = goodsnm;
                item.price = output_price;
                item.count = 999;
                item.order_count = 0;

                holdSelectedIGoodList(item);


            }else{
                listOptionAdapter = new SelectOptionListAdpater(getApplicationContext(), optionNames);
                list_default.setAdapter(listOptionAdapter);
            }

            if(addOptionNamesSize == 0) {
                linear_list_add.setVisibility(View.GONE);
                bool_add_option = true;
            }else{
                listAddOptionAdapter = new SelectOptionListAdpater(getApplicationContext(), addOptionNames);
                list_add.setAdapter(listAddOptionAdapter);
            }
        }
    }

    // icon값에서 숫자 추출
    public ArrayList<Integer> parseValidBinary(int icon){
        ArrayList<Integer> itemList = new ArrayList<Integer>();

        for(int i=0; i<10; i++){
            int check = (int)Math.pow(2, i);

            if((check&icon) == check){
                System.out.println("아이콘 추출 : " + check);
                itemList.add(check);
            }
        }
        return itemList;
    }


    // 상품, 옵션
    class DefaultOptionItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            selected_num = position;
            selected_what = 0;  // 구매옵션 선택
            if(optionNamesSize == 1){
                //defaultOptionListAdapter = new OptionListAdapter(getApplicationContext(), convertSimpleOptionInfo(option1List));
                defaultOptionListAdapter = new OptionListAdapter(getApplicationContext(), convertOptionInfo(arrOption2List.get(0), 1));
                list_select_item.setAdapter(defaultOptionListAdapter);
            }else if(optionNamesSize == 2){
                if(selected_num == 0){ //1차 옵션
                    defaultOptionListAdapter = new OptionListAdapter(getApplicationContext(), convertSimpleOptionInfo(option1List));
                    defaultOptionListAdapter.setMode(1);    // 심플모드

                }else if(selected_num == 1){ // 2차 구매옵션

                    if(optionNames.get(0).equals(optionOriginFirstName)){   // 1차 구매옵션 아무것도 선택하지 않았다면
                        Toast.makeText(getApplicationContext(), "첫번째 구매옵션을 선택해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(!optionNames.get(0).equals(optionOriginFirstName)){  // 1차 구매옵션 선택 완료.

                        defaultOptionListAdapter = new OptionListAdapter(getApplicationContext(), convertOptionInfo(arrOption2List.get(firstOptionSelected), 2));
                        System.out.println("firstOptionSelected : " + firstOptionSelected);
                        for(int i=0; i<arrOption2List.get(position).size(); i++){
                            System.out.println(arrOption2List.get(position).get(i).opt2);
                        }

                        /*
                        int pos = option1List.indexOf(optionNames.get(0));

                        if(pos == -1)
                            Toast.makeText(getApplicationContext(), "1차구매옵션에서 찾을 수 없음.", Toast.LENGTH_SHORT).show();
                            return;
                        else{
                            defaultOptionListAdapter = new OptionListAdapter(getApplicationContext(), convertOptionInfo(arrOption2List.get(position)));
                        }
                        */
                    }
                }
                list_select_item.setAdapter(defaultOptionListAdapter);
            }
            linear_list1.setVisibility(View.GONE);
            list_select_good.setVisibility(View.GONE);
            list_select_item.setVisibility(View.VISIBLE);
        }
    }

    public ArrayList<GoodOptionInfo> convertSimpleOptionInfo(ArrayList<String> optionList){
        ArrayList<GoodOptionInfo> itemList = new ArrayList<GoodOptionInfo>();

        for(int i=0; i < optionList.size(); i++){
            String optionName = optionList.get(i);
            GoodOptionInfo item = new GoodOptionInfo();

            item.name = optionName;

            System.out.println("구매옵션 1 : " + item.name);

            itemList.add(item);
        }

        return itemList;
    }

    // OptionInfo -> GoodOptionInfo, praseNum[ 1: opt1, 2: opt2)
    public ArrayList<GoodOptionInfo> convertOptionInfo(ArrayList<OptionInfo> optionList, int parseNum){
        ArrayList<GoodOptionInfo> itemList = new ArrayList<GoodOptionInfo>();

        for(int i=0; i < optionList.size(); i++){
            OptionInfo optionItem = optionList.get(i);
            GoodOptionInfo item = new GoodOptionInfo();

            if(parseNum == 1)
                item.name = optionItem.opt1;
            else if(parseNum == 2)
                item.name = optionItem.opt2;

            item.sno = optionItem.sno;
            item.count = optionItem.stock;
            item.price = optionItem.price;
            item.dc_price = goods_price - optionItem.price; // 할인 금액
            item.order_count = 0;

            itemList.add(item);
        }
        return itemList;
    }

    public ArrayList<GoodOptionInfo> convertAddOptionInfo(ArrayList<AddOptionInfo> addOptionList){
        ArrayList<GoodOptionInfo> itemList = new ArrayList<GoodOptionInfo>();

        for(int i=0; i < addOptionList.size(); i++){
            AddOptionInfo addOptionItem = addOptionList.get(i);
            GoodOptionInfo item = new GoodOptionInfo();

            item.sno = addOptionItem.sno;
            item.name = addOptionItem.opt;
            item.count = 999;
            item.price = addOptionItem.addprice;
            item.order_count = option_count;
            item.isAddOption = true;
            item.optionName = addOptionItem.optionName;

            itemList.add(item);
        }
        return itemList;
    }

    // 상품, 옵션
    class AddOptionItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            linear_list1.setVisibility(View.GONE);
            list_select_good.setVisibility(View.GONE);
            list_select_item.setVisibility(View.VISIBLE);

            selected_add_num = position;
            selected_what = 1;  // 구매옵션 선택

            addOptionListAdapter = new OptionListAdapter(getApplicationContext(), convertAddOptionInfo(arrAddOptionList.get(position)));
            list_select_item.setAdapter(addOptionListAdapter);
        }
    }




    // 상품 정보(가격, 수량)
    class List2ItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        }
    }

    // 상품 or 옵션 리스트
    class List3ItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            linear_list1.setVisibility(View.VISIBLE);
            list_select_good.setVisibility(View.VISIBLE);
            list_select_item.setVisibility(View.GONE);

            addSelectedGoodList(position);

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

                        //System.out.println("remendTime : " + remendTime + "\ncurrentTimeMillis : " + (System.currentTimeMillis()/1000));
                        String timeTxt = "";

                        if(remendTime > 0 ){    // 시간 남아 있을때 (1초까지)
                            int day = (int) (remendTime/60/60/24);
                            int hour = (int) (remendTime/60/60);
                            int min = (int) (remendTime/60)%60;
                            int sec = (int) (remendTime%60);

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
