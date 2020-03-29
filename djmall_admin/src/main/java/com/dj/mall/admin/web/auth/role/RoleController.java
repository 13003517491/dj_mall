package com.dj.mall.admin.web.auth.role;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dj.mall.admin.vo.auth.role.RoleVOReq;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.role.RoleDTOReq;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @描述  角色控制类
 * @创建人 zhangjq
 * @创建时间 2020/3/29
 */
@RestController
@RequestMapping("/auth/role/")
public class RoleController {


    @Reference
    private RoleApi roleApi;

    @RequestMapping("list")
    public ResultModel<Object> list() throws Exception {
        return new ResultModel<>().success(DozerUtil.mapList(roleApi.getRole(), RoleVOResp.class));
    }

    /**
     * 角色新增
     */
    @RequestMapping("add")
    public ResultModel<Object> add(RoleVOReq roleVOReq) throws Exception {
        roleApi.saveRole(DozerUtil.map(roleVOReq, RoleDTOReq.class));
        return new ResultModel<>().success();
    }

    /**
     * 角色名去重
     */
    @RequestMapping("distinct")
    public Boolean findByRoleName(String roleName) throws Exception {
        return roleApi.distinct(roleName);
    }

    /**
     * 角色修改
     * @param roleName 角色名
     * @param roleId 角色id
     * @return
     */
    @RequestMapping("update")
    public ResultModel<Object> update(String roleName, Integer roleId) throws Exception {
        roleApi.updateRoleNameById(roleName, roleId);
        return new ResultModel<>().success();
    }

    /**
     * 角色删除
     */
    @RequestMapping("delById")
    public ResultModel<Object> delById(Integer id) throws Exception {
        roleApi.delroleResAndUserRoleAndUserById(id);
        return new ResultModel<>().success();
    }

}
