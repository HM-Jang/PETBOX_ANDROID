package com.petbox.shop;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.petbox.shop.Adapter.Pager.CategoryPagerAdapter;
import com.petbox.shop.Adapter.Pager.HomePagerAdapter;
import com.petbox.shop.Adapter.Pager.MyPagePagerAdapter;
import com.petbox.shop.Adapter.Pager.SearchPagerAdapter;
import com.petbox.shop.Fragment.Category.CategoryFragment;
import com.petbox.shop.Fragment.Home.BestGoodFragment;
import com.petbox.shop.Fragment.Home.ChanceDealFragment;
import com.petbox.shop.Fragment.Home.EventFragment;
import com.petbox.shop.Fragment.Home.PlanningFragment;
import com.petbox.shop.Fragment.Home.PrimiumFragment;
import com.petbox.shop.Fragment.Home.RegularShippingFragment;
import com.petbox.shop.Fragment.MyPage.MyPageFragment;
import com.petbox.shop.Fragment.Search.PopularSearchFragment;
import com.petbox.shop.Fragment.Search.RecentSearchFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, BestGoodFragment.OnFragmentInteractionListener, ChanceDealFragment.OnFragmentInteractionListener, EventFragment.OnFragmentInteractionListener,
PlanningFragment.OnFragmentInteractionListener, PrimiumFragment.OnFragmentInteractionListener, RegularShippingFragment.OnFragmentInteractionListener, CategoryFragment.OnFragmentInteractionListener, MyPageFragment.OnFragmentInteractionListener
, PopularSearchFragment.OnFragmentInteractionListener, RecentSearchFragment.OnFragmentInteractionListener{

    private static final int REQ_SPLASH = 1;
    private static final int RES_SPLASH_CANCEL = 0;
    public static final String TAG = "MainAct";

    private TabLayout tabLayout;
    private ViewPager mViewPager;

    private HomePagerAdapter homePagerAdapter;
    private CategoryPagerAdapter categoryPagerAdapter;
    private SearchPagerAdapter searchPagerAdapter;
    private MyPagePagerAdapter myPagePagerAdapter;

    private FragmentManager fragmentManager;

    int menu_selected = 0;  // 0: 펫박스홈, 1: 카테고리, 2: 검색, 3: 마이페이지

    //SlidingTabLayout mSlidingTabLayout;

    private Toolbar toolbar;
    ImageButton ibtn_home, ibtn_category, ibtn_search, ibtn_login, ibtn_mypage;
    ImageButton ibtn_menu_category, ibtn_menu_cart;

    EditText edit_search;
    ImageView iv_logo, iv_search;

    int mainColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = new Intent(this, SplashActivity.class);
        startActivityForResult(i, REQ_SPLASH);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainColor = getResources().getColor(R.color.colorPrimary);

        //toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        //setSupportActionBar(toolbar);

        edit_search = (EditText)findViewById(R.id.edit_search);
        edit_search.setFocusable(false);
        edit_search.setClickable(true);

        edit_search.setOnClickListener(this);

        iv_search = (ImageView)findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);

        iv_logo = (ImageView)findViewById(R.id.iv_logo);

        ibtn_menu_category = (ImageButton)findViewById(R.id.ibtn_menu_category);
        ibtn_menu_category.setOnClickListener(this);

        ibtn_menu_cart = (ImageButton)findViewById(R.id.ibtn_menu_cart);
        ibtn_menu_cart.setOnClickListener(this);

        ibtn_home = (ImageButton)findViewById(R.id.ibtn_home);
        ibtn_home.setOnClickListener(this);

        ibtn_category = (ImageButton)findViewById(R.id.ibtn_category);
        ibtn_category.setOnClickListener(this);

        ibtn_search = (ImageButton)findViewById(R.id.ibtn_search);
        ibtn_search.setOnClickListener(this);

        ibtn_login = (ImageButton)findViewById(R.id.ibtn_login);
        ibtn_login.setOnClickListener(this);

        ibtn_mypage = (ImageButton)findViewById(R.id.ibtn_mypage);
        ibtn_mypage.setOnClickListener(this);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        fragmentManager = getSupportFragmentManager();

        homePagerAdapter = new HomePagerAdapter(fragmentManager);
        categoryPagerAdapter = new CategoryPagerAdapter(fragmentManager);
        searchPagerAdapter = new SearchPagerAdapter(fragmentManager);
        myPagePagerAdapter = new MyPagePagerAdapter(fragmentManager);

        mViewPager.setAdapter(homePagerAdapter);
        tabLayout = (TabLayout)findViewById(R.id.slide_tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        tabLayout.setSelectedTabIndicatorColor(mainColor);
        tabLayout.setTabTextColors(0xff4e91ff, 0xff000000);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void setHomePagerAdapter(){
        //clearBackStack();
        //Log.i("TAG", "MNGR[COUNT] : "+fragmentManager.getFragments().size());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(homePagerAdapter);
        mViewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void setCategoryPagerAdapter(){
        //clearBackStack();
        //Log.i("TAG", "MNGR[COUNT] : "+fragmentManager.getFragments().size());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(categoryPagerAdapter);
        mViewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void setSearchPagerAdapter(){
        //clearBackStack();
        //Log.i("TAG", "MNGR[COUNT] : "+fragmentManager.getFragments().size());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(searchPagerAdapter);
        mViewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void setMyPagePagerAdapter(){
        //clearBackStack();
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(myPagePagerAdapter);
        mViewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void clearBackStack() {
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for(int i=0; i< fragmentManager.getFragments().size(); i++){
            Log.i(TAG, i+"번째 Fragment REMOVE");
            fragmentTransaction.remove(fragmentManager.getFragments().get(i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.edit_search:
                iv_logo.setVisibility(View.GONE);
                edit_search.setFocusable(true);
                edit_search.setClickable(true);
                break;

            case R.id.iv_search:
                iv_logo.setVisibility(View.VISIBLE);
                edit_search.setFocusable(false);
                edit_search.setClickable(true);
                break;

            case R.id.ibtn_menu_category:
                setCategoryPagerAdapter();
                //Toast.makeText(getApplicationContext(), "menu_category", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ibtn_menu_cart:
                Intent cart_intnet = new Intent(MainActivity.this, CartActivity.class);
                startActivity(cart_intnet);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                //Toast.makeText(getApplicationContext(), "menu_cart", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ibtn_home:
                //Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
                menu_selected = 0;

                if(menu_selected == 0 ){    // 홈
                    setHomePagerAdapter();

                    ibtn_home.setImageResource(R.drawable.btn_bottom_home_on);
                    ibtn_category.setImageResource(R.drawable.btn_bottom_category_off);
                    ibtn_search.setImageResource(R.drawable.btn_bottom_search_off);

                    if(ibtn_mypage.getVisibility() == View.VISIBLE)
                        ibtn_mypage.setImageResource(R.drawable.btn_bottom_mypage_off);
                }


                break;

            case R.id.ibtn_category:
                //Toast.makeText(getApplicationContext(), "category", Toast.LENGTH_SHORT).show();
                menu_selected = 1;

                if(menu_selected == 1 ) {    // 카테고리
                    setCategoryPagerAdapter();

                    ibtn_home.setImageResource(R.drawable.btn_bottom_home_off);
                    ibtn_category.setImageResource(R.drawable.btn_bottom_category_on);
                    ibtn_search.setImageResource(R.drawable.btn_bottom_search_off);

                    if (ibtn_mypage.getVisibility() == View.VISIBLE)
                        ibtn_mypage.setImageResource(R.drawable.btn_bottom_mypage_off);
                }

                break;

            case R.id.ibtn_search:
                //Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();
                menu_selected = 2;

                if(menu_selected == 2 ) {    // 검색
                    setSearchPagerAdapter();

                    ibtn_home.setImageResource(R.drawable.btn_bottom_home_off);
                    ibtn_category.setImageResource(R.drawable.btn_bottom_category_off);
                    ibtn_search.setImageResource(R.drawable.btn_bottom_search_on);

                    if (ibtn_mypage.getVisibility() == View.VISIBLE)
                        ibtn_mypage.setImageResource(R.drawable.btn_bottom_mypage_off);
                }

                break;

            case R.id.ibtn_login:
                Intent login_intnet = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login_intnet);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                //Toast.makeText(getApplicationContext(), "login", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ibtn_mypage:
                menu_selected = 3;

                if(menu_selected == 3) {    // 홈

                    Toast.makeText(getApplicationContext(), "mypage", Toast.LENGTH_SHORT).show();
                    setMyPagePagerAdapter();

                    ibtn_home.setImageResource(R.drawable.btn_bottom_home_off);
                    ibtn_category.setImageResource(R.drawable.btn_bottom_category_off);
                    ibtn_search.setImageResource(R.drawable.btn_bottom_search_off);

                    if (ibtn_mypage.getVisibility() == View.VISIBLE)
                        ibtn_mypage.setImageResource(R.drawable.btn_bottom_mypage_on);

                }

                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQ_SPLASH:

                if(resultCode == RES_SPLASH_CANCEL){
                    finish();
                }
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
