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

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.weipan.kotilin.MainActivity;
import com.weipan.kotilin.R;
import com.weipan.kotilin.SoundPlayUtils;


/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 */
public class CloseConfirmDialog extends Dialog {

    private final Activity context;

    private OnCloseOrderLitener onCloseOrderLitener;
    public CountDownConfrimHelper helper;
    private TextView tvClose;

    public interface OnCloseOrderLitener {
        void close();
    }

    public void setOnCloseOrderLitener(OnCloseOrderLitener litener) {
        this.onCloseOrderLitener = litener;
    }

    public CloseConfirmDialog(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    public void show() {
        super.show();
        if (!ObjectUtils.isEmpty(helper)) {
            helper.stop();
            helper = null;
        }
        helper = new CountDownConfrimHelper(tvClose, 10, 1);
        helper.setOnFinishListener(new CountDownConfrimHelper.OnFinishListener() {
            @Override
            public void fin() {
                if (!ObjectUtils.isEmpty(onCloseOrderLitener)) {
                    if (!ObjectUtils.isEmpty(helper)) {
                        helper.stop();
                    }
                    helper = null;
                    if (!(ActivityUtils.getTopActivity() instanceof MainActivity)) {
                        return;
                    }
                    dismiss();
                    onCloseOrderLitener.close();
                }

            }
        });
        helper.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.close_confirm_dialog);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        Display display = context.getWindowManager().getDefaultDisplay();
        lp.width = (int) (display.getWidth() * 0.9); // 宽度
        dialogWindow.setAttributes(lp);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
        tvClose = findViewById(R.id.tv_close);
        TextView tvCotinue = findViewById(R.id.tv_cotinue);
        tvClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SoundPlayUtils.play(1);
                if (!ObjectUtils.isEmpty(onCloseOrderLitener)) {
                    if (!ObjectUtils.isEmpty(helper)) {
                        helper.stop();
                    }
                    helper = null;
                    dismiss();
                    onCloseOrderLitener.close();
                }

            }
        });
        tvCotinue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SoundPlayUtils.play(1);
                if (!ObjectUtils.isEmpty(helper)) {
                    helper.stop();
                }
                helper = null;
                dismiss();
            }
        });

    }

}
