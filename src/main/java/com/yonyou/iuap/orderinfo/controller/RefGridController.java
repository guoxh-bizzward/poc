package com.yonyou.iuap.orderinfo.controller;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.currtype.entity.Currtype;
import com.yonyou.iuap.currtype.service.CurrtypeService;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.uap.ieop.security.entity.DataPermission;
import com.yonyou.uap.ieop.security.exception.BusinessException;
import com.yonyou.uap.ieop.security.sdk.AuthRbacClient;
import iuap.ref.ref.RefClientPageInfo;
import iuap.ref.sdk.refmodel.model.AbstractGridRefModel;
import iuap.ref.sdk.refmodel.vo.RefViewModelVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/reference/bdCommonOrg")
public class RefGridController extends AbstractGridRefModel {

    @Autowired
    private CurrtypeService currtypeService;

    @Override
    @ResponseBody
    public List<Map<String, String>> filterRefJSON(@RequestBody RefViewModelVO refViewModelVO) {
        return null;
    }

    @Override
    @ResponseBody
    public List<Map<String, String>> matchPKRefJSON(@RequestBody RefViewModelVO refViewModelVO) {
        return null;
    }

    @Override
    @ResponseBody
    public List<Map<String, String>> matchBlurRefJSON(@RequestBody RefViewModelVO refViewModelVO) {
        return null;
    }

    @Override
    @ResponseBody
    public Map<String, Object> getCommonRefData(@RequestBody RefViewModelVO refViewModelVO) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        int pageNum = refViewModelVO.getRefClientPageInfo().getCurrPageIndex()==0 ? 1:refViewModelVO.getRefClientPageInfo().getCurrPageIndex();
        int pageSize = refViewModelVO.getRefClientPageInfo().getPageSize();

        PageRequest request = buildPageRequest(pageNum, pageSize, null);

        String searchParams = StringUtils.isEmpty(refViewModelVO.getContent())? null :refViewModelVO.getContent();

        Page<Currtype> pageData = currtypeService.selectAllByPage(request, this.buildSearchParams(searchParams));
        List<Currtype> listData = pageData.getContent();
        if(CollectionUtils.isNotEmpty(listData)) {
            List<Map<String, String>> list = buildRtnValsOfRef(listData);
            RefClientPageInfo refPageInfo = refViewModelVO.getRefClientPageInfo();
            refPageInfo.setPageCount(pageData.getTotalPages());
            refPageInfo.setCurrPageIndex(pageData.getNumber());
            refPageInfo.setPageSize(pageSize);

            refViewModelVO.setRefClientPageInfo(refPageInfo);

            resultMap.put("dataList", list);
            resultMap.put("refViewModel", refViewModelVO);
        }
        return resultMap;
    }

    /**
     * 构建查询条件
     * @param searchParam
     * @return
     */
    private SearchParams buildSearchParams(String searchParam) {
        SearchParams params = new SearchParams();
        Map<String,Object> results = new HashMap<String,Object>();
        if(!StringUtils.isEmpty(searchParam)) {
            results.put("searchParam", searchParam);
        }
        params.setSearchMap(results);
        return params;
    }

    /**
     * 构建分页SQL
     * @param pageNum
     * @param pageSize
     * @param sortColumn
     * @return
     */
    private PageRequest buildPageRequest(int pageNum, int pageSize, String sortColumn) {
        Sort sort = null;
        if(StringUtils.isEmpty(sortColumn) || "auto".equalsIgnoreCase(sortColumn)) {
            sort = new Sort(Sort.Direction.ASC, new String[] {"code"});
        }else {
            sort = new Sort(Sort.Direction.ASC, new String[] {sortColumn});
        }
        return new PageRequest(pageNum-1, pageSize, sort);
    }


    private List<Map<String,String>> buildRtnValsOfRef(List<Currtype> currtypeList) {
        // 数据权限集合set
        String tenantId = InvocationInfoProxy.getTenantid();
        String sysId = InvocationInfoProxy.getSysid();
        String userId = InvocationInfoProxy.getUserid();
        List<DataPermission> dataPerms = null;
        try {
            dataPerms = AuthRbacClient.getInstance().queryDataPerms(tenantId, sysId, userId, "currtype");
            Set<String> set = new HashSet<String>();
            if(dataPerms != null && dataPerms.size()>0){
                for (DataPermission temp : dataPerms) {
                    if(temp != null){
                        set.add(temp.getResourceId());
                    }
                }
            }

            List<Map<String, String>> results = new ArrayList<Map<String,String>>();
            if ((currtypeList != null) && (!currtypeList.isEmpty())) {
                for (Currtype entity : currtypeList) {
                    Map<String, String> refDataMap = new HashMap<String, String>();
                    refDataMap.put("id", entity.getId());
                    refDataMap.put("refname", entity.getName());
                    refDataMap.put("refcode", entity.getCode());
                    refDataMap.put("refpk", entity.getId());

                    results.add(refDataMap);
                }
            }
            return results;

        } catch (BusinessException e) {
            e.printStackTrace();

            return null;
        }
    }

}
