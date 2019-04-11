package com.yonyou.iuap.duban.service;
import com.yonyou.iuap.baseservice.intg.service.GenericIntegrateService;
import com.yonyou.iuap.baseservice.intg.support.ServiceFeature;
       
import com.yonyou.iuap.duban.dao.DubanSubMapper;
import com.yonyou.iuap.duban.entity.DubanSub;
          

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.baseservice.ref.service.RefCommonService;
import java.util.List;

import static com.yonyou.iuap.baseservice.intg.support.ServiceFeature.*;
@Service
    

/**
 * DubanSub CRUD 核心服务,提供逻辑删除/乐观锁
 */
public class DubanSubService extends  GenericIntegrateService<DubanSub>{

    private DubanSubMapper dubanSubMapper;
    @Autowired
    public void setDubanSubMapper(DubanSubMapper dubanSubMapper) {

        this.dubanSubMapper = dubanSubMapper;
        super.setGenericMapper(dubanSubMapper);
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