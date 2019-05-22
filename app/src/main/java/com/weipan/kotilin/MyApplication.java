package com.weipan.kotilin;

import android.app.Application;

/**
 * 作者：create by comersss on 2019/5/22 09:00
 * 邮箱：904359289@qq.com
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SoundPlayUtils.init(this);
    }
}
