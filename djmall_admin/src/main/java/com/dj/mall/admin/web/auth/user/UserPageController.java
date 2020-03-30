package com.dj.mall.admin.web.auth.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.entity.auth.user.UserRoleEntity;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PasswordSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @描述
 * @创建人 zhangjq
 * @创建时间 2020/3/24
 */
@Controller
@RequestMapping("/auth/user/")
public class UserPageController {


    @Reference
    private UserApi userApi;

    @Reference
    private RoleApi roleApi;

    /**
     * 去登录页面
     */
    @RequestMapping("toLogin")
    private String toLogin() {
        return "user/user_login";
    }

    /**
     * 去注册页面
     */
    @RequestMapping("toAdd")
    private String toAdd(Model model) throws Exception {
        String salt = PasswordSecurityUtil.generateSalt();
        model.addAttribute("salt", salt);
        model.addAttribute("roleList", DozerUtil.mapList(roleApi.getRole(), RoleVOResp.class));
        return "user/user_add";
    }

    /**
     * 邮箱激活
     * @param username
     * @return
     */
    @RequestMapping("toValidate")
    public String toValidate(String username) throws Exception {
        userApi.updateStatusByUsername(username);
        return "redirect:toLogin";
    }

    /**
     * 去重置密码
     */
    @RequestMapping("toResetPwd")
    private String toResetPwd() {
        return "user/user_reset_pwd";
    }


    /**
     * 去用户展示页面
     */
    @RequestMapping("toList")
    private String toList1(Model model) throws Exception {
        model.addAttribute("roleList", DozerUtil.mapList(roleApi.getRole(), RoleVOResp.class));
        return "user/user_list";
    }

    /**
     * 去修改页面
     */
    @RequestMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Integer id, Model model) throws Exception {
        UserDTOResp userDTOResp = userApi.getUserById(id);
        model.addAttribute("user", userDTOResp);
        return "user/user_update";
    }

    /**
     * 去授权页面
     */
    @RequestMapping("toUpdateRole/{id}")
    public String toUpdateRole(@PathVariable Integer id, Model model) throws Exception {
        UserRoleEntity userRole = userApi.getUserRole(id);
        List<RoleDTOResp> roleList = roleApi.getRole();
        model.addAttribute("userRole", userRole);
        model.addAttribute("roleList", roleList);
        return "user/user_update_role";
    }

}
