package com.lgj.lgjshop.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2020-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Goodsorder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uname;

    private Integer gId;

    private Integer edtime;

    private String shopaddresss;

    private String useraddress;


}
