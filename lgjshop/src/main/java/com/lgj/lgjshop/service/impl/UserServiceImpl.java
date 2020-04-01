package com.lgj.lgjshop.service.impl;

import com.lgj.lgjshop.entity.User;
import com.lgj.lgjshop.mapper.UserMapper;
import com.lgj.lgjshop.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-03-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
