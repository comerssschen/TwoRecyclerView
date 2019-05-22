package com.weipan.kotilin.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.weipan.kotilin.R;
import com.weipan.kotilin.SoundPlayUtils;


/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 */
public class TrueMoneyDialog extends Dialog {

    private final Activity context;

    public TrueMoneyDialog(Activity context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.true_money_dialog);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        Display display = context.getWindowManager().getDefaultDisplay();
        lp.width = (int) (display.getWidth() * 0.9); // 宽度
        dialogWindow.setAttributes(lp);

        TextView tv_close = findViewById(R.id.tv_close);
        TextView tv_open = findViewById(R.id.tv_open);

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPlayUtils.play(1);
                SPUtils.getInstance().put("realMoney", true);
                dismiss();
            }
        });
        tv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPlayUtils.play(1);
                SPUtils.getInstance().put("realMoney", false);
                dismiss();
            }
        });
        setCancelable(false);
        setCanceledOnTouchOutside(true);


    }


}
