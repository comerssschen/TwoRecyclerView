package com.weipan.kotilin.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weipan.kotilin.R;
import com.weipan.kotilin.bean.TwoBean;

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
        addItemType(TwoBean.EMPTY, R.layout.item_two);
    }

    @Override
    protected void convert(BaseViewHolder helper, TwoBean item) {
        switch (helper.getItemViewType()) {
            case TwoBean.TITLE:
                helper.setText(R.id.tv, item.getName());
                break;
            case TwoBean.CONTENT:
                helper.setText(R.id.tv, item.getName());
                helper.setText(R.id.tv_money, item.getPrice());
                helper.setBackgroundRes(R.id.ll_bg, item.getBgImg());
                helper.addOnClickListener(R.id.iv_add);

                break;
            case TwoBean.EMPTY:
                helper.setVisible(R.id.ll_bg, false);
                break;
        }
    }
}
