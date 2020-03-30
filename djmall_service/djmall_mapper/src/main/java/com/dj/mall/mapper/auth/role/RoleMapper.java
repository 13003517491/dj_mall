package com.dj.mall.mapper.auth.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.auth.resource.ResourceEntity;
import com.dj.mall.entity.auth.role.RoleEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @描述  角色mapper
 * @创建人 zhangjq
 * @创建时间 2020/3/26
 */
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * 根据角色id获取资源
     * @param roleId 角色id
     * @return
     * @throws DataAccessException
     */
    List<ResourceEntity> getRoleResourceByRoleId(Integer roleId) throws DataAccessException;
}
