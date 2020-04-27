package com.lgj.liuguijianonlinemallapp.bean;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2020-04-26
 */

public class Gassess implements Serializable {

    /**
     * id : 1
     * uname : liuguijian
     * assess : 相当不错
     * gid : 1
     */

    private int id;
    private String uname;
    private String assess;
    private int gid;

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

    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
}
