package com.weipan.kotilin;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 作者：create by comersss on 2019/5/15 10:37
 * 邮箱：904359289@qq.com
 */
public class TwoBean implements MultiItemEntity {

    public TwoBean(int id, String name, boolean isTitle, int itemType) {
        this.id = id;
        this.name = name;
        this.isTitle = isTitle;
        this.itemType = itemType;

    }

    public static final int TITLE = 1;
    public static final int CONTENT = 2;
    private int itemType;
    private int id;
    private String name;
    private boolean isTitle;

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
