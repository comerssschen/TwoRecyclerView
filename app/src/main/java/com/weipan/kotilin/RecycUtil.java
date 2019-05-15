package com.weipan.kotilin;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * 作者：create by comersss on 2019/5/15 11:07
 * 邮箱：904359289@qq.com
 */
public class RecycUtil {
    private static int first = 0;
    private static int last = 0;

    public static void moveToPositAndTop(final int n, final LinearLayoutManager layoutM, final RecyclerView recyc, Handler handler) {
        first = layoutM.findFirstVisibleItemPosition();
        last = layoutM.findLastVisibleItemPosition();
        if (n <= first) {
            recyc.scrollToPosition(n);
        } else if (n <= last) {
            int offSet = recyc.getChildAt(n - first).getTop();
            recyc.smoothScrollBy(0, offSet);
        } else {
            recyc.scrollToPosition(n);
            //这里要延迟一下，因为之前的scrollToPosition的实际功能是开子线程完成的，如果直接调findFirstVisibleItemPosition，获取的数据还是之前的
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    first = layoutM.findFirstVisibleItemPosition();
                    last = layoutM.findLastVisibleItemPosition();
                    Log.v("zzw", " rightclick:" + n + " first:" + first + " last:" + last);
                    if (recyc.getChildAt(last - first) == null) {

                    } else {
                        int offSet = recyc.getChildAt(last - first).getTop();
                        recyc.smoothScrollBy(0, offSet);
                    }
                }
            }, 50);
        }

    }

    public static void moveToPositAndCenter(final int n, final LinearLayoutManager layoutM, final RecyclerView recyc, Handler handler) {
        first = layoutM.findFirstVisibleItemPosition();
        last = layoutM.findLastVisibleItemPosition();
        if (n <= first) {
            recyc.scrollToPosition(n);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    first = layoutM.findFirstVisibleItemPosition();
                    last = layoutM.findLastVisibleItemPosition();
                    if (recyc.getChildAt(last - first) == null) {

                    } else {
                        int offSet = recyc.getChildAt(last - first).getTop();
                        recyc.smoothScrollBy(0, -offSet / 2);
                    }

                }
            }, 10);
        } else if (n <= last) {
            int offSet = recyc.getChildAt(n - first).getTop();
            recyc.smoothScrollBy(0, offSet / 2);
        } else {
            recyc.scrollToPosition(n);
            //这里要延迟一下，因为之前的scrollToPosition的实际功能是开子线程完成的，如果直接调findFirstVisibleItemPosition，获取的数据还是之前的
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    first = layoutM.findFirstVisibleItemPosition();
                    last = layoutM.findLastVisibleItemPosition();

                    if (recyc.getChildAt(last - first) == null) {

                    } else {
                        int offSet = recyc.getChildAt(last - first).getTop();
                        recyc.smoothScrollBy(0, offSet / 2);
                    }
                }
            }, 10);
        }
    }

}
