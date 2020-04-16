package com.lgj.lgjshop.entity;

import lombok.Data;

import java.util.List;

@Data
public class Classify {
    private Integer id;
    private String name;
    public List<Classify> childName;
}
