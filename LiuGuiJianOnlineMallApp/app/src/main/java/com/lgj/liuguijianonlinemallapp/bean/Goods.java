package com.lgj.liuguijianonlinemallapp.bean;

import java.io.Serializable;

public class Goods implements Serializable {

    /**
     * id : 1
     * gdesc : 三合一数据线一拖三充电线
     * gprice : 45.6
     * gname : 三合一数据线
     * gimage : https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1058509656,3544942575&fm=26&gp=0.jpg
     * gshopId : 1
     * gclassify : 手机数码
     * gdiscountPrice : 56.35
     * gfirstClassify : 家用电器
     * gsecondClassify : 数码电器
     * gthirdClassify : 数码电器
     */

    private Integer id;
    private String gdesc;
    private double gprice;
    private String gname;
    private String gimage;
    private Integer gshopId;
    private String gclassify;
    private double gdiscountPrice;
    private String gfirstClassify;
    private String gsecondClassify;
    private String gthirdClassify;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGdesc() {
        return gdesc;
    }

    public void setGdesc(String gdesc) {
        this.gdesc = gdesc;
    }

    public double getGprice() {
        return gprice;
    }

    public void setGprice(double gprice) {
        this.gprice = gprice;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGimage() {
        return gimage;
    }

    public void setGimage(String gimage) {
        this.gimage = gimage;
    }

    public Integer getGshopId() {
        return gshopId;
    }

    public void setGshopId(int gshopId) {
        this.gshopId = gshopId;
    }

    public String getGclassify() {
        return gclassify;
    }

    public void setGclassify(String gclassify) {
        this.gclassify = gclassify;
    }

    public double getGdiscountPrice() {
        return gdiscountPrice;
    }

    public void setGdiscountPrice(double gdiscountPrice) {
        this.gdiscountPrice = gdiscountPrice;
    }

    public String getGfirstClassify() {
        return gfirstClassify;
    }

    public void setGfirstClassify(String gfirstClassify) {
        this.gfirstClassify = gfirstClassify;
    }

    public String getGsecondClassify() {
        return gsecondClassify;
    }

    public void setGsecondClassify(String gsecondClassify) {
        this.gsecondClassify = gsecondClassify;
    }

    public String getGthirdClassify() {
        return gthirdClassify;
    }

    public void setGthirdClassify(String gthirdClassify) {
        this.gthirdClassify = gthirdClassify;
    }
}
