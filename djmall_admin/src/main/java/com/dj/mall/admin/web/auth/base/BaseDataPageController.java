package com.dj.mall.admin.web.auth.base;

import com.dj.mall.admin.vo.PermissionCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ZhangJQ
 * @描述 基础数据访问前台
 * @创建人 zhangjq
 * @创建时间 2020/4/1
 */
@Controller
@RequestMapping("/auth/base/")
public class BaseDataPageController {

    /**
     * 去展示
     */
    @RequestMapping("toList")
    public String toList() {
        return "base/base_data_list";
    }
}
