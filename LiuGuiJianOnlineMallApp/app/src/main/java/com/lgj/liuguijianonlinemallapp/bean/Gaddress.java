package com.lgj.liuguijianonlinemallapp.bean;

import java.io.Serializable;

public class Gaddress implements Serializable {

    /**
     * id : 1
     * uname : liuguijian
     * address : 山东省济宁市泗水县土洞村
     * receiver : 刘贵健
     * phone : 15726286351
     */

    private int id;
    private String uname;
    private String address;
    private String receiver;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
