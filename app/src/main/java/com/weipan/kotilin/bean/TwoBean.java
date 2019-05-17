package com.weipan.kotilin.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 作者：create by comersss on 2019/5/15 10:37
 * 邮箱：904359289@qq.com
 */
public class TwoBean implements MultiItemEntity {

    public TwoBean(int id, int goodsId, String name, int bgImg, String price, boolean isTitle, int itemType) {
        this.id = id;
        this.name = name;
        this.isTitle = isTitle;
        this.itemType = itemType;
        this.bgImg = bgImg;
        this.price = price;
        this.goodsId = goodsId;

    }

    public static final int TITLE = 1;
    public static final int CONTENT = 2;
    public static final int EMPTY = 3;
    private int itemType;
    private int id;
    private int goodsId;
    private String name;
    private boolean isTitle;
    private int bgImg;
    private String price;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getBgImg() {
        return bgImg;
    }

    public void setBgImg(int bgImg) {
        this.bgImg = bgImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
