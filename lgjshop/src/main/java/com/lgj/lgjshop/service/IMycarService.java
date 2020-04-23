package com.lgj.lgjshop.service;

import com.lgj.lgjshop.entity.Goods;
import com.lgj.lgjshop.entity.Mycar;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-04-01
 */
public interface IMycarService extends IService<Mycar> {
    List<Mycar> getCarGoods(String uname);
}
