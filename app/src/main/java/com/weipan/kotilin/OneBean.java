package com.weipan.kotilin;

/**
 * 作者：create by comersss on 2019/5/15 10:36
 * 邮箱：904359289@qq.com
 */
public class OneBean {
    public OneBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;
    private String name;

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
}
