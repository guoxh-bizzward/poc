package com.yonyou.iuap.order_info.controller;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import net.sf.json.JSONObject;
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
import com.yonyou.iuap.order_info.entity.OrderInfoNew;
import com.yonyou.iuap.order_info.service.OrderInfoNewService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mvc.type.JsonResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;
import com.yonyou.iuap.common.utils.ExcelExportImportor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 说明：orderinfo_new 基础Controller——提供数据增、删、改、查、导入导出等rest接口
 * 
 * @date 2019-4-17 21:33:43
 */
@Controller
@RequestMapping(value="/order_info_new")
public class OrderInfoNewController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(OrderInfoNewController.class);

    private OrderInfoNewService orderInfoNewService;

    @Autowired
    public void setOrderInfoNewService(OrderInfoNewService orderInfoNewService) {
        this.orderInfoNewService = orderInfoNewService;
    }


    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(PageRequest pageRequest, SearchParams searchParams) {
        Page<OrderInfoNew> page = this.orderInfoNewService.selectAllByPage(pageRequest, searchParams);
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
            OrderInfoNew entity = this.orderInfoNewService.findById(id);
            return this.buildSuccess(entity);
        }
    }


    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(@RequestBody OrderInfoNew entity) {
        JsonResponse jsonResp;
        try {
            this.orderInfoNewService.save(entity);
            jsonResp = this.buildSuccess(entity);
        }catch(Exception exp) {
            jsonResp = this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
        }
        return jsonResp;
    }

    @RequestMapping(value = "/saveBatch")
    @ResponseBody
    public Object saveBatch(@RequestBody List<OrderInfoNew> listData) {
        this.orderInfoNewService.saveBatch(listData);
        return this.buildSuccess();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestBody OrderInfoNew entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.orderInfoNewService.delete(entity);
        return super.buildSuccess();
    }

    @RequestMapping(value = "/deleteBatch")
    @ResponseBody
    public Object deleteBatch(@RequestBody List<OrderInfoNew> listData, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.orderInfoNewService.deleteBatch(listData);
        return super.buildSuccess();
    }


    @RequestMapping(value = "/excelTemplateDownload", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, String> excelTemplateDownload(HttpServletRequest request,
            HttpServletResponse response){
        Map<String, String> result = new HashMap<String, String>();

        try {
            ExcelExportImportor.downloadExcelTemplate(response, getImportHeadInfo(), "orderinfo_new", "orderinfo_new模板");
            result.put("status", "success");
            result.put("msg", "Excel模版下载成功");
        } catch (Exception e) {
            logger.error("Excel模版下载失败", e);
            result.put("status", "failed");
            result.put("msg", "Excel模版下载失败");
        }
        return result;
    }

    @RequestMapping(value = "/toImportExcel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> importExcel(HttpServletRequest request){
        Map<String, String> result = new HashMap<String, String>();
        try {

            List<OrderInfoNew> list = new ArrayList<OrderInfoNew>();
            CommonsMultipartResolver resolver = new CommonsMultipartResolver();
            if(resolver.isMultipart(request)){
                 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                 int size = multipartRequest.getMultiFileMap().size();
                 MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
                 if(multiValueMap !=null && size > 0){
                    for(MultiValueMap.Entry<String, List<MultipartFile>> me : multiValueMap.entrySet()){
                        List<MultipartFile> multipartFile = me.getValue();
                        for(MultipartFile mult : multipartFile){
                            String multName =  mult.getOriginalFilename().toString();
                            String multTypeName = multName.substring(multName.lastIndexOf(".")+1, multName.length());
                            if((multTypeName != "xlsx" && !"xlsx".equals(multTypeName)) && (multTypeName != "xls" && !"xls".equals(multTypeName))){
                                throw new Exception("导入数据格式异常！");
                            }
                            list = ExcelExportImportor.loadExcel(mult.getInputStream(), getImportHeadInfo(), OrderInfoNew.class);
                            if(list==null || list.size()== 0){
                                throw new Exception("导入数据异常！");
                            }
                        }
                    }
                 }
            }
            orderInfoNewService.saveBatch(list);
            result.put("status", "success");
            result.put("msg", "Excel导入成功");
        } catch (Exception e) {
            logger.error("Excel导入失败", e);
            result.put("status", "failed");
            result.put("msg", e.getMessage()!=null?e.getMessage():"Excel导入失败");
        }
        return result;
    }

    @RequestMapping(value = "/toExportExcel",method = RequestMethod.POST)
    @ResponseBody
    public Object exportExcel(PageRequest pageRequest,
                        @FrontModelExchange(modelType = OrderInfoNew.class) SearchParams searchParams,HttpServletResponse response,@RequestBody List<OrderInfoNew> dataList){

       Map<String, String> result = new HashMap<String, String>();
       try {
          List idsList = new ArrayList();
          for (OrderInfoNew entity : dataList) {
             idsList.add(entity.getId());
          }
          List list = orderInfoNewService.selectListByExcelData(idsList);
          ExcelExportImportor.writeExcel(response, list, getExportHeadInfo(), "orderinfo_new", "orderinfo_new");
          result.put("status", "success");
          result.put("msg", "信息导出成功");
          result.put("fileName", "orderinfo_new");
       } catch (Exception e) {
          logger.error("Excel下载失败", e);
          result.put("status", "failed");
          result.put("msg", "Excel下载失败");
       }
       return result;
    }

    @RequestMapping(value = "/toExportExcelAll",method = RequestMethod.GET)
    @ResponseBody
    public Object exportExcelAll(PageRequest pageRequest,
                        @FrontModelExchange(modelType = OrderInfoNew.class) SearchParams searchParams,HttpServletResponse response){

       Map<String, String> result = new HashMap<String, String>();
       try {
          Page<OrderInfoNew> page = orderInfoNewService.selectAllByPage(pageRequest, searchParams);
          List list = page.getContent();
          if(list == null || list.size()==0){
              throw new Exception("没有导出数据！");
          }
          ExcelExportImportor.writeExcel(response, list, getExportHeadInfo(), "orderinfo_new", "orderinfo_new");
          result.put("status", "success");
          result.put("msg", "信息导出成功");
       } catch (Exception e) {
          logger.error("Excel下载失败", e);
          result.put("status", "failed");
          result.put("msg", "Excel下载失败");
       }
       return result;
    }

    private Map<String, String> getExportHeadInfo() {
        String values = "{'orderTypeEnumValue':'订单类型','orderNo':'编号','purOrgSrc':'采购单位','releaseTime':'发布时间','orderAmount':'订单金额','purGroupNo':'采购组编号','confirmTime':'确认时间','orderStateEnumValue':'订单状态',}";
        return getMapInfo(values);
    }

    private Map<String, String> getImportHeadInfo() {
        String values = "{'orderType':'订单类型','orderNo':'编号','purOrg':'采购单位','releaseTime':'发布时间','orderAmount':'订单金额','purGroupNo':'采购组编号','confirmTime':'确认时间','orderState':'订单状态',}";
        return getMapInfo(values);
    }

    private Map<String, String> getMapInfo(String values){
        String values_new = values.substring(0, values.length()-1);
        if(values_new.endsWith(",")){
                values = values_new.substring(0, values_new.length()-1)+"}";
        }
        Map<String, String> headInfo = null;
        //if (headInfo == null) {
        net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(values);
        headInfo = (Map<String, String>) json;
        //}
        return headInfo;
    }


}