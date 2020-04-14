package com.lgj.lgjshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.lgj.lgjshop.entity.Goods;
import com.lgj.lgjshop.mapper.GoodsMapper;
import com.lgj.lgjshop.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-03-30
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    GoodsMapper mapper;

    @Override
    public Goods getGoodsDetail(Integer id) {
        return mapper.getGoodsDetail(id);
    }

    @Override
    public List<Map> getGoodsDetailImg(Integer id) {
        return mapper.getGoodsDetailImg(id);
    }
}
