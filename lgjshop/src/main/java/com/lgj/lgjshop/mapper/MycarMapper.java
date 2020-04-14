package com.lgj.lgjshop.mapper;

import com.lgj.lgjshop.entity.Goods;
import com.lgj.lgjshop.entity.Mycar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-04-01
 */
@Mapper
@Repository
public interface MycarMapper extends BaseMapper<Mycar> {
    List<Goods>getCarGoods(String uname);
}
