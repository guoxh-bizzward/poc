package com.yonyou.iuap.duban.service;
import com.yonyou.iuap.baseservice.intg.service.GenericIntegrateService;
import com.yonyou.iuap.baseservice.intg.support.ServiceFeature;
import com.yonyou.iuap.duban.dao.DubanMapper;
import com.yonyou.iuap.duban.entity.Duban;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.baseservice.ref.service.RefCommonService;
import java.util.List;

import static com.yonyou.iuap.baseservice.intg.support.ServiceFeature.*;
@Service

/**
 * Duban CRUD 核心服务,提供逻辑删除/乐观锁
 */
public class DubanService extends GenericIntegrateService<Duban>{


    private DubanMapper dubanMapper;

    @Autowired
    public void setDubanMapper(DubanMapper dubanMapper) {
        this.dubanMapper = dubanMapper;
        super.setGenericMapper(dubanMapper);
    }
    
        @Autowired
    private RefCommonService refService;
    @Autowired
    private DubanEnumService DubanEnumService;
        public List selectListByExcelData(List idsList) {
                List list  = dubanMapper.selectListByExcelData(idsList);
                list = refService.fillListWithRef(list);
                list = DubanEnumService.afterListQuery(list);
                return list;
        }


    /**
     * @CAU 可插拔设计
     * @return 向父类 GenericIntegrateService 提供可插拔的特性声明
     */
    @Override
    protected ServiceFeature[] getFeats() {
        return new ServiceFeature[]{ REFERENCE,BPM,MULTI_TENANT,LOGICAL_DEL };
    }
}