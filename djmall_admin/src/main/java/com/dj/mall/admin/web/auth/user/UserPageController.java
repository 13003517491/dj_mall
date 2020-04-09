package com.dj.mall.admin.web.auth.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.PermissionCode;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PasswordSecurityUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


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
    public String toLogin() {
        return "user/user_login";
    }

    /**
     * 去注册页面
     */
    @RequestMapping("toAdd")
    public String toAdd(Model model) throws Exception {
        model.addAttribute("salt", PasswordSecurityUtil.generateSalt());
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
     * 忘记密码
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toResetPwd")
    public String toResetPwd(Model model) throws Exception {
        model.addAttribute("salt", PasswordSecurityUtil.generateSalt());
        return "user/user_reset_pwd";
    }

    /**
     * 去修密码
     * @param username 用户名
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toUpdatePwd")
    public String toUpdatePwd(String username, Model model) throws Exception {
        model.addAttribute("salt", PasswordSecurityUtil.generateSalt());
        model.addAttribute("username", username);
        return "user/user_update_pwd";
    }


    /**
     * 去用户展示页面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toList")
    @RequiresPermissions(value = PermissionCode.USER_MANAGER)
    public String toList1(Model model) throws Exception {
        model.addAttribute("roleList", DozerUtil.mapList(roleApi.getRole(), RoleVOResp.class));
        return "user/user_list";
    }

    /**
     * 去修改页面
     * @param id 用户id
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toUpdate/{id}")
    @RequiresPermissions(value = PermissionCode.USER_UPDATE_BTN)
    public String toUpdate(@PathVariable Integer id, Model model) throws Exception {
        model.addAttribute("user", DozerUtil.map(userApi.getUserById(id), UserVOResp.class));
        return "user/user_update";
    }

    /**
     * 去授权页面
     * @param id 用户id
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toUpdateRole/{id}")
    @RequiresPermissions(value = PermissionCode.USER_AUTH_BTN)
    public String toUpdateRole(@PathVariable Integer id, Model model) throws Exception {
        model.addAttribute("userRole", DozerUtil.map(userApi.getUserRole(id), UserVOResp.class));
        model.addAttribute("roleList", DozerUtil.mapList(roleApi.getRole(), RoleVOResp.class));
        return "user/user_update_role";
    }

}
