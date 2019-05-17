package com.weipan.kotilin.bean;

import java.util.List;

/**
 * 作者：create by comersss on 2019/5/15 15:44
 * 邮箱：904359289@qq.com
 */
public class DataBean {


    private List<CategoryOneArrayBean> categoryOneArray;

    public List<CategoryOneArrayBean> getCategoryOneArray() {
        return categoryOneArray;
    }

    public void setCategoryOneArray(List<CategoryOneArrayBean> categoryOneArray) {
        this.categoryOneArray = categoryOneArray;
    }

    public static class CategoryOneArrayBean {
        /**
         * categoryTwoArray : [{"goodsId":10000010,"name":"香辣鸡腿堡套餐","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"19.00","cacode":"0"},{"goodsId":10000011,"name":"薯条套餐","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"25.00","cacode":"0"},{"goodsId":10000012,"name":"双人套餐A","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"59.00","cacode":"0"},{"goodsId":10000013,"name":"双人套餐套餐B","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"49.00","cacode":"0"},{"goodsId":10000014,"name":"奥尔良鸡腿堡小食套餐","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"29.00","cacode":"0"},{"goodsId":10000015,"name":"鸡腿堡套餐B","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"39.00","cacode":"0"},{"goodsId":10000016,"name":"单人热辣套餐A","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"39.00","cacode":"0"},{"goodsId":10000017,"name":"单人热辣套餐B","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"49.00","cacode":"0"},{"goodsId":10000018,"name":"老北京鸡肉卷套餐","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"29.00","cacode":"0"},{"goodsId":10000019,"name":"田园堡单人套餐","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"39.00","cacode":"0"},{"goodsId":100000110,"name":"劲脆鲜虾堡套餐A","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"63.00","cacode":"0"},{"goodsId":100000111,"name":"劲脆鲜虾堡套餐B","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"69.00","cacode":"0"},{"goodsId":100000112,"name":"牛肉堡单人套餐","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"42.00","cacode":"0"},{"goodsId":100000113,"name":"牛肉堡双人套餐","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"63.00","cacode":"0"},{"goodsId":100000114,"name":"蜜汁鸡腿套餐","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"29.00","cacode":"0"},{"goodsId":100000115,"name":"h合家欢套餐","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"39.00","cacode":"0"},{"goodsId":100000116,"name":"辣翅套餐","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"32.00","cacode":"0"},{"goodsId":100000117,"name":"双享套餐","imgsrc":"https://comersss.gitee.io/apk/image/group1_item1.png","price":"48.00","cacode":"0"}]
         * goodsId : 100000118
         * name : 热卖套餐
         * imgsrc : https://comersss.gitee.io/apk/image/group1_item1.png
         * cacode : 0
         * price : 0000
         */

        private int goodsId;
        private String name;
        private String imgsrc;
        private String cacode;
        private String price;
        private List<CategoryTwoArrayBean> categoryTwoArray;

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

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getCacode() {
            return cacode;
        }

        public void setCacode(String cacode) {
            this.cacode = cacode;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public List<CategoryTwoArrayBean> getCategoryTwoArray() {
            return categoryTwoArray;
        }

        public void setCategoryTwoArray(List<CategoryTwoArrayBean> categoryTwoArray) {
            this.categoryTwoArray = categoryTwoArray;
        }

        public static class CategoryTwoArrayBean {
            /**
             * goodsId : 10000010
             * name : 香辣鸡腿堡套餐
             * imgsrc : https://comersss.gitee.io/apk/image/group1_item1.png
             * price : 19.00
             * cacode : 0
             */

            private int goodsId;
            private String name;
            private String imgsrc;
            private String price;
            private String cacode;

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

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getCacode() {
                return cacode;
            }

            public void setCacode(String cacode) {
                this.cacode = cacode;
            }
        }
    }
}
