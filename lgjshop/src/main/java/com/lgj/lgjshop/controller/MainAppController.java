package com.lgj.lgjshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgj.lgjshop.config.JedisUtil;
import com.lgj.lgjshop.entity.*;
import com.lgj.lgjshop.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IFirstclassifyService firstclassifyService;

    @Autowired
    private ISecondclassifyService secondclassifyService;

    @Autowired
    private IThirdclassifyService thirdclassifyService;

    @Autowired
    private IGassessService gassessService;

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
    @RequestMapping(value = "getGoodsById", method = RequestMethod.POST)
    @ApiOperation(value = "通过id查询商品", notes = "")
    public ServerResult getGoodsById(@RequestParam Integer id) {
        Goods goods = goodsService.getById(id);
        return new ServerResult(0, "", goods);
    }
    @RequestMapping(value = "addAssess", method = RequestMethod.POST)
    @ApiOperation(value = "通过id查询商品", notes = "")
    public ServerResult addAssess(@RequestParam Integer gid,@RequestParam String uname,String assess) {
        Gassess gassess=new Gassess();
        gassess.setGId(gid);
        gassess.setUname(uname);
        if (assess==null||assess.isEmpty()){
            gassess.setAssess("此用户未填写评价");
        }else {
            gassess.setAssess(assess);
        }
        gassessService.save(gassess);
        return new ServerResult(0, "评价成功");
    }
    @RequestMapping(value = "getAssess", method = RequestMethod.POST)
    @ApiOperation(value = "通过id查询商品评价", notes = "")
    public ServerResult addAssess(@RequestParam Integer gid) {
        QueryWrapper<Gassess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("g_id", gid);
        List<Gassess> list = gassessService.list(queryWrapper);
        return new ServerResult(0, "",list);
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
        List<User> listEeeor =new ArrayList<>();
        User user=new User();
        listEeeor.add(user);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        List<User> list = userService.list(queryWrapper);
        if (list.size() == 1) {
            if(list.get(0).getLevel().equals("普通用户")){
                redisService.set(username, "isLogin", 3 * 24 * 60 * 60);
                return new ServerResult(0, "登录成功", list.get(0));
            }else{
                return new ServerResult(1, "用户角色不匹配请更换用户登录!",listEeeor.get(0));
            }

        } else {
            boolean isFlag = true;
            List<User> userList = userService.list();
            for (User u : userList) {
                if (username.equals(u.getUsername())) {
                    isFlag = false;
                    if (!password.equals(u.getPassword())) {
                        return new ServerResult(1, "密码错误,请重新填写",listEeeor.get(0));
                    }
                }
            }
            if (isFlag) {
                return new ServerResult(1, "用户不存在，请前往注册!",listEeeor.get(0));
            }
        }
        return new ServerResult(1, "用户名或密码错误!",listEeeor.get(0));
    }

    @RequestMapping(value = "isReLogin", method = RequestMethod.POST)
    @ApiOperation(value = "是否需要重新登录登录", notes = "")
    public ServerResult isReLogin(@RequestParam String username) throws Exception {
        List<User> listEeeor =new ArrayList<>();
        User user=new User();
        listEeeor.add(user);
        String s = redisService.get(username);
        if (s == null || s.isEmpty()) {
            return new ServerResult(1, "需要重新登录!",listEeeor.get(0));
        } else {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            List<User> list = userService.list(queryWrapper);
            return new ServerResult(0, "登录成功", list.get(0));
        }
    }

    @RequestMapping(value = "exitLogin", method = RequestMethod.POST)
    @ApiOperation(value = "退出登录", notes = "")
    public ServerResult exitLogin() throws Exception {
        redisService.flush();
        return new ServerResult(0, "退出成功");
    }

    @RequestMapping(value = "addCar", method = RequestMethod.POST)
    @ApiOperation(value = "加入购物车", notes = "")
    public ServerResult addCar(@RequestParam String username
            , @RequestParam int g_id
            , @RequestParam int count) {
        QueryWrapper<Mycar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uname", username);
        queryWrapper.eq("g_id", g_id);
        List<Mycar> list = mycarService.list(queryWrapper);
        if (list == null || list.size() == 0) {
            Mycar mycar = new Mycar();
            mycar.setUname(username);
            mycar.setGId(g_id);
            mycar.setCount(count);
            boolean save = mycarService.save(mycar);
            if (save) {
                return new ServerResult(0, "加入购物车成功");
            } else {
                return new ServerResult(0, "加入购物车失败");
            }
        } else {
            Mycar mycar = list.get(0);
            int new_count = mycar.getCount() + count;
            mycar.setCount(new_count);
            boolean b = mycarService.saveOrUpdate(mycar);
            if (b) {
                return new ServerResult(0, "加入购物车成功");
            } else {
                return new ServerResult(0, "加入购物车失败");
            }
        }
    }

    @RequestMapping(value = "getCar", method = RequestMethod.POST)
    @ApiOperation(value = "获得购物车商品", notes = "")
    public ServerResult getCar(@RequestParam String username) {
        List<Mycar> carGoods = mycarService.getCarGoods(username);
        return new ServerResult(0, "", carGoods);
    }
    @RequestMapping(value = "isShowMark", method = RequestMethod.POST)
    @ApiOperation(value = "是否显示购物车角标", notes = "")
    public ServerResult isShowMark(@RequestParam String username) {
        QueryWrapper<Mycar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uname", username);
        List<Mycar> carGoods = mycarService.list(queryWrapper);
        if(carGoods==null||carGoods.size()==0){
            return new ServerResult(1, "不显示");
        }else {
            return new ServerResult(0, "显示");
        }

    }
    @RequestMapping(value = "deleteCar", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除购物车一条单子", notes = "")
    public ServerResult deleteCar(@RequestParam Integer id) {
        boolean b = mycarService.removeById(id);
        if (b){
            return new ServerResult(0, "删除成功");
        }else {
            return new ServerResult(1, "删除失败");
        }

    }
    @RequestMapping(value = "buyFromCar", method = RequestMethod.POST)
    @ApiOperation(value = "购物车下单", notes = "")
    public ServerResult buyFromCar(@RequestParam String username
            , @RequestParam int id, @RequestParam int g_id
            , @RequestParam int edTime
            , @RequestParam String userAddress
            , @RequestParam int count
            , @RequestParam int state
            , @RequestParam int rid) {
        QueryWrapper<Mycar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        boolean remove = mycarService.remove(queryWrapper);
        if (remove) {
            Goodsorder goodsorder = new Goodsorder();
            goodsorder.setUname(username);
            goodsorder.setGId(g_id);
            goodsorder.setEdtime(edTime);
            Goods goods = goodsService.getGoodsDetail(g_id);
            goodsorder.setSaddress(goods.getSaddress());
            goodsorder.setAddress(userAddress);
            goodsorder.setCount(count);
            goodsorder.setState(state);
            goodsorder.setRId(rid);
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

    @RequestMapping(value = "buyFromDetail", method = RequestMethod.POST)
    @ApiOperation(value = "立即购买", notes = "")
    public ServerResult buyFromDetail(@RequestParam String username
            , @RequestParam int g_id
            , @RequestParam int edTime
            , @RequestParam String userAddress
            , @RequestParam int count
            , @RequestParam int state
            , @RequestParam int rid) {
        Goodsorder goodsorder = new Goodsorder();
        goodsorder.setUname(username);
        goodsorder.setGId(g_id);
        goodsorder.setEdtime(edTime);
        Goods goods = goodsService.getGoodsDetail(g_id);
        goodsorder.setSaddress(goods.getSaddress());
        goodsorder.setAddress(userAddress);
        goodsorder.setCount(count);
        goodsorder.setState(state);
        goodsorder.setRId(rid);
        boolean save = goodsorderService.save(goodsorder);
        if (save) {
            return new ServerResult(0, "下单成功");
        } else {
            return new ServerResult(1, "下单失败，请重试或联系管理员!");
        }

    }

    @RequestMapping(value = "updateOrder", method = RequestMethod.POST)
    @ApiOperation(value = "更新下单 0 待付款 1 待发货 2 待收货 3 待评价 4 售后/退款", notes = "")
    public ServerResult updateOrder(@RequestParam int id, @RequestParam int state) {
        Goodsorder goodsorder = goodsorderService.getById(id);
        goodsorder.setState(state);
        boolean b = goodsorderService.saveOrUpdate(goodsorder);
        if (b) {
            return new ServerResult(0, "成功");
        } else {
            return new ServerResult(1, "失败，请重试或联系管理员!");
        }
    }

    @RequestMapping(value = "deleteOrder", method = RequestMethod.POST)
    @ApiOperation(value = "删除下单 只有待付款和退款或售后可删除", notes = "")
    public ServerResult deleteOrder(@RequestParam int id) {
        boolean b = goodsorderService.removeById(id);
        if (b) {
            return new ServerResult(0, "成功");
        } else {
            return new ServerResult(1, "失败，请重试或联系管理员!");
        }
    }

    @RequestMapping(value = "getAllOrder", method = RequestMethod.POST)
    @ApiOperation(value = "获得全部下单", notes = "")
    public ServerResult getAllOrder(@RequestParam String uname) {
        QueryWrapper<Goodsorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uname", uname);
        List<Goodsorder> list = goodsorderService.list(queryWrapper);
        return new ServerResult(0, "成功", list);
    }

    @RequestMapping(value = "getAllOrderByUAS", method = RequestMethod.POST)
    @ApiOperation(value = "通过用户名和状态获得全部下单", notes = "")
    public ServerResult getAllOrderByUAS(@RequestParam String uname, int state) {
        List<Goodsorder> orderAll;
        if (state < 0) {
            orderAll = goodsorderService.getOrderAll(uname);
        } else {
            orderAll = goodsorderService.getOrderByState(uname, state);
        }

        return new ServerResult(0, "成功", orderAll);
    }

    @RequestMapping(value = "classify", method = RequestMethod.POST)
    @ApiOperation(value = "分类", notes = "")
    public ServerResult classify() {
        List<Firstclassify> list = firstclassifyService.list();
        List<Secondclassify> list2 = secondclassifyService.list();
        List<Thirdclassify> list3 = thirdclassifyService.list();
        List<Classify> classifyList = new ArrayList<>();
        for (Thirdclassify thirdclassify : list3) {
            Classify classify = new Classify();
            classify.setId(thirdclassify.getSId());
            classify.setName(thirdclassify.getThirdClassify());
            classifyList.add(classify);
        }
        List<Classify> classifyList2 = new ArrayList<>();
        for (Secondclassify secondclassify : list2) {
            Classify classify = new Classify();
            classify.setId(secondclassify.getFId());
            classify.setName(secondclassify.getSecondClassify());
            classify.childName = new ArrayList<>();
            for (Classify classify1 : classifyList) {
                if (classify1.getId() == secondclassify.getId()) {
                    classify.childName.add(classify1);
                }
            }
            classifyList2.add(classify);
        }
        List<Classify> classifyList3 = new ArrayList<>();
        for (Firstclassify firstclassify : list) {
            Classify classify = new Classify();
            classify.setId(firstclassify.getId());
            classify.setName(firstclassify.getFirstClassify());
            classify.childName = new ArrayList<>();
            for (Classify classify1 : classifyList2) {
                if (classify1.getId() == firstclassify.getId()) {
                    classify.childName.add(classify1);
                }
            }
            classifyList3.add(classify);
        }
        return new ServerResult(0, "成功", classifyList3);
    }

    @RequestMapping(value = "getGoodsDetail", method = RequestMethod.POST)
    @ApiOperation(value = "获取商品详情", notes = "")
    public ServerResult getGoodsDetail(@RequestParam int id) {
        Goods goods = goodsService.getGoodsDetail(id);
        List<Map> goodsDetailImg = goodsService.getGoodsDetailImg(id);
        List<String> imgList = new ArrayList<>();
        for (Map mp : goodsDetailImg) {
            imgList.add(mp.get("imgurl").toString());
        }
        goods.setImgurl(imgList);
        return new ServerResult(0, "", goods);
    }

    @RequestMapping(value = "saveReceiver", method = RequestMethod.POST)
    @ApiOperation(value = "保存收货人信息", notes = "")
    public ServerResult saveReceiver(Integer id,
                                     @RequestParam String uname,
                                     @RequestParam String receiver,
                                     @RequestParam String address,
                                     @RequestParam String phone) {
        Gaddress gaddress = new Gaddress();
        gaddress.setId(id);
        gaddress.setUname(uname);
        gaddress.setAddress(address);
        gaddress.setReceiver(receiver);
        gaddress.setPhone(phone);
        boolean save = gaddressService.saveOrUpdate(gaddress);
        if (save) {
            return new ServerResult(0, "保存成功");
        } else {
            return new ServerResult(1, "保存失败");
        }
    }
    @RequestMapping(value = "deleteReceiver", method = RequestMethod.POST)
    @ApiOperation(value = "删除收货人信息", notes = "")
    public ServerResult deleteReceiver(@RequestParam Integer id) {

        boolean remove = gaddressService.removeById(id);
        if (remove) {
            return new ServerResult(0, "删除成功");
        } else {
            return new ServerResult(1, "删除失败");
        }
    }
    @RequestMapping(value = "getReceiver", method = RequestMethod.POST)
    @ApiOperation(value = "获取收货人信息", notes = "")
    public ServerResult getReceiver(@RequestParam String uname) {
        QueryWrapper<Gaddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uname", uname);
        List<Gaddress> list = gaddressService.list(queryWrapper);
        return new ServerResult(0, "", list);
    }

}
