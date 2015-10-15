package com.petbox.shop.Delegate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by petbox on 2015-10-13.
 */
public interface NumberPickerDelegate {
    public void clickIncrease(HashMap<String, Integer> params);
    public void clickDecrease(HashMap<String, Integer> params);
    public void deleteItem(int price);
    public void setNum(HashMap<String, Integer> params);
}
