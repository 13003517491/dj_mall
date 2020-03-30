package com.dj.mall.admin.web.auth.resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
        UserDTOResp userDTORespList = (UserDTOResp) session.getAttribute("userEntity");
        List<ResourceDTOResp> menuList = new ArrayList<>();
        for (ResourceDTOResp resource : userDTORespList.getPermissionList()) {
            if (resource.getResourceType().equals(SystemConstant.MENU_SHOW)) {
                menuList.add(resource);
            }
        }
        return new ResultModel<>().success(DozerUtil.mapList(menuList, ResourceVOResp.class));
    }

    /**
     * 资源内容展示
     * @return
     * @throws Exception
     */
    @RequestMapping("show")
    public ResultModel<Object> show() throws Exception {
        List<ResourceDTOResp> resourceDTORespList = resourceApi.getResource();
        return new ResultModel<>().success(resourceDTORespList);
    }

    /**
     * 资源名去重
     */
    @RequestMapping("findByResourceName")
    public Boolean findByRoleName(String resourceName) throws Exception{
        resourceApi.distinct(resourceName);
        return true;
    }


    /**
     * 资源修改
     */
    @RequestMapping("update")
    public ResultModel<Object> update(ResourceVOReq resourceVOReq) throws Exception {
        resourceApi.updateResourceById(DozerUtil.map(resourceVOReq, ResourceDTOReq.class));
        return new ResultModel<>().success();
    }

    /**
     * 资源删除
     */
    @RequestMapping("delById")
    public ResultModel<Object> delById(Integer id) throws Exception {
        resourceApi.delResAndRoleResByIds(id);
        return new ResultModel<>().success();
    }



}
