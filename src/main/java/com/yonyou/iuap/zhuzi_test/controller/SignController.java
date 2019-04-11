package com.yonyou.iuap.zhuzi_test.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.zhuzi_test.entity.OrderInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guoxh
 * @date 2019/4/3 19:06
 * @desc TODO
 */
@RestController
@RequestMapping("/sign")
public class SignController extends BaseController {

    @RequestMapping(value="/invoke",method = RequestMethod.GET)
    public Object signInvoke(HttpServletRequest request){
        JSONObject obj = RestUtils.getInstance().doGetWithSign("http://localhost:8180/poc/order_info/restWithSign/list?1=1",null, JSONObject.class);
        JSONArray details = obj.getJSONObject("detailMsg").getJSONObject("data").getJSONArray("content");
        List<OrderInfo> list = JSONArray.parseArray(details.toJSONString(),OrderInfo.class);

        System.out.println(obj.toString());
        return buildSuccess();
    }

    @RequestMapping(value="/invoke",method = RequestMethod.POST)
    public Object signInvokePost(HttpServletRequest request){
        Map<String,String> params = new HashMap<>(4);
        params.put("search_id","1");
        JSONObject obj = RestUtils.getInstance().doPostWithSign("http://localhost:8180/poc/order_info/restWithSign/list",params,JSONObject.class);

        return buildSuccess();
    }
}
