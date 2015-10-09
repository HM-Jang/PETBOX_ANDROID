package com.petbox.shop.Utility;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by petbox on 2015-10-05.
 */
public class Utility {

    //펫박스홈에 쓰임. ListView item항목 수에 따라 길이 조정
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    //Email 형식검사
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w]+@[\\w]+(.[\\w]+)+$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    //Password 형식검사
    public static boolean isPasswordValid(String pw) {
        boolean isValid = false;

        String expression = "^[\\w\\W]*[a-zA-Z]+[\\w\\W]*$";
        CharSequence inputStr = pw;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    //Name 형식검사
    public static boolean isNameValid(String pw) {
        boolean isValid = false;

        String expression_korean = "^[가-힣]{2}[가-힣]*$";
        String expression_english = "^[a-zA-Z]{4}([\\s]?[a-zA-z])*$";
        CharSequence inputStr = pw;

        Pattern pattern = Pattern.compile(expression_korean, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }

        pattern = Pattern.compile(expression_english, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(inputStr);
        if(matcher.matches()){
            isValid = true;
        }

        return isValid;
    }

    //핸드폰번호 형식검사
    public static boolean isPhoneValid(String phone) {
        boolean isValid = false;

        String expression = "^01(0|1|[6-9])-\\d{3,4}-\\d{4}$";
        CharSequence inputStr = phone;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


}
