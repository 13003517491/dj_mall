package com.dj.mall.mapper.auth.resource;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.auth.resource.ResourceEntity;
import com.dj.mall.mapper.bo.resource.ResourceBOResp;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @描述  资源 mapper
 * @创建人 zhangjq
 * @创建时间 2020/3/28
 */
public interface ResourceMapper extends BaseMapper<ResourceEntity> {

    /**
     * 获取用户资源
     * @param userId
     * @return
     */
    List<ResourceEntity> getUserResourceList(Integer userId) throws DataAccessException;
}
