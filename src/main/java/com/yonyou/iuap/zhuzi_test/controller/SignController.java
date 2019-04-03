package com.yonyou.iuap.zhuzi_test.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.zhuzi_test.entity.OrderInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author guoxh
 * @date 2019/4/3 19:06
 * @desc TODO
 */
@RestController
@RequestMapping("/sign")
public class SignController extends BaseController {

    @RequestMapping("/invoke")
    public Object signInvoke(HttpServletRequest request){
        JSONObject obj = RestUtils.getInstance().doGetWithSign("http://localhost:8180/poc/order_info/restWithSign/list?1=1",null, JSONObject.class);
        JSONArray details = obj.getJSONObject("detailMsg").getJSONObject("data").getJSONArray("content");
        List<OrderInfo> list = JSONArray.parseArray(details.toJSONString(),OrderInfo.class);

        System.out.println(obj.toString());
        return buildSuccess();
    }
}
