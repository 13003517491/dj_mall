package com.dj.mall.admin.web.auth.resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dj.mall.admin.vo.PermissionCode;
import com.dj.mall.admin.vo.auth.resource.ResourceVOReq;
import com.dj.mall.admin.vo.auth.resource.ResourceVOResp;
import com.dj.mall.api.auth.resource.ResourceApi;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.entity.auth.resource.ResourceEntity;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOReq;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述  资源控制类
 * @创建人 zhangjq
 * @创建时间 2020/3/28
 */
@RestController
@RequestMapping("/auth/resource/")
public class ResourceController {

    @Reference
    private ResourceApi resourceApi;

    /**
     * 左侧菜单展示
     * @param session
     * @return
     */
    @RequestMapping("list")
    public ResultModel<Object> list(HttpSession session) {
        UserDTOResp userDTORespList = (UserDTOResp) session.getAttribute(SystemConstant.USER_SESSION);
        List<ResourceDTOResp> menuList = new ArrayList<>();
        userDTORespList.getPermissionList().forEach(resource -> {
            if (resource.getResourceType().equals(SystemConstant.MENU_SHOW)) {
                menuList.add(resource);
            }
        });
        return new ResultModel<>().success(DozerUtil.mapList(menuList, ResourceVOResp.class));
    }

    /**
     * 资源内容展示
     * @return
     * @throws Exception
     */
    @RequestMapping("show")
    @RequiresPermissions(value = PermissionCode.RESOURCE_MANAGER)
    public ResultModel<Object> show() throws Exception {
        List<ResourceDTOResp> resourceDTORespList = resourceApi.getResource();
        return new ResultModel<>().success(resourceDTORespList);
    }

    /**
     * 资源名去重
     * @param resourceName 资源名
     * @return
     * @throws Exception
     */
    @RequestMapping("findByResourceName")
    public Boolean findByRoleName(String resourceName) throws Exception{
        resourceApi.distinct(resourceName);
        return true;
    }


    /**
     * 资源修改
     * @param resourceVOReq 接参数
     * @return
     * @throws Exception
     */
    @RequestMapping("update")
    @RequiresPermissions(value = PermissionCode.RESOURCE_UPDATE_BTN)
    public ResultModel<Object> update(ResourceVOReq resourceVOReq) throws Exception {
        resourceApi.updateResourceById(DozerUtil.map(resourceVOReq, ResourceDTOReq.class));
        return new ResultModel<>().success();
    }

    /**
     * 资源删除
     * @param id 资源删除
     * @return
     * @throws Exception
     */
    @RequestMapping("delById")
    @RequiresPermissions(value = PermissionCode.RESOURCE_DEL_BTN)
    public ResultModel<Object> delById(Integer id) throws Exception {
        resourceApi.delResAndRoleResByIds(id);
        return new ResultModel<>().success();
    }

    /**
     * 资源新增
     * @param resourceVOReq 接收参数
     * @return
     */
    @RequestMapping("add")
    @RequiresPermissions(value = PermissionCode.RESOURCE_ADD_BTN)
    public ResultModel<Object> add(ResourceVOReq resourceVOReq) throws Exception {
        resourceApi.saveResource(DozerUtil.map(resourceVOReq, ResourceDTOReq.class));
        return new ResultModel<>().success();
    }

}
