package com.weipan.kotilin;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 作者：create by comersss on 2019/5/15 10:39
 * 邮箱：904359289@qq.com
 */
public class OneAdapter extends BaseQuickAdapter<OneBean, BaseViewHolder> {
    public int index = -1;

    public OneAdapter(int layoutResId, @Nullable List<OneBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OneBean item) {
        helper.setText(R.id.tv, item.getName());

        if (index == item.getId()) {
            helper.setBackgroundColor(R.id.rl, mContext.getResources().getColor(R.color.gray));
        } else {
            helper.setBackgroundColor(R.id.rl, mContext.getResources().getColor(R.color.white));
        }
    }
}
