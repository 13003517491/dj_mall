package com.dj.mall.admin.web.auth.resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.PermissionCode;
import com.dj.mall.api.auth.resource.ResourceApi;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/resource/")
public class ResourcePageController {
    @Reference
    private ResourceApi resourceApi;

    /**
     * 去展示
     */
    @RequestMapping("toList")
    @RequiresPermissions(value = PermissionCode.RESOURCE_MANAGER)
    public String toList() {
        return "resource/resource_list";
    }

    /**
     * 去修改页面
     * @param resourceId 资源id
     * @param model 存放
     * @return
     * @throws Exception
     */
    @RequestMapping("toUpdate")
    @RequiresPermissions(value = PermissionCode.RESOURCE_UPDATE_BTN)
    public String toUpdate(Integer resourceId, Model model) throws Exception {
        model.addAttribute("resource", resourceApi.getResourceById(resourceId));
        return "resource/resource_update";
    }

    /**
     * 去新增
     */
    @RequestMapping("toAdd/{pId}")
    @RequiresPermissions(value = PermissionCode.RESOURCE_ADD_BTN)
    public String toAdd(@PathVariable Integer pId, Model model) {
        model.addAttribute("pId", pId);
        return "resource/resource_add";
    }

}
