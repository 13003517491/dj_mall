package com.dj.mall.api.auth.base;

import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;

import java.util.List;

/**
 * @author ZhangJQ
 * @描述 基础数据接口
 * @创建人 zhangjq
 * @创建时间 2020/4/1
 */
public interface BaseDataApi {

    /**
     * 获取基础数据
     * @return
     */
    List<BaseDataDTOResp> getBaseData() throws Exception;
}
