package com.dj.mall.admin.web.auth.role;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @描述
 * @创建人 zhangjq
 * @创建时间 2020/3/29
 */
@Controller
@RequestMapping("/auth/role/")
public class RolePageController {

    /**
     * 去展示
     */
    @RequestMapping("toList")
    public String toList() {
        return "role/role_list";
    }

    /**
     * 去新增
     */
    @RequestMapping("toAdd")
    public String toAdd() {
        return "role/role_add";
    }
}
