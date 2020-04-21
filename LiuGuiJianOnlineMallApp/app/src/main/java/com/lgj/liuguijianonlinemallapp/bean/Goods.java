package com.lgj.liuguijianonlinemallapp.bean;

import java.io.Serializable;
import java.util.List;

public class Goods implements Serializable {

    /**
     * id : 1
     * sname : 一号店
     * saddress : 山东省济宁市泗水县土洞村
     * imgurl : ["https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1058509656,3544942575&fm=26&gp=0.jpg","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1058509656,3544942575&fm=26&gp=0.jpg"]
     * gfirstClassify : 家用电器
     * gthirdClassify : 数码电器
     * gdiscountPrice : 56.35
     * gsecondClassify : 数码电器
     * gdesc : 三合一数据线一拖三充电线
     * gshopId : 1
     * gprice : 45.6
     * gimage : https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1058509656,3544942575&fm=26&gp=0.jpg
     * gclassify : 手机数码
     * gname : 三合一数据线
     */

    private int id;
    private String sname;
    private String saddress;
    private String gfirstClassify;
    private String gthirdClassify;
    private double gdiscountPrice;
    private String gsecondClassify;
    private String gdesc;
    private int gshopId;
    private int gid;
    private double gprice;
    private String gimage;
    private String gclassify;
    private String gname;
    private List<String> imgurl;
    private String uname;
    private int count;
    private   boolean isChecked=false;
    private  Integer state;
    private  Integer edtime;
    private  String address;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSaddress() {
        return saddress;
    }

    public void setSaddress(String saddress) {
        this.saddress = saddress;
    }

    public String getGfirstClassify() {
        return gfirstClassify;
    }

    public void setGfirstClassify(String gfirstClassify) {
        this.gfirstClassify = gfirstClassify;
    }

    public String getGthirdClassify() {
        return gthirdClassify;
    }

    public void setGthirdClassify(String gthirdClassify) {
        this.gthirdClassify = gthirdClassify;
    }

    public double getGdiscountPrice() {
        return gdiscountPrice;
    }

    public void setGdiscountPrice(double gdiscountPrice) {
        this.gdiscountPrice = gdiscountPrice;
    }

    public String getGsecondClassify() {
        return gsecondClassify;
    }

    public void setGsecondClassify(String gsecondClassify) {
        this.gsecondClassify = gsecondClassify;
    }

    public String getGdesc() {
        return gdesc;
    }

    public void setGdesc(String gdesc) {
        this.gdesc = gdesc;
    }

    public int getGshopId() {
        return gshopId;
    }

    public void setGshopId(int gshopId) {
        this.gshopId = gshopId;
    }

    public double getGprice() {
        return gprice;
    }

    public void setGprice(double gprice) {
        this.gprice = gprice;
    }

    public String getGimage() {
        return gimage;
    }

    public void setGimage(String gimage) {
        this.gimage = gimage;
    }

    public String getGclassify() {
        return gclassify;
    }

    public void setGclassify(String gclassify) {
        this.gclassify = gclassify;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public List<String> getImgurl() {
        return imgurl;
    }

    public void setImgurl(List<String> imgurl) {
        this.imgurl = imgurl;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getEdtime() {
        return edtime;
    }

    public void setEdtime(Integer edtime) {
        this.edtime = edtime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
}
