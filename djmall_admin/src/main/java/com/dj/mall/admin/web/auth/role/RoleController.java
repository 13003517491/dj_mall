package com.dj.mall.admin.web.auth.role;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dj.mall.admin.vo.PermissionCode;
import com.dj.mall.admin.vo.auth.resource.ResourceVOResp;
import com.dj.mall.admin.vo.auth.role.RoleVOReq;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.api.auth.resource.ResourceApi;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.entity.auth.resource.TreeData;
import com.dj.mall.entity.auth.role.RoleResourceEntity;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.dto.auth.role.RoleDTOReq;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述  角色控制类
 * @创建人 zhangjq
 * @创建时间 2020/3/29
 */
@RestController
@RequestMapping("/auth/role/")
public class RoleController {

    @Reference
    private ResourceApi resourceApi;

    @Reference
    private RoleApi roleApi;

    @RequestMapping("list")
    @RequiresPermissions(value = PermissionCode.ROLE_MANAGER)
    public ResultModel<Object> list() throws Exception {
        return new ResultModel<>().success(DozerUtil.mapList(roleApi.getRole(), RoleVOResp.class));
    }

    /**
     * 角色新增
     */
    @RequestMapping("add")
    @RequiresPermissions(value = PermissionCode.ROLE_ADD_BTN)
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
    @RequiresPermissions(value = PermissionCode.ROLE_UPDATE_BTN)
    public ResultModel<Object> update(String roleName, Integer roleId) throws Exception {
        roleApi.updateRoleNameById(roleName, roleId);
        return new ResultModel<>().success();
    }

    /**
     * 角色删除
     */
    @RequestMapping("delById")
    @RequiresPermissions(value = PermissionCode.ROLE_DEL_BTN)
    public ResultModel<Object> delById(Integer id) throws Exception {
        roleApi.delroleResAndUserRoleAndUserById(id);
        return new ResultModel<>().success();
    }

    /**
     * 资源表的关联
     *
     * @param roleId 角色id
     * @return
     */
    @RequestMapping("roleResources/{roleId}")
    @RequiresPermissions(value = PermissionCode.ROLE_ROLERESOURCE_BTN)
    public ResultModel<Object> roleResources(@PathVariable Integer roleId) throws Exception {
        //获取全部的资源表的信息
        List<ResourceDTOResp> resourceList = resourceApi.getResource();
        //获取已关联角色的资源的信息
        List<ResourceDTOResp> roleResourceList = roleApi.getRoleResource(roleId);
        //节点数据
        List<TreeData> treeDataList = new ArrayList<>();
        resourceList.forEach(resource -> {
            TreeData treeData = TreeData.builder().id(resource.getResourceId()).resourceName(resource.getResourceName()).pId(resource.getPId()).build();
            for (ResourceDTOResp roleResource : roleResourceList) {
                //复选框的回显
                if (resource.getResourceId().equals(roleResource.getResourceId())) {
                    treeData.setChecked(true);
                    break;
                }
            }
            treeDataList.add(treeData);
        });
        return new ResultModel<>().success(treeDataList);
    }

    /**
     * 删除角色原关联的资源保存新关联的资源
     */
    @RequestMapping("saveUpdateRole/{roleId}")
    @RequiresPermissions(value = PermissionCode.ROLE_ROLERESOURCE_BTN)
    public ResultModel<Object> saveUpdateRole(@PathVariable Integer roleId, Integer[] resourceIds) throws Exception {
        roleApi.saveRoleResource(roleId, resourceIds);
        return new ResultModel<>().success();
    }


}
