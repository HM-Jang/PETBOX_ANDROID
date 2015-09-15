package com.petbox.shop;


import android.app.FragmentTransaction;
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

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    public static final String TAG = "MainAct";

    private TabLayout tabLayout;
    private ViewPager mViewPager;

    private HomePagerAdapter homePagerAdapter;
    private CategoryPagerAdapter categoryPagerAdapter;
    private SearchPagerAdapter searchPagerAdapter;
    private MyPagePagerAdapter myPagePagerAdapter;

    private FragmentManager fragmentManager;


    //SlidingTabLayout mSlidingTabLayout;

    private Toolbar toolbar;
    ImageButton ibtn_home, ibtn_category, ibtn_search, ibtn_login, ibtn_mypage;
    ImageButton ibtn_menu_category, ibtn_menu_cart;

    EditText edit_search;
    ImageView iv_logo, iv_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);

        fragmentManager = getSupportFragmentManager();

        homePagerAdapter = new HomePagerAdapter(fragmentManager);
        categoryPagerAdapter = new CategoryPagerAdapter(fragmentManager);
        searchPagerAdapter = new SearchPagerAdapter(fragmentManager);
        myPagePagerAdapter = new MyPagePagerAdapter(fragmentManager);

        mViewPager.setAdapter(homePagerAdapter);
        tabLayout = (TabLayout)findViewById(R.id.slide_tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        tabLayout.setSelectedTabIndicatorColor(0xff00aeff);
        tabLayout.setTabTextColors(0xffd5a781, 0xffffffff);
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
                Toast.makeText(getApplicationContext(), "menu_cart", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ibtn_home:
                //Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
                setHomePagerAdapter();
                break;

            case R.id.ibtn_category:
                //Toast.makeText(getApplicationContext(), "category", Toast.LENGTH_SHORT).show();
                setCategoryPagerAdapter();
                break;

            case R.id.ibtn_search:
                //Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();
                setSearchPagerAdapter();
                break;

            case R.id.ibtn_login:
                Toast.makeText(getApplicationContext(), "login", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ibtn_mypage:
                Toast.makeText(getApplicationContext(), "mypage", Toast.LENGTH_SHORT).show();
                setMyPagePagerAdapter();
                break;

        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
