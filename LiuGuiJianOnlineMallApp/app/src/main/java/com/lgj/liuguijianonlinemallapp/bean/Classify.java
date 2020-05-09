package com.lgj.liuguijianonlinemallapp.bean;


import java.io.Serializable;
import java.util.List;


public class Classify implements Serializable {
    private Integer id;
    private String name;
    public List<Classify> childName;
    public boolean isSelected=false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Classify> getChildName() {
        return childName;
    }

    public void setChildName(List<Classify> childName) {
        this.childName = childName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
