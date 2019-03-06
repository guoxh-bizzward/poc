package com.yonyou.iuap.currtype.controller;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yonyou.uap.wb.entity.org.Organization;
import net.sf.json.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.WordUtils;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.collections.MapUtils;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.iuap.currtype.entity.Currtype;
import com.yonyou.iuap.currtype.service.CurrtypeService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mvc.type.JsonResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;
/**
 * 说明：币种 基础Controller——提供数据增、删、改、查、导入导出等rest接口
 * 
 * @date 2019-3-5 21:16:32
 */
@Controller
@RequestMapping(value="/currtype")
public class CurrtypeController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(CurrtypeController.class);

    private CurrtypeService currtypeService;

    @Autowired
    public void setCurrtypeService(CurrtypeService currtypeService) {
        this.currtypeService = currtypeService;
    }


    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(PageRequest pageRequest, SearchParams searchParams) {
        Page<Currtype> page = this.currtypeService.selectAllByPage(pageRequest, searchParams);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", page);
        return this.buildMapSuccess(map);
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public Object get(PageRequest pageRequest, SearchParams searchParams) {
        String id = MapUtils.getString(searchParams.getSearchMap(), "id");
        if (id==null){
            return this.buildSuccess();//前端约定传空id则拿到空对象
        }
        if(StrUtil.isBlank(id)) {
            return this.buildError("msg", "主键id参数为空!", RequestStatusEnum.FAIL_FIELD);
        }else {
            Currtype entity = this.currtypeService.findById(id);
            return this.buildSuccess(entity);
        }
    }


    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(@RequestBody Currtype entity) {
        JsonResponse jsonResp;
        try {
            this.currtypeService.save(entity);
            jsonResp = this.buildSuccess(entity);
        }catch(Exception exp) {
            jsonResp = this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
        }
        return jsonResp;
    }

    @RequestMapping(value = "/saveBatch")
    @ResponseBody
    public Object saveBatch(@RequestBody List<Currtype> listData) {
        this.currtypeService.saveBatch(listData);
        return this.buildSuccess();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestBody Currtype entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.currtypeService.delete(entity);
        return super.buildSuccess();
    }

    @RequestMapping(value = "/deleteBatch")
    @ResponseBody
    public Object deleteBatch(@RequestBody List<Currtype> listData, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.currtypeService.deleteBatch(listData);
        return super.buildSuccess();
    }
    @RequestMapping(value = "/getByIds")
    @ResponseBody
    public Object getByIds(HttpServletRequest request){
        JSONObject result = new JSONObject();
        String data = request.getParameter("data");
        if (StringUtils.isEmpty(data)) {
            return result;
        }
        JSONArray array = JSON.parseArray(data);

        List<Currtype> currtypeList = new ArrayList<Currtype>();
        if (!CollectionUtils.isEmpty(array)) {
            String[] strArray = (String[]) array.toArray(new String[array.size()]);
            currtypeList = this.currtypeService.getCurrtypeByIds(strArray);
        }
        result.put("data", transformTOBriefEntity(currtypeList));
        return result;
    }
    private List<Organization> transformTOBriefEntity(List<Currtype> currtypeList){
        List<Organization> results = new ArrayList<>();
        if(!CollectionUtils.isEmpty(currtypeList)){
            for (Currtype entity:currtypeList){
                Organization organization =new Organization();
                organization.setCode(entity.getCode());
                organization.setName(entity.getName());
                organization.setId(entity.getId());
                organization.setCreate_date(new Date());
                organization.setEffective_date(new Date());
                organization.setTs(new Date());
                results.add(organization);
            }
        }

        return results;
    }

}