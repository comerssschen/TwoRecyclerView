package com.weipan.kotilin.bean;

import java.io.Serializable;

/**
 * 作者：create by comersss on 2019/5/16 14:08
 * 邮箱：904359289@qq.com
 */
public class CarBean implements Serializable {

    private String name;
    private int bgImg;
    private int goodsId;
    private String price;
    private int conut;
    private String totalPrice;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBgImg() {
        return bgImg;
    }

    public void setBgImg(int bgImg) {
        this.bgImg = bgImg;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getConut() {
        return conut;
    }

    public void setConut(int conut) {
        this.conut = conut;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
