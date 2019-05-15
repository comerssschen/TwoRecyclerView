package com.weipan.kotilin;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 作者：create by comersss on 2019/5/15 11:03
 * 邮箱：904359289@qq.com
 */
public class TwoAdapter extends BaseMultiItemQuickAdapter<TwoBean, BaseViewHolder> {

    public TwoAdapter(@Nullable List<TwoBean> data) {
        super(data);
        addItemType(TwoBean.TITLE, R.layout.item_two_title);
        addItemType(TwoBean.CONTENT, R.layout.item_two);
    }

    @Override
    protected void convert(BaseViewHolder helper, TwoBean item) {
        switch (helper.getItemViewType()) {
            case TwoBean.TITLE:
                helper.setText(R.id.tv, item.getName());
                break;
            case TwoBean.CONTENT:
                helper.setText(R.id.tv, item.getName());
                break;
        }
    }
}
