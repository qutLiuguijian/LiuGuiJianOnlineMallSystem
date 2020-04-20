package com.lgj.lgjshop.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("goods")
@KeySequence(value = "goods_id")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
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

    private transient String sname;

    private transient String saddress;

    private transient List<String> imgurl;

    private transient String uname;

    private transient Integer count;

    private transient Integer state;

    private transient Integer edtime;

    private transient String address;

}
