package com.lgj.lgjshop.service.impl;

import com.lgj.lgjshop.entity.Goods;
import com.lgj.lgjshop.entity.Goodsorder;
import com.lgj.lgjshop.mapper.GoodsMapper;
import com.lgj.lgjshop.mapper.GoodsorderMapper;
import com.lgj.lgjshop.service.IGoodsorderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-04-15
 */
@Service
public class GoodsorderServiceImpl extends ServiceImpl<GoodsorderMapper, Goodsorder> implements IGoodsorderService {
    @Autowired
    GoodsorderMapper mapper;
    @Override
    public List<Goods> getOrderByState(String uname, int state) {
        return mapper.getOrderByState(uname,state);
    }

    @Override
    public List<Goods> getOrderAll(String uname) {
        return mapper.getOrderAll(uname);
    }
}
