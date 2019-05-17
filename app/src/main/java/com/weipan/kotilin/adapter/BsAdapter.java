package com.weipan.kotilin.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weipan.kotilin.CustomCarGoodsCounterView;
import com.weipan.kotilin.R;
import com.weipan.kotilin.bean.CarBean;

import java.util.List;

/**
 * 作者：create by comersss on 2019/5/16 16:17
 * 邮箱：904359289@qq.com
 */
public class BsAdapter extends BaseQuickAdapter<CarBean, BaseViewHolder> {
    private mListener mListener;

    public interface mListener {
        void updateGoodsNumber(int number, CarBean item, int position);
    }

    public void setmListener(mListener listener) {
        mListener = listener;
    }

    public BsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CarBean item) {
        helper.setImageResource(R.id.iv_image, item.getBgImg());
        helper.setText(R.id.tv_title, item.getName());
        helper.setText(R.id.tv_description, item.getName());
        helper.setText(R.id.tv_money, item.getPrice());
        CustomCarGoodsCounterView customCarGoodsCounterView = helper.getView(R.id.good_countview);
        customCarGoodsCounterView.setedit(false);
        customCarGoodsCounterView.setGoodsNumber(item.getConut());
        customCarGoodsCounterView.setUpdateGoodsNumberListener(new CustomCarGoodsCounterView.UpdateGoodsNumberListener() {
            @Override
            public void updateGoodsNumber(int number) {
                if (mListener != null) {
                    mListener.updateGoodsNumber(number, item, helper.getPosition());
                }
            }
        });
    }
}
