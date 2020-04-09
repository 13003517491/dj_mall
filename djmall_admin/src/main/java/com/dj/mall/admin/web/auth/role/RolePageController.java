package com.dj.mall.admin.web.auth.role;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.mall.admin.vo.PermissionCode;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @描述
 * @创建人 zhangjq
 * @创建时间 2020/3/29
 */
@Controller
@RequestMapping("/auth/role/")
public class RolePageController {

    @Reference
    private RoleApi roleApi;

    /**
     * 去展示
     */
    @RequestMapping("toList")
    @RequiresPermissions(value = PermissionCode.ROLE_MANAGER)
    public String toList() {
        return "role/role_list";
    }

    /**
     * 去新增
     */
    @RequestMapping("toAdd")
    @RequiresPermissions(value = PermissionCode.ROLE_ADD_BTN)
    public String toAdd() {
        return "role/role_add";
    }

    /**
     * 去修改页面
     */
    @RequestMapping("toUpdate")
    @RequiresPermissions(value = PermissionCode.ROLE_UPDATE_BTN)
    public String toUpdate(Integer id, Model model) throws Exception {
        RoleDTOResp roleDTOResp = roleApi.getRoleById(id);
        model.addAttribute("role", roleDTOResp);
        return "role/role_update";
    }

    /**
     * 去关联资源数据
     *
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping("toRoleResList")
    @RequiresPermissions(value = PermissionCode.ROLE_ROLERESOURCE_BTN)
    public String toResource(Integer roleId, Model model) {
        model.addAttribute("roleId", roleId);
        return "role/role_res_list";
    }
}
