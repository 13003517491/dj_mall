package com.dj.mall.pro.auth.impl.role;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
}
