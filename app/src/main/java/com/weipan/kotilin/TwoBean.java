package com.weipan.kotilin;

/**
 * 作者：create by comersss on 2019/5/15 10:37
 * 邮箱：904359289@qq.com
 */
public class TwoBean {

    public TwoBean(int id, String name, boolean isTitle) {
        this.id = id;
        this.name = name;
        this.isTitle = isTitle;
    }

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
}
