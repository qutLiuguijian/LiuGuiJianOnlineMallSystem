package com.lgj.lgjshop.service;

import com.lgj.lgjshop.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-03-30
 */
public interface IGoodsService extends IService<Goods> {
    Goods getGoodsDetail(Integer id);
    List<Map> getGoodsDetailImg(Integer id);
}
