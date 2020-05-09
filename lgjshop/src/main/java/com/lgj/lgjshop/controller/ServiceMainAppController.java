package com.lgj.lgjshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgj.lgjshop.config.JedisUtil;
import com.lgj.lgjshop.entity.*;
import com.lgj.lgjshop.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/service")
public class ServiceMainAppController {
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
    private IGshopService gshopService;

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

    @Autowired
    private IGimgurlService gimgurlService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    @ApiOperation(value = "登录", notes = "")
    public String getReceiver() {
        return "login";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ApiOperation(value = "登录", notes = "")
    public ModelAndView index(@RequestParam String username, @RequestParam String password) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(username + password);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        List<User> list = userService.list(queryWrapper);
        if (list.size() == 1) {
            if(list.get(0).getLevel().equals("管理员")){
                List<Goodsorder> sendOrder = goodsorderService.getSendOrder();
                modelAndView.addObject("list", sendOrder);
                List<Goods> goods = goodsService.list();
                modelAndView.addObject("list2", goods);
                modelAndView.setViewName("index");
                return modelAndView;
            }else {
                modelAndView.addObject("error", "只允许管理员登录！");
                modelAndView.setViewName("login");
                return modelAndView;
            }
        } else {
            boolean isFlag = true;
            List<User> userList = userService.list();
            for (User u : userList) {
                if (username.equals(u.getUsername())) {
                    isFlag = false;
                    if (!password.equals(u.getPassword())) {
                        modelAndView.addObject("error", "密码不正确！");
                        modelAndView.setViewName("login");
                        return modelAndView;
                    }
                }
            }
            if (isFlag) {
                modelAndView.addObject("error", "无此用户！");
                modelAndView.setViewName("login");
                return modelAndView;
            }
        }

        return modelAndView;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ApiOperation(value = "注册用户", notes = "")
    @ResponseBody
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String phone) {
        List<User> list = userService.list();
        for (User u : list) {
            if (username.equals(u.getUsername())) {
                return "该用户已存在！";
            }
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setLevel("管理员");
        boolean save = userService.save(user);
        if (save) {
            return "注册成功！";
        } else {
            return "注册失败！";
        }
    }

    @RequestMapping(value = "addShop", method = RequestMethod.POST)
    @ApiOperation(value = "添加商铺", notes = "")
    @ResponseBody
    public String addShop(@RequestParam String sname, @RequestParam String saddress) {
        List<Gshop> list = gshopService.list();
        for (Gshop u : list) {
            if (sname.equals(u.getSname())) {
                return "该店铺名已存在！";
            }
        }
        Gshop gshop = new Gshop();
        gshop.setSname(sname);
        gshop.setSaddress(saddress);
        boolean save = gshopService.save(gshop);
        if (save) {
            return "添加成功！";
        } else {
            return "添加失败！";
        }
    }

    @RequestMapping(value = "getSend", method = RequestMethod.POST)
    @ApiOperation(value = "获得发货列表", notes = "")
    @ResponseBody
    public String getSend() throws JsonProcessingException {
        List<Goodsorder> sendOrder = goodsorderService.getSendOrder();
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(sendOrder);
        return s;
    }

    @RequestMapping(value = "send", method = RequestMethod.POST)
    @ApiOperation(value = "发货", notes = "")
    @ResponseBody
    public String send(@RequestParam int id) {
        Goodsorder goodsorder = goodsorderService.getById(id);
        goodsorder.setState(2);
        boolean b = goodsorderService.saveOrUpdate(goodsorder);
        if (b) {
            return "发货成功";
        } else {
            return "发货失败";
        }
    }

    @RequestMapping(value = "getGoods", method = RequestMethod.POST)
    @ApiOperation(value = "获得所有商品", notes = "")
    @ResponseBody
    public String getGoods() throws JsonProcessingException {
        List<Goods> list = goodsService.list();
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(list);
        return s;
    }

    @RequestMapping(value = "removeGoods", method = RequestMethod.POST)
    @ApiOperation(value = "删除某件商品", notes = "")
    @ResponseBody
    public String removeGoods(@RequestParam Integer id) throws JsonProcessingException {
        boolean b = goodsService.removeById(id);
        QueryWrapper<Gimgurl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("g_id", id);
        List<Gimgurl> list = gimgurlService.list(queryWrapper);
        for (int i = 0; i < list.size(); i++) {
            gimgurlService.removeById(list.get(i).getId());
        }
        if (b) {
            return "删除成功";
        } else {
            return "删除失败";
        }

    }

    @RequestMapping(value = "getClassify", method = RequestMethod.POST)
    @ApiOperation(value = "获得所有分类", notes = "")
    @ResponseBody
    public String getClassify() throws JsonProcessingException {
        List<Firstclassify> list = firstclassifyService.list();
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(list);
        System.out.println(s);
        return s;
    }

    @RequestMapping(value = "getShop", method = RequestMethod.POST)
    @ApiOperation(value = "获得所有店铺", notes = "")
    @ResponseBody
    public String getShop() throws JsonProcessingException {
        List<Gshop> list = gshopService.list();
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(list);
        System.out.println(s);
        return s;
    }

    @RequestMapping(value = "getTClassify", method = RequestMethod.POST)
    @ApiOperation(value = "获得所有三级分类", notes = "")
    @ResponseBody
    public String getTClassify() throws JsonProcessingException {
        List<Thirdclassify> list = thirdclassifyService.list();
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(list);
        System.out.println(s);
        return s;
    }

    @RequestMapping(value = "addGoods", method = RequestMethod.POST)
    @ApiOperation(value = "添加商品", notes = "")
    @ResponseBody
    public String addGoods(String gName,
                           String shop,
                           Double gPrice,
                           Double gDiscountPrice,
                           String gDesc,
                           String gClassify,
                           String gThirdClassify,
                           String gImage) throws JsonProcessingException {
        Goods goods = new Goods();
        goods.setGName(gName);
        goods.setGDesc(gDesc);
        goods.setGPrice(gPrice);
        goods.setGDiscountPrice(gDiscountPrice);
        goods.setGClassify(gClassify);
        goods.setGThirdClassify(gThirdClassify);
        QueryWrapper<Gshop> queryWrapper = new QueryWrapper<Gshop>();
        queryWrapper.eq("sname", shop);
        List<Gshop> list = gshopService.list(queryWrapper);
        goods.setGShopId(list.get(0).getId());
        QueryWrapper<Thirdclassify> queryWrapper2 = new QueryWrapper<Thirdclassify>();
        queryWrapper2.eq("third_classify", gThirdClassify);
        List<Thirdclassify> list2 = thirdclassifyService.list(queryWrapper2);
        Secondclassify secondclassify = secondclassifyService.getById(list2.get(0).getSId());
        goods.setGSecondClassify(secondclassify.getSecondClassify());
        Firstclassify firstclassify = firstclassifyService.getById(secondclassify.getFId());
        goods.setGFirstClassify(firstclassify.getFirstClassify());
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> o = (List<String>) objectMapper.readValue(gImage,List.class);
        System.out.println(o.size()+"="+o.toString()+"="+o.get(0));
        if (o.size()<2){
            return "图片至少上传两张!";
        }
        goods.setGImage(o.get(0));
//        String[] split = gImage.split("@,!");
//        goods.setGImage(split[0]);
//        if (split.length < 2) {
//            return "图片至少上传两张!";
//        }
//        for (int i = 0; i < split.length; i++) {
//            if (split[i].equals("") || split[i].isEmpty()) {
//                return "请检查多张图片url中间是否使用多个@,!隔开!";
//            }
//        }
        boolean save = goodsService.save(goods);
        if (save) {
            QueryWrapper<Goods> queryWrapper3 = new QueryWrapper<Goods>();
            queryWrapper3.eq("g_name", gName);
            queryWrapper3.eq("g_shop_id", list.get(0).getId());
            List<Goods> list1 = goodsService.list(queryWrapper3);
            for (int i = 0; i < o.size(); i++) {
                Gimgurl gimgurl = new Gimgurl();
                gimgurl.setGId(list1.get(0).getId());
                gimgurl.setImgurl(o.get(i));
                gimgurlService.save(gimgurl);
            }
            return "添加成功";
        } else {
            return "添加失败";
        }

    }

    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    @ResponseBody
    public String uploadPhoto(@RequestParam("file")MultipartFile[] file, HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> list = new ArrayList<>();
        for (MultipartFile photo : file) {
            if (photo == null) {
                return "请选择要上传的文件!";
            }
            if (photo.getSize() > 1024 * 1024 * 10) {
                return "文件大小不能超过10M！";
            }
            //获取文件后缀
            String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1, photo.getOriginalFilename().length());
            if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
                return "请选择jpg,jpeg,gif,png格式的图片！";
            }
            String savePath = System.getProperty("user.dir") + "/src/main/resources/static/images/upload/";
            File savePathFile = new File(savePath);
            if (!savePathFile.exists()) {
                //若不存在该目录，则创建目录
                savePathFile.mkdir();
            }
            String filename = new Date().getTime() + "." + suffix;
            try {
                //将文件保存指定目录
                photo.transferTo(new File(savePath + filename));
            } catch (Exception e) {
                e.printStackTrace();
                return "保存文件异常！";
            }
            list.add(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/images/" + filename);
        }
        String s = objectMapper.writeValueAsString(list);
        return s;
    }
}
