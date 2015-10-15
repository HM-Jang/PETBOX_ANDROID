package com.petbox.shop.Item;

/**
 * Created by petbox on 2015-10-13.
 */
public class GoodOptionInfo {
    public String name = "";    //상품명
    public int count = 0;  // 잔여량
    public int price = 0; // 가격
    public int dc_price = 0;    // 할인 가격
    public int order_count = 0; //주문 개수

    public int sno = 0; // 구매옵션 번호
    public boolean isAddOption = false;    // false : 구매옵션, true : 추가옵션
    public String optionName = "";
}
