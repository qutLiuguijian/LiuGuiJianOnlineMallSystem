package com.lgj.lgjshop.mapper;

import com.lgj.lgjshop.entity.Goods;
import com.lgj.lgjshop.entity.Goodsorder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-04-15
 */
@Mapper
@Repository
public interface GoodsorderMapper extends BaseMapper<Goodsorder> {
    List<Goodsorder> getOrderByState(@Param("uname") String uname, @Param("state")int state);
    List<Goodsorder> getOrderAll(String uname);
    List<Goodsorder> getSendOrder();
}
