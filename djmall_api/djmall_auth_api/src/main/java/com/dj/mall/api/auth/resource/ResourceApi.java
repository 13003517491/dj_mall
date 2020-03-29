package com.dj.mall.api.auth.resource;

import com.dj.mall.model.dto.auth.resource.ResourceDTOReq;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;

import java.util.List;

/**
 * @描述  资源接口
 * @创建人 zhangjq
 * @创建时间 2020/3/28
 */
public interface ResourceApi {

    /**
     * 获取用户资源
     * @param userId 用户id
     * @return
     */
    List<ResourceDTOResp> getUserResourceList(Integer userId) throws Exception;

    /**
     * 获取所有资源
     * @return
     * @throws Exception
     */
    List<ResourceDTOResp> getResource() throws Exception;

    /**
     * 资源名去重
     * @param resourceName 资源名
     * @return
     * @throws Exception
     */
    Boolean distinct(String resourceName) throws Exception;


    /**
     * 根据id修改资源
     * @param resourceDTOReq 接收参数
     */
    void updateResourceById(ResourceDTOReq resourceDTOReq) throws Exception;

    /**
     *
     * @param resourceId 资源id
     * @return
     * @throws Exception
     */
    ResourceDTOResp getResourceById(Integer resourceId) throws Exception;

    /**
     * 删除资源
     * @param id
     * @throws Exception
     */
    void delResAndRoleResByIds(Integer id) throws Exception;
}
