package com.petbox.shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.petbox.shop.Adapter.List.CartListAdapter;
import com.petbox.shop.Item.BestGoodInfo;
import com.petbox.shop.Item.CartItemInfo;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_back, btn_buy, btn_regist ;    // 뒤로가기, 구매하기, 정기배송 신청
    CheckBox chk_all;   // 전체선택
    TextView tv_chk_count, tv_content1, tv_content2, tv_content3; // 선택된 체크 아이템 갯수 표시
    ImageButton ibtn_delete;    // 선택된 항목 삭제
    ListView listView;
    CartListAdapter listAdapter;

    ArrayList<CartItemInfo> mItemList;  // 장바구니 리스트 정보들

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_cart);

        btn_back = (Button) findViewById(R.id.btn_cart_back);
        btn_back.setOnClickListener(this);

        btn_buy = (Button) findViewById(R.id.btn_cart_buy);
        btn_buy.setOnClickListener(this);

        btn_regist = (Button) findViewById(R.id.btn_cart_regist);
        btn_regist.setOnClickListener(this);

        chk_all = (CheckBox) findViewById(R.id.chk_cart_all);
        chk_all.setOnClickListener(this);

        tv_chk_count = (TextView) findViewById(R.id.tv_cart_chk_count);
        tv_content1 = (TextView) findViewById(R.id.tv_cart_content1);
        tv_content2 = (TextView) findViewById(R.id.tv_cart_content2);
        tv_content3 = (TextView) findViewById(R.id.tv_cart_content3);

        ibtn_delete = (ImageButton) findViewById(R.id.ibtn_cart_delete);
        ibtn_delete.setOnClickListener(this);

        mItemList = new ArrayList<CartItemInfo>();

        CartItemInfo info[] = new CartItemInfo[3];

        for(int i=0; i<3; i++){
            info[i] = new CartItemInfo();

            info[i].name = "상품명"+i;
            info[i].detail_name = "상품명"+i +" 11kg";
            info[i].num = 1;
            info[i].max = (i+1)*4;
            info[i].price = (i+1)*1000;

            mItemList.add(info[i]);
        }
        listView = (ListView) findViewById(R.id.list_cart);
        listAdapter = new CartListAdapter(getApplicationContext(), mItemList);
        listView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
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

    }
}
