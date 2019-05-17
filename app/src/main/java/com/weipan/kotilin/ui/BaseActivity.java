package com.weipan.kotilin.ui;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.Utils;


public class BaseActivity extends AppCompatActivity {
    public Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        ringtone = RingtoneManager.getRingtone(Utils.getApp(), notification);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BarUtils.setNavBarVisibility(this,false);
    }

}
