package com.weipan.kotilin;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * 作者：create by comersss on 2019/5/21 18:25
 * 邮箱：904359289@qq.com
 */
public class SoundPlayUtils {
    // SoundPool对象
    public static SoundPool mSoundPlayer = new SoundPool(10,
            AudioManager.STREAM_SYSTEM, 5);
    public static SoundPlayUtils soundPlayUtils;
    // 上下文
    static Context mContext;

    /**
     * 初始化
     * 初始化音乐池  播发喇叭的   BaseApplication初始化 方法里面用
     *
     * @param context
     */
    public static SoundPlayUtils init(Context context) {
        if (soundPlayUtils == null) {
            soundPlayUtils = new SoundPlayUtils();
        }

        // 初始化声音
        mContext = context;
        /**
         * 参数1:加载音乐流第多少个 (只用了俩个音乐) 2:设置音乐的质量 音乐流 3:资源的质量  0
         */
        mSoundPlayer.load(mContext, R.raw.click4, 1);// 1
        mSoundPlayer.load(mContext, R.raw.alet, 1);// 2
        mSoundPlayer.load(mContext, R.raw.start_order, 1);// 3
        mSoundPlayer.load(mContext, R.raw.pay_fail, 1);// 4
        mSoundPlayer.load(mContext, R.raw.pay_sucess, 1);// 5
        mSoundPlayer.load(mContext, R.raw.chose_goods, 1);// 6
        mSoundPlayer.load(mContext, R.raw.chose_pay_type, 1);//7
        mSoundPlayer.load(mContext, R.raw.cancle_pay, 1);//8
        return soundPlayUtils;
    }

    /**
     * 播放声音
     *
     * @param soundID 设置声音
     */
    public static void play(int soundID) {
        mSoundPlayer.play(soundID, 1, 1, 0, 0, 1);
    }

}
