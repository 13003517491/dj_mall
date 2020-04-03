package com.dj.mall.api.auth.base;

import com.dj.mall.model.dto.auth.base.BaseDataDTOReq;
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

    /**
     * 获取所有编码
     * @return
     * @throws Exception
     */
    List<BaseDataDTOResp> getBaseDataList() throws Exception;

    /**
     * 根据id获取基础数据
     * @return
     * @throws Exception
     */
    BaseDataDTOResp getBaseDataById(Integer id) throws Exception;

    /**
     * 根据id修改数据名
     * @param name 数据名
     * @param baseDataId 数据id
     * @throws Exception
     */
    void updateNameById(String name, Integer baseDataId) throws Exception;

    /**
     * 新增数据
     * @param baseDataDTOReq
     * @throws Exception
     */
    void saveBaseData(BaseDataDTOReq baseDataDTOReq) throws Exception;
}
