package com.lgj.lgjshop.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.lgj.lgjshop.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-03-30
 */
@Mapper
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {
    Goods getGoodsDetail(Integer id);
    List<Map> getGoodsDetailImg(Integer id);
}
