package com.lgj.lgjshop.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String gName;

    private Integer gShopId;

    private Double gPrice;

    private Double gDiscountPrice;

    private String gDesc;

    private String gClassify;

    private String gFirstClassify;

    private String gSecondClassify;

    private String gThirdClassify;

    private String gImage;

    private transient List<String> imageList;
}
