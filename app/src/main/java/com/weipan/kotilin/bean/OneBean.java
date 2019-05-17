package com.weipan.kotilin.bean;

/**
 * 作者：create by comersss on 2019/5/15 10:36
 * 邮箱：904359289@qq.com
 */
public class OneBean {
    public OneBean(int id, String name, int bgImgCheck, int bgImgUnCheck) {
        this.id = id;
        this.name = name;
        this.bgImgCheck = bgImgCheck;
        this.bgImgUnCheck = bgImgUnCheck;
    }

    private int id;
    private String name;
    private int bgImgCheck;
    private int bgImgUnCheck;

    public int getBgImgCheck() {
        return bgImgCheck;
    }

    public void setBgImgCheck(int bgImgCheck) {
        this.bgImgCheck = bgImgCheck;
    }

    public int getBgImgUnCheck() {
        return bgImgUnCheck;
    }

    public void setBgImgUnCheck(int bgImgUnCheck) {
        this.bgImgUnCheck = bgImgUnCheck;
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
}
