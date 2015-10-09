package com.petbox.shop.CustomView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.petbox.shop.R;

/**
 * Created by petbox on 2015-10-09.
 */
public class RegistFailedDialog extends Dialog implements View.OnClickListener{

    String title,content;
    TextView tv_title;
    TextView tv_content;

    Context mContext;
    Button btn_ok;

    public RegistFailedDialog(Context context) {
        super(context);
        mContext = context;
    }

    public RegistFailedDialog(Context context, String title, String content){
        super(context);
        mContext = context;
        this.title = title;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_regist_failed);

        tv_title = (TextView)findViewById(R.id.tv_dialog_regist_title);
        tv_title.setText(title);

        tv_content = (TextView)findViewById(R.id.tv_dialog_regist_content);
        tv_content.setText(content);

        btn_ok = (Button)findViewById(R.id.btn_dialog_regist_ok);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}
