package com.weipan.kotilin;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 作者：create by comersss on 2019/5/15 11:03
 * 邮箱：904359289@qq.com
 */
public class TwoAdapter extends BaseQuickAdapter<TwoBean, BaseViewHolder> {

    public TwoAdapter(int layoutResId, @Nullable List<TwoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TwoBean item) {
        helper.setText(R.id.tv, item.getName());
    }
}
