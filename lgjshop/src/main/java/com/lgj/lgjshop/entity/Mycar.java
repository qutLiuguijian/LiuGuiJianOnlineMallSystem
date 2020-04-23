package com.lgj.lgjshop.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("mycar")
@KeySequence(value = "mycar_id")
public class Mycar implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.INPUT)
    private Integer id;

    private String uname;

    private Integer gId;

    private Integer count;

    private transient String gName;

    private transient Integer gShopId;

    private transient Double gPrice;

    private transient Double gDiscountPrice;

    private transient String gDesc;

    private transient String gClassify;

    private transient String gFirstClassify;

    private transient String gSecondClassify;

    private transient String gThirdClassify;

    private transient String gImage;

    private transient String sname;

}
