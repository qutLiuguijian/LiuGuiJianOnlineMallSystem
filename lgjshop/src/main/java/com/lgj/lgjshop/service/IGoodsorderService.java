package com.lgj.lgjshop.service;

import com.lgj.lgjshop.entity.Goods;
import com.lgj.lgjshop.entity.Goodsorder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-04-15
 */
public interface IGoodsorderService extends IService<Goodsorder> {
    List<Goodsorder> getOrderByState(String uname, int state);
    List<Goodsorder> getOrderAll(String uname);
    List<Goodsorder> getSendOrder();
}
