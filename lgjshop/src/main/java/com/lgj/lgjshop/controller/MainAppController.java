package com.lgj.lgjshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgj.lgjshop.config.JedisUtil;
import com.lgj.lgjshop.entity.*;
import com.lgj.lgjshop.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
public class MainAppController {
    @Autowired
    IGoodsService goodsService;

    @Autowired
    IUserService userService;

    @Autowired
    IMycarService mycarService;

    @Autowired
    IGoodsorderService goodsorderService;

    @Autowired
    IGaddressService gaddressService;

    @Autowired
    private JedisUtil redisService;

    @RequestMapping(value = "getGoodsByClassify", method = RequestMethod.POST)
    @ApiOperation(value = "通过分类查询商品列表", notes = "")
    public ServerResult getGoodsByClassify(@RequestParam String classify) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("g_classify", classify);
        List<Goods> list = goodsService.list(queryWrapper);
        return new ServerResult(0, "", list);
    }

    @RequestMapping(value = "getGoodsByTClassify", method = RequestMethod.POST)
    @ApiOperation(value = "通过三级分类查询商品列表", notes = "")
    public ServerResult getGoodsByTClassify(@RequestParam String thirdClassify) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("g_third_classify", thirdClassify);
        List<Goods> list = goodsService.list(queryWrapper);
        return new ServerResult(0, "", list);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ApiOperation(value = "注册用户", notes = "")
    public ServerResult register(@RequestParam String username, @RequestParam String password, @RequestParam String phone) {
        List<User> list = userService.list();
        for (User u : list) {
            if (username.equals(u.getUsername())) {
                return new ServerResult(1, "当前用户名已被注册,请重新填写!");
            }
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setLevel("普通用户");
        boolean save = userService.save(user);
        if (save) {
            return new ServerResult(0, "注册成功");
        } else {
            return new ServerResult(1, "注册失败，请重试或联系管理员!");
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ApiOperation(value = "用户名密码登录", notes = "")
    public ServerResult login(@RequestParam String username, @RequestParam String password) throws Exception {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        List<User> list = userService.list(queryWrapper);
        if (list.size() == 1) {
            redisService.set(username, "isLogin", 3 * 24 * 60 * 60);
            return new ServerResult(0, "登录成功", list.get(0));
        } else {
            boolean isFlag = true;
            List<User> userList = userService.list();
            for (User u : userList) {
                if (username.equals(u.getUsername())) {
                    isFlag = false;
                    if (!password.equals(u.getPassword())) {
                        return new ServerResult(1, "密码错误,请重新填写或者选择其他登录方式");
                    }
                }
            }
            if (isFlag) {
                return new ServerResult(1, "用户不存在，请前往注册!");
            }
        }
        return new ServerResult(1, "用户名或密码错误!");
    }

    @RequestMapping(value = "isReLogin", method = RequestMethod.POST)
    @ApiOperation(value = "是否需要重新登录登录", notes = "")
    public ServerResult isReLogin(@RequestParam String username) throws Exception {
        String s = redisService.get(username);
        if (s == null || s.isEmpty()) {
            return new ServerResult(1, "需要重新登录!");
        } else {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            List<User> list = userService.list(queryWrapper);
            return new ServerResult(0, "登录成功", list.get(0));
        }
    }

    @RequestMapping(value = "buy", method = RequestMethod.POST)
    @ApiOperation(value = "下单", notes = "")
    public ServerResult isReLogin(@RequestParam String username
            , @RequestParam int g_id
            , @RequestParam int edTime
            , @RequestParam String shopAddress
            , @RequestParam String userAddress) {
        QueryWrapper<Mycar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("my_goodsid", g_id);
        boolean remove = mycarService.remove(queryWrapper);
        if (remove) {
            Goodsorder goodsorder = new Goodsorder();
            goodsorder.setUname(username);
            goodsorder.setGId(g_id);
            goodsorder.setEdtime(edTime);
            goodsorder.setShopaddresss(shopAddress);
            goodsorder.setUseraddress(userAddress);
            boolean save = goodsorderService.save(goodsorder);
            if (save) {
                return new ServerResult(0, "下单成功");
            } else {
                return new ServerResult(1, "下单失败，请重试或联系管理员!");
            }
        } else {
            return new ServerResult(1, "下单失败，请重试或联系管理员!");
        }
    }
}
