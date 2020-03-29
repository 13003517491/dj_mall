package com.dj.mall.api.auth.role;

import com.dj.mall.model.dto.auth.role.RoleDTOReq;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;

import java.util.List;

/**
 * @描述  角色接口
 * @创建人 zhangjq
 * @创建时间 2020/3/26
 */
public interface RoleApi {

    /**
     * 获取所有角色
     * @return
     * @throws Exception
     */
    List<RoleDTOResp> getRole() throws Exception;

    /**
     * 资源去重
     * @param roleName
     * @return
     */
    Boolean distinct(String roleName) throws Exception;

    /**
     * 角色新增
     * @param roleDTOReq
     * @throws Exception
     */
    void saveRole(RoleDTOReq roleDTOReq) throws Exception;

    /**
     * 根据id获取角色
     * @param id 角色id
     * @throws Exception
     */
    RoleDTOResp getRoleById(Integer id) throws Exception;

    /**
     * 根据角色id 修改角色名
     * @param roleName 角色名
     * @param roleId 角色id
     */
    void updateRoleNameById(String roleName, Integer roleId) throws Exception;
}
