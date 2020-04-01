package com.dj.mall.pro.auth.impl.base;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.base.BaseDataApi;
import com.dj.mall.entity.auth.base.BaseDataEntity;
import com.dj.mall.mapper.auth.base.BaseDataMapper;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import com.dj.mall.model.util.DozerUtil;

import java.util.List;

/**
 * @author ZhangJQ
 * @描述 基础数据接口实现类
 * @创建人 zhangjq
 * @创建时间 2020/4/1
 */
@Service
public class BaseDataApiImpl extends ServiceImpl<BaseDataMapper, BaseDataEntity> implements BaseDataApi {

    /**
     * 获取基础数据
     *
     * @return
     */
    @Override
    public List<BaseDataDTOResp> getBaseData() throws Exception {
        List<BaseDataEntity> baseDataList = this.list();
        return DozerUtil.mapList(baseDataList, BaseDataDTOResp.class);
    }
}
