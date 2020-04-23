package com.lgj.lgjshop.service.impl;

import com.lgj.lgjshop.entity.Goods;
import com.lgj.lgjshop.entity.Mycar;
import com.lgj.lgjshop.mapper.MycarMapper;
import com.lgj.lgjshop.service.IMycarService;
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
 * @since 2020-04-01
 */
@Service
public class MycarServiceImpl extends ServiceImpl<MycarMapper, Mycar> implements IMycarService {

    @Autowired
    MycarMapper mycarMapper;
    @Override
    public List<Mycar> getCarGoods(String uname) {
        return mycarMapper.getCarGoods(uname);
    }
}
