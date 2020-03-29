package com.dj.mall.pro.auth.impl.role;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.entity.auth.role.RoleEntity;
import com.dj.mall.mapper.auth.role.RoleMapper;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.role.RoleDTOReq;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.util.DozerUtil;

import java.util.List;

/**
 * @描述
 * @创建人 zhangjq
 * @创建时间 2020/3/26
 */
@Service
public class RoleApiImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleApi {

    /**
     * 获取所有角色
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<RoleDTOResp> getRole() throws Exception {
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", SystemConstant.NOT_DEL);
        return DozerUtil.mapList(this.list(queryWrapper), RoleDTOResp.class);
    }

    /**
     * 资源去重
     *
     * @param roleName
     * @return
     */
    @Override
    public Boolean distinct(String roleName) throws Exception {
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_del", SystemConstant.NOT_DEL)
                .eq("role_name", roleName);
        if (this.getOne(queryWrapper) != null) {
            return false;
        }
        return true;
    }

    /**
     * 角色新增
     *
     * @param roleDTOReq
     * @throws Exception
     */
    @Override
    public void saveRole(RoleDTOReq roleDTOReq) throws Exception {
        this.save(DozerUtil.map(roleDTOReq, RoleEntity.class));
    }

    /**
     * 根据id获取角色
     *
     * @param id 角色id
     * @throws Exception
     */
    @Override
    public RoleDTOResp getRoleById(Integer id) throws Exception {
        return  DozerUtil.map(this.getById(id), RoleDTOResp.class);
    }

    /**
     * 根据角色id 修改角色名
     *
     * @param roleName 角色名
     * @param roleId   角色id
     */
    @Override
    public void updateRoleNameById(String roleName, Integer roleId) throws Exception {
        UpdateWrapper<RoleEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("role_name", roleName);
        updateWrapper.eq("id", roleId);
        this.update(updateWrapper);
    }
}
