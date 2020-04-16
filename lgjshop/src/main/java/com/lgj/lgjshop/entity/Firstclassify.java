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
 * @since 2020-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Firstclassify implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String firstClassify;


}
