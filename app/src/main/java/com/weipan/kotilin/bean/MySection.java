package com.weipan.kotilin.bean;


import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 作者：Created by cc on 2017/6/14 09:24.
 * 邮箱：904359289@QQ.com.
 * 类 ：
 */

public class MySection extends SectionEntity<TwoBean> {

    public MySection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MySection(TwoBean t) {
        super(t);
    }
}